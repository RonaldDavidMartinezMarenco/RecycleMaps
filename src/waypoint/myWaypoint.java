
package waypoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class myWaypoint extends DefaultWaypoint{
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    public myWaypoint(){
        
    }

    public myWaypoint(String name,EventWayPoint event, GeoPosition coord) {
        super(coord);
        this.name = name;
        initButton(event);
    }
    
    private String name;
    private JButton button;
    
    private void initButton(EventWayPoint event){
        button = new ButtonWaypoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               event.selected(myWaypoint.this);
            }
        });
    }
}
