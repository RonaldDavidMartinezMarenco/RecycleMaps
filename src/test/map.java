
package test;

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
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWayPoint;
import waypoint.WaypointRender;
import waypoint.myWaypoint;

public class map extends javax.swing.JFrame {
     private main v1;
     private GeoPosition selectedPosition;
     private ArrayList<GeoPosition>coordinates;
     private int index;
     private boolean verifyF = false;
     private boolean verifyR=false;
     private boolean verifyS=false;
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
     
     public map() {
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
        jXMapViewer.setZoom(12);
        //Crear evento del mouse
        MouseInputListener x = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(x);
        jXMapViewer.addMouseMotionListener(x);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent();
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
    private void clearWaypoint() {
        for (myWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        waypoints.clear();
        initWaypoint();
    }
    private void drawRoute(ArrayList<GeoPosition> routePoints){
        for (int i = 0; i < routePoints.size()-1; i++) {
            GeoPosition start = routePoints.get(i); 
            GeoPosition end = routePoints.get(i+1);    
        }
    }
    private void drawLineBetweenPoints(Graphics2D g2d, GeoPosition start, GeoPosition end)
   {
          
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

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jXMapViewer = new org.jxmapviewer.JXMapViewer();
        cmdAdd = new javax.swing.JButton();
        cmdClear = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);

        jPanel1.setBackground(new java.awt.Color(0, 153, 0));
        jPanel1.setForeground(new java.awt.Color(0, 153, 0));
        jPanel1.setFocusable(false);
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(499, 512));
        jPanel1.setRequestFocusEnabled(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jXMapViewer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 692, Short.MAX_VALUE)
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXMapViewer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jXMapViewer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        cmdAdd.setText("Recycle Waypoints");
        cmdAdd.setActionCommand("Show RecyclePoints");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });

        cmdClear.setText("Clear");
        cmdClear.setToolTipText("");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        jButton1.setText("Select Points");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Final Points");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Show Route");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Thanks!");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(cmdAdd)
                        .addGap(37, 37, 37)
                        .addComponent(cmdClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton4)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdAdd)
                    .addComponent(cmdClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(80, 80, 80)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    //Añade waypoints de reciclaje al presioanar el boton. 
    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        verifyR=true;
        //Waypoints Fijos de informacion
        for (int i = 0; i < positions.length; i++) 
        {
            addWaypoint(new myWaypoint(namesRecycle[i],event,positions[i]));
        }
        
    }//GEN-LAST:event_cmdAddActionPerformed
    //Boton clear
    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed

        clearWaypoint();
        System.out.println(selectedPosition);
    }//GEN-LAST:event_cmdClearActionPerformed
    //Select positions
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        verifyR=false;
        verifyF=false;
        AddressInputDialog d = new AddressInputDialog(this); //Pasamos el jFrame actual como padre aqui inicializamos el constructor que guarda las direcciones.
        //Aqui llamaremos a geoPosition Coordinates si implementamos la API DE GOOGLE MAPS;
        coordinates = d.getDir();
        if(coordinates.isEmpty()){
            for(GeoPosition pos:coordinates){
                System.out.println("Latitud: "+pos.getLatitude()+"Longitud:"+pos.getLongitude());
            }
            System.out.println("Coordenadas vacias");
            verifyS=false;
        }else{
            for(GeoPosition pos:coordinates){
                System.out.println("Latitud: "+pos.getLatitude()+"Longitud:"+pos.getLongitude());
            }
            verifyS = true;
        }
            
      
        
    }//GEN-LAST:event_jButton1ActionPerformed
    //Select punto final
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(verifyS){    
            verifyR= false;
            verifyF = true;
        
            recyclePoints d= new recyclePoints(this,namesRecycle,positions);
            selectedPosition = d.getSelectedPosition();
            index = d.getSelectedIndex();
            if (selectedPosition != null) {
            System.out.println("Latitud: " + selectedPosition.getLatitude() + 
                           ", Longitud: " + selectedPosition.getLongitude());
            }
            coordinates.add(selectedPosition);
            verifyS = false;
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    //Mostrar ruta
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Aqui añadimos los puntos de ruta y verificamos si se entro al boton "final"
        if(verifyS){
            clearWaypoint(); //Caso tal de que se presione de nuevo el boton
        }
        if(verifyF){
          System.out.println("Se presiono el boton 2");
          addWaypoint (new myWaypoint(namesRecycle[index], event,selectedPosition));
        }
        for (int i = 0; i < coordinates.size(); i++) {
          addWaypoint(new myWaypoint("Direccion ruta "+(i+1), event, coordinates.get(i))); 
        }
        //Aqui mostraremos la ruta. 
        
    }//GEN-LAST:event_jButton3ActionPerformed
    //Boton de informacion
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
    }//GEN-LAST:event_jButton4ActionPerformed
        
    
    
   
    
    public void setV1(main v1){
        this.v1 = v1;
    }
    public static void main(String args[]) {
    
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new map().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdClear;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private org.jxmapviewer.JXMapViewer jXMapViewer;
    // End of variables declaration//GEN-END:variables
}
