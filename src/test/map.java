
package test;

import data.RoutePainter;
import data.RoutingData;
import data.RoutingService;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import static javax.swing.UIManager.get;
import javax.swing.event.MouseInputListener;
import model.ModelUserr;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import test.recyclePoints;
import waypoint.EventWayPoint;
import waypoint.WaypointRender;
import waypoint.myWaypoint;

public class map extends javax.swing.JFrame {
     private final ModelUserr  user;
     
     private GeoPosition selectedPosition;
     //Lista con puntos de la ruta.
     private ArrayList<GeoPosition>coordinates;
     private List<RoutingData> routingData = new ArrayList<>();
     
     private int index;
     //No han sido seleccionados
     private boolean vFinal = false;
     private boolean vPuntos=false;
     private boolean vInicio=false;
     private boolean vRuta = false;
     private boolean activo = true;
     private final Set<myWaypoint> waypoints = new HashSet<>();
     private GeoPosition[] positions = {
        new GeoPosition(10.9732, -74.7833), //Exito metropolitano
        new GeoPosition(10.9569, -74.8094), //Exito Panorama
        new GeoPosition(10.9384, -74.8519),//Exito murillo
        new GeoPosition(10.9645, -74.7921),//Supertiendas y Droguerias olimpicas
        new GeoPosition(10.9563, -74.7992), //Falabella
        new GeoPosition(10.9437, -74.8389),//Asociacion de recicladores Fenanciclar
        new GeoPosition(10.9611, -74.7954),// Exito Chapinero
        new GeoPosition(10.9556, -74.8082), //Exito la colina
        new GeoPosition(10.9492, -74.8241), //Exito country
        new GeoPosition(10.9438, -74.8385),// Exito villa mayor
        new GeoPosition(10.9663, -74.7811), //Fundacion puntos verdes
        new GeoPosition(10.9584, -74.7932),//Reciclaje la ciudadela
        new GeoPosition(10.9505, -74.8214),//Centro reciclaje villa country
        new GeoPosition(10.9356, -74.8523),//punto verde soledad
        new GeoPosition(10.9629, -74.7792), //reciclaje el carmen
        new GeoPosition(10.9545, -74.8056),//Ecobot universidad norte
        };
     
      private String[] namesRecycle = {
        "Exito Metropolitano",
        "Exito Panorama",
        "Exito Murillo",
        "Supertiendas y Droguerias Olimpicas",
        "Falabella",
        "Asociacion de Recicladores Fenanciclar",
        "Exito Chapinero",
        "Exito La Colina",
        "Exito Country",
        "Exito Villa Mayor",
        "Fundacion Puntos Verdes",
        "Reciclaje La Ciudadela",
        "Centro Reciclaje Villa Country",
        "Punto Verde Soledad",
        "Reciclaje El Carmen",
        "Ecobot Universidad Norte"
    };

    public void setNamesRecycle(String[] namesRecycle) {
        this.namesRecycle = namesRecycle;
    }
     private EventWayPoint event;
     
     public map(ModelUserr user) {
        this.user = user;
        initComponents();
        init();
        
    }
    
     private void init()
     {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory (info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition (10.9938599,-74.7926118);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(6);
        //Crear evento del mouse
        MouseInputListener x = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(x);
        jXMapViewer.addMouseMotionListener(x);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent(); 
     }
     
     public static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, 
                                      mensaje, 
                                      "Error", 
                                      JOptionPane.ERROR_MESSAGE);
    }
     
     private void addWaypoint(myWaypoint waypoint){
         for (myWaypoint d : waypoints ) {
             jXMapViewer.remove(d.getButton());
         }
         waypoints.add(waypoint);
         initWaypoint();
     }
    private void initWaypoint(){
        WaypointPainter<myWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (myWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }
        
    }
    private void showRoute(){
        if(coordinates.size()==2){
           routingData = RoutingService.getInstance().routing(coordinates.get(0).getLatitude(),coordinates.get(0).getLongitude(), coordinates.get(1).getLatitude(), coordinates.get(1).getLongitude());
        }else{
            routingData.clear();
        }
        
        jXMapViewer.setRoutingData(routingData);
    }
    private void clearWaypoint() {
        for (myWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        //routingData.clear();
        waypoints.clear();
        initWaypoint();
    }
    private void clearRoute(){
        waypoints.clear();
        jXMapViewer.repaint(); 
    }
    private EventWayPoint getEvent(){
        return new EventWayPoint() {
            @Override
            public void selected(myWaypoint waypoint) {
               JOptionPane.showMessageDialog(map.this, waypoint.getName());
            }
        };
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        cmdRecyclePoints = new com.raven.swing.Button();
        cmdShowRoute = new com.raven.swing.Button();
        cmdClear = new com.raven.swing.Button();
        cmdFinalPoints = new com.raven.swing.Button();
        cmdSelectPoints = new com.raven.swing.Button();
        cmdInformation = new com.raven.swing.Button();
        jLabel1 = new javax.swing.JLabel();
        jXMapViewer = new data.JXMapViewerCustom();
        cmdExit = new com.raven.swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Map");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        setSize(new java.awt.Dimension(933, 534));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Title.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Title.setForeground(new java.awt.Color(7, 164, 121));
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoICO_1.gif"))); // NOI18N

        cmdRecyclePoints.setBackground(new java.awt.Color(7, 164, 121));
        cmdRecyclePoints.setForeground(new java.awt.Color(255, 255, 255));
        cmdRecyclePoints.setText("Puntos Reciclaje");
        cmdRecyclePoints.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdRecyclePoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRecyclePointsActionPerformed(evt);
            }
        });

        cmdShowRoute.setBackground(new java.awt.Color(7, 164, 121));
        cmdShowRoute.setForeground(new java.awt.Color(255, 255, 255));
        cmdShowRoute.setText("Mostrar Ruta");
        cmdShowRoute.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdShowRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdShowRouteActionPerformed(evt);
            }
        });

        cmdClear.setBackground(new java.awt.Color(7, 164, 121));
        cmdClear.setForeground(new java.awt.Color(255, 255, 255));
        cmdClear.setText("Ocultar Puntos");
        cmdClear.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        cmdFinalPoints.setBackground(new java.awt.Color(7, 164, 121));
        cmdFinalPoints.setForeground(new java.awt.Color(255, 255, 255));
        cmdFinalPoints.setText("Punto de llegada");
        cmdFinalPoints.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdFinalPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFinalPointsActionPerformed(evt);
            }
        });

        cmdSelectPoints.setBackground(new java.awt.Color(7, 164, 121));
        cmdSelectPoints.setForeground(new java.awt.Color(255, 255, 255));
        cmdSelectPoints.setText("Punto de partida");
        cmdSelectPoints.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdSelectPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSelectPointsActionPerformed(evt);
            }
        });

        cmdInformation.setBackground(new java.awt.Color(7, 164, 121));
        cmdInformation.setForeground(new java.awt.Color(255, 255, 255));
        cmdInformation.setText("Ver Información");
        cmdInformation.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdInformationActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ECOReciclaje.gif"))); // NOI18N
        jLabel1.setText("jLabel1");

        cmdExit.setBackground(new java.awt.Color(7, 164, 121));
        cmdExit.setForeground(new java.awt.Color(255, 255, 255));
        cmdExit.setText("Salir");
        cmdExit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmdExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXMapViewerLayout.createSequentialGroup()
                .addContainerGap(416, Short.MAX_VALUE)
                .addComponent(cmdExit, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXMapViewerLayout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(cmdExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(72, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmdShowRoute, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdRecyclePoints, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdSelectPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmdFinalPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdClear, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jXMapViewer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(210, 210, 210))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdRecyclePoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdSelectPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdFinalPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdShowRoute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmdInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXMapViewer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void cmdRecyclePointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRecyclePointsActionPerformed
        
        
        if (activo == true) {
            vPuntos = true;
            for (int i = 0; i < positions.length; i++) {
                addWaypoint(new myWaypoint(namesRecycle[i], event, positions[i]));
            }
        } else {
            mostrarError("Tienes que ocultar los puntos de reciclaje si quieres volver ha accionar este boton.");
        }
        activo = false;
        
    }//GEN-LAST:event_cmdRecyclePointsActionPerformed

    private void cmdShowRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdShowRouteActionPerformed
       
        vRuta = true;

        //Aqui añadimos los puntos de ruta y verificamos si se entro al boton "final"
        if (vInicio) {
            clearWaypoint(); //Caso tal de que se presione de nuevo el boton    
        } else {
            mostrarError("Selecciona al menos un punto inicial");
        }
        if (vFinal) {
            System.out.println("Se presiono el boton 2");
            //addWaypoint(new myWaypoint(namesRecycle[index], event, selectedPosition));
            for (int i = 0; i < coordinates.size(); i++) {
                addWaypoint(new myWaypoint("Direccion ruta " + (i + 1), event, coordinates.get(i)));
                vInicio = true;
            }
            //showRoute();
            /*
            List<double[]> waypoints = new ArrayList<>();
            for (GeoPosition geo : coordinates) {
                waypoints.add(new double[]{geo.getLatitude(), geo.getLongitude()});
            }
            */    
        } else {
            mostrarError("Selecciona un punto de llegada");
        }

        //Aqui mostraremos la ruta. 
        
    }//GEN-LAST:event_cmdShowRouteActionPerformed

    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
        activo=true;
        clearWaypoint();
    }//GEN-LAST:event_cmdClearActionPerformed

    private void cmdFinalPointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFinalPointsActionPerformed
        if (vInicio) {
            vPuntos = false;
            vFinal = true;

            recyclePoints d = new recyclePoints(this, namesRecycle, positions);
            selectedPosition = d.getSelectedPosition();
            index = d.getSelectedIndex();
            if (selectedPosition != null) {
                System.out.println("Latitud: " + selectedPosition.getLatitude()
                        + ", Longitud: " + selectedPosition.getLongitude());
            }
            coordinates.add(selectedPosition);
            //vInicio = false;
        } else {
            mostrarError("Selecciona al menos un punto inicial");
        }
    }//GEN-LAST:event_cmdFinalPointsActionPerformed

    private void cmdSelectPointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelectPointsActionPerformed
        vPuntos=false;
        vFinal=false;
        AddressInputDialog d = new AddressInputDialog(this); //Pasamos el jFrame actual como padre aqui inicializamos el constructor que guarda las direcciones.
        //Aqui llamaremos a geoPosition Coordinates si implementamos la API DE GOOGLE MAPS;
        coordinates = d.getDir();
        if(coordinates.isEmpty()){
            for(GeoPosition pos:coordinates){
                System.out.println("Latitud: "+pos.getLatitude()+"Longitud:"+pos.getLongitude());
            }
            System.out.println("Coordenadas vacias");
            vInicio=false;
        }else{
            for(GeoPosition pos:coordinates){
                System.out.println("Latitud: "+pos.getLatitude()+"Longitud:"+pos.getLongitude());
            }
            vInicio = true;
        }
    }//GEN-LAST:event_cmdSelectPointsActionPerformed

    private void cmdInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdInformationActionPerformed
    JTextArea textArea = new JTextArea();
    textArea.setText("Reciclar es de suma importancia para nosotros y visualizar aquellos puntos nos facilita la vida.\n"+""+
                    "Reciclar ayuda a reducir la contaminación y conservar los recursos naturales.\n" +""+
                    "Recuerda separar los residuos en plásticos, vidrios, papel y orgánicos.\n"+""+
                    "Agradezco que tengas en cuenta que es una simulacion,claramente puede mejorar debido a que usamos servicios en linea gratiuitos como JxmapViewer junto a OpenStreetMap.\n"+" "+
                    "Recomendamos que uses una API como Google maps, la cual puedes encontar en Google Cloud y asi facilitar la conversion a coordenada.");
    textArea.setLineWrap(true); // Permitir saltos de línea
    textArea.setWrapStyleWord(true); // Ajustar palabras completas
    textArea.setEditable(false); // No editable
    textArea.setPreferredSize(new Dimension(700, 500)); // Establecer tamaño preferido

    // Crear un JScrollPane para permitir el desplazamiento
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(300, 100)); // Ajusta el tamaño del JScrollPane

    JOptionPane.showMessageDialog(this, scrollPane,"Importancia sobre el reciclaje",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cmdInformationActionPerformed

    private void cmdExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_cmdExitActionPerformed

    public static void main(ModelUserr user) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new map(user).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    private com.raven.swing.Button cmdClear;
    private com.raven.swing.Button cmdExit;
    private com.raven.swing.Button cmdFinalPoints;
    private com.raven.swing.Button cmdInformation;
    private com.raven.swing.Button cmdRecyclePoints;
    private com.raven.swing.Button cmdSelectPoints;
    private com.raven.swing.Button cmdShowRoute;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private data.JXMapViewerCustom jXMapViewer;
    // End of variables declaration//GEN-END:variables
}
