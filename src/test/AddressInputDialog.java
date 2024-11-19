package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

public class AddressInputDialog extends JDialog {
    private JTextField latitudeField;
    private JTextField longitudeField;
    private JButton addButton;
    private JButton doneButton;
    private static final double LATITUD_MIN = 10.917;
    private static final double LATITUD_MAX = 11.033;
    private static final double LONGITUD_MIN = -74.825;
    private static final double LONGITUD_MAX = -74.766;

    private ArrayList<GeoPosition> dir;  // Aquí almacenamos las coordenadas ingresadas manualmente

    public AddressInputDialog(JFrame parent) {
        super(parent, "Ingresar Coordenadas", true);
        dir = new ArrayList<>();  // Lista para guardar las coordenadas

        setLayout(new FlowLayout());
        setSize(500, 300);
        setLocationRelativeTo(parent);

        // Campos de texto para la latitud y longitud
        latitudeField = new JTextField(10);
        longitudeField = new JTextField(10);
        
        addButton = new JButton("Agregar");
        doneButton = new JButton("Hecho");

        // Añadir los componentes al diálogo
        add(new JLabel("Latitud:"));
        add(Box.createVerticalStrut(100)); // Espaciador vertical
        add(latitudeField);
        add(new JLabel("Longitud:"));
        add(longitudeField);
        add(addButton);
        add(doneButton);

        // Acción para el botón "Agregar"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener y validar la latitud y longitud
                    double latitude = Double.parseDouble(latitudeField.getText());
                    double longitude = Double.parseDouble(longitudeField.getText());
                    
                    if(latitude >= LATITUD_MIN && latitude<= LATITUD_MAX && longitude>=LONGITUD_MIN && longitude 
                            <=LONGITUD_MAX){
                    // Crear un objeto GeoPosition con las coordenadas ingresadas
                    GeoPosition coordinates = new GeoPosition(latitude, longitude);
                    dir.add(coordinates);  // Agregar las coordenadas a la lista
               
                    // Limpiar los campos de texto después de agregar las coordenadas
                    latitudeField.setText("");
                    longitudeField.setText("");
                    JOptionPane.showMessageDialog(AddressInputDialog.this, 
                    "Coordenadas agregadas: Latitud " + latitude + ", Longitud " + longitude);
                    }else{
                         JOptionPane.showMessageDialog(AddressInputDialog.this, 
                         "Las coordenadas ingresadas están fuera del rango permitido para Barranquilla.", 
                          "Error de validación", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException ex) {
                    // Manejar el error si los valores ingresados no son números válidos
                    JOptionPane.showMessageDialog(AddressInputDialog.this, 
                        "Por favor ingresa valores numéricos válidos para la latitud y longitud.");
                }
            }
        });

        // Acción para el botón "Hecho"
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar las coordenadas guardadas en consola y cerrar el diálogo
                System.out.println("Coordenadas guardadas: " + dir);
                dispose();  // Cerrar el diálogo
            }
        });

        setVisible(true);
    }

    // Getter para obtener las coordenadas ingresadas
    public ArrayList<GeoPosition> getDir() {
        return dir;
    }

 

    // Setter por si deseas establecer las coordenadas manualmente desde otro lugar
    public void setDir(ArrayList<GeoPosition> dir) {
        this.dir = dir;
    }
    
}
