
package waypoint;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonWaypoint extends JButton{
    public ButtonWaypoint(){
        setContentAreaFilled (false);
        //setIcon(new ImageIcon(getClass().getResource("/images/icon1")));
        setIcon(new ImageIcon(getClass().getResource("/images/gps.gif")));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(50,50));
    }
}
