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
import com.graphhopper.util.shapes.GHPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

 
public class RoutingService
{
    private static RoutingService instance;
    private final GraphHopper hopper;
    
    public static RoutingService getInstance(){
        if(instance==null){
            instance = new RoutingService();
        }
        return instance; 
    }
    
    private RoutingService() {
        hopper = createGraphHopperInstance("osn file/colombia-latest.osm.bz2");
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
    //Lista de doubles
     public List<RoutingData> routing(List<double[]> points) {
        //Aseguramos que al menos hayan dos puntos antes de dibujar la ruta.
        if (points.size() < 2) {
        throw new IllegalArgumentException("Se requieren al menos dos puntos: origen y destino.");
        }
        // simple configuration of the request object
        GHRequest req = new GHRequest(points.get(0)[0], points.get(0)[1], points.get(points.size() - 1)[0], points.get(points.size() - 1)[1]).
                // note that we have to specify which profile we are using even when there is only one like here
                setProfile("car").
                // define the language for the turn instructions
                setLocale(Locale.US);
          // Si hay más de dos puntos, agregarlos como puntos intermedios
          
        for (int i = 1; i < points.size() - 1; i++) {
        req.addPoint(new GHPoint(points.get(i)[0], points.get(i)[1]));
        }
        GHResponse rsp = hopper.route(req);

        // handle errors
        if (rsp.hasErrors()) {
            throw new RuntimeException("Error al enrutamiento"+rsp.getErrors().toString());
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
        List<RoutingData> routingDataList = new ArrayList<>();
        for (Instruction instruction : il) {
            // System.out.println("distance " + instruction.getDistance() + " for instruction: " + instruction.getTurnDescription(tr));
            routingDataList.add(new RoutingData(instruction.getDistance(), instruction.getTurnDescription(tr), instruction.getPoints()));
        }
        //return list;
        return routingDataList;
    }
}
