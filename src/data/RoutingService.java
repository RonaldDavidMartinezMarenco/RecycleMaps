package data;


import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.Translation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoutingService {

    private static RoutingService instance;
    private final GraphHopper hopper;

    public static RoutingService getInstance() {
        if (instance == null) {
            instance = new RoutingService();
        }
        return instance;
    }

    private RoutingService() {
        hopper = createGraphHopperInstance("osm colombia/colombia-latest.osm.pbf");
    }

    private GraphHopper createGraphHopperInstance(String ghLoc) {
        
        GraphHopper graHopper = new GraphHopper();
        graHopper.setOSMFile(ghLoc);
        // specify where to store graphhopper files
        graHopper.setGraphHopperLocation("target/routing-graph-cache");

        // see docs/core/profiles.md to learn more about profiles
        graHopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));

        // this enables speed mode for the profile we called car
        graHopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        graHopper.importOrLoad();
        return graHopper;
    }
    public List<RoutingData> routing(double fromLat, double fromLon, double toLat, double toLon) {
        // simple configuration of the request object
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).
                // note that we have to specify which profile we are using even when there is only one like here
                setProfile("car").
                // define the language for the turn instructions
                setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);

        // handle errors
        if (rsp.hasErrors()) {
            throw new RuntimeException(rsp.getErrors().toString());
        }

        // use the best path, see the GHResponse class for more possibilities.
        ResponsePath path = rsp.getBest();

        // points, distance in meters and time in millis of the full path
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
        InstructionList il = path.getInstructions();
        // iterate over all turn instructions
        List<RoutingData> list = new ArrayList<>();
        for (Instruction instruction : il) {
            // System.out.println("distance " + instruction.getDistance() + " for instruction: " + instruction.getTurnDescription(tr));
            list.add(new RoutingData(instruction.getDistance(), instruction.getTurnDescription(tr), instruction.getPoints()));
        }
        return list;
    }
    /*
    public List<RoutingData> routing(List<double[]> waypoints) {
        
        if (waypoints.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos dos puntos para calcular una ruta.");
        }

        // Crear una solicitud GHRequest
        GHRequest req = new GHRequest().setProfile("car").setLocale(Locale.US);

        // Agregar todos los puntos al GHRequest
        for (double[] waypoint : waypoints) {
            if (waypoint.length != 2) {
                throw new IllegalArgumentException("Cada punto debe contener exactamente latitud y longitud.");
            }
            req.addPoint(new com.graphhopper.util.shapes.GHPoint(waypoint[0], waypoint[1]));
        }

        // Enviar la solicitud y obtener la respuesta
        GHResponse rsp = hopper.route(req);

        // Manejo de errores
        if (rsp.hasErrors()) {
            throw new RuntimeException(rsp.getErrors().toString());
        }

        // Procesar la mejor ruta
        ResponsePath path = rsp.getBest();
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();
        Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
        InstructionList il = path.getInstructions();

        // Crear la lista de datos de enrutamiento
        List<RoutingData> list = new ArrayList<>();
        for (Instruction instruction : il) {
            list.add(new RoutingData(instruction.getDistance(), instruction.getTurnDescription(tr), instruction.getPoints()));
        }
        return list;
        
    }
    */
        

}
