package test;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;
import org.jxmapviewer.viewer.GeoPosition;

public class recyclePoints extends JDialog
{
     private GeoPosition selectedPosition;
     private int selectedIndex;
    public recyclePoints(JFrame parentFrame, String[] namesRecycle, GeoPosition[] positions) {
        // Crear el JDialog, especificando el JFrame padre para que esté anclado a él
        JDialog popupDialog = new JDialog(parentFrame, "Selecciona una dirección", true);
        popupDialog.setSize(300, 300);
        popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Centrar el JDialog sobre el JFrame
        popupDialog.setLocationRelativeTo(parentFrame);

        // Crear una JList para mostrar las direcciones
        JList<String> listaDirecciones = new JList<>(namesRecycle);
        listaDirecciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaDirecciones);

        // Agregar un ListSelectionListener para manejar la selección
        listaDirecciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedIndex = listaDirecciones.getSelectedIndex();
                if (selectedIndex != -1) {
                    selectedPosition = positions[selectedIndex];
                    // Cerrar el diálogo emergente
                    popupDialog.dispose();
                }
            }
        });

        // Agregar el JScrollPane al JDialog
        popupDialog.add(scrollPane);
        popupDialog.setVisible(true); // Mostrar el diálogo emergente
    }

    // Getter para obtener la posición seleccionada
    public GeoPosition getSelectedPosition() {
        return selectedPosition;
    }
    public int getSelectedIndex(){
        return selectedIndex;
    }
}
        
        
