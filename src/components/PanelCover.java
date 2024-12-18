/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package components;

import com.raven.swing.ButtonOutLine;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Usuario
 */
public class PanelCover extends javax.swing.JPanel {
    
    private final DecimalFormat df = new DecimalFormat ("##0.###");
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private ButtonOutLine button;
    private boolean isLogin;
    public PanelCover() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill","[center]","push[]25[]10[]25[]push");
        setLayout(layout); 
        init();
    }
    private void init(){
        title = new JLabel("¡Listo a reciclar!");
        title.setFont(new Font("sansserif",1,30));
        title.setForeground(new Color(245, 245, 245));
        add(title);
        description = new JLabel ("Gracias por confiar en nosotros.");
        add(description);
        description1 = new JLabel ("Ingresa con tu informacion personal");
        description1.setForeground(new Color(245,245,245));
        add(description1);
        button = new ButtonOutLine();
        button.setBackground(new Color(255,255,255));
        button.setForeground(new Color(255,255,255));
        button.setText("Ingresar");
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
              event.actionPerformed(ae);  
            }
            
        });
        add(button,"w 60%, h 40");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //Color verde
        GradientPaint gra = new GradientPaint(0, 0, new Color(35, 166, 97), 0, getHeight(), new Color(22, 116, 66));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public void addEvent(ActionListener event){
        this.event=event;
    }
    
    public void registerLeft(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(description, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -"+v+"% 0 0");
    }
    public void registerRigth(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(description, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -"+v+"% 0 0");
    }
    
    public void loginLeft (double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 "+v+"% 0"+v+"%");
        layout.setComponentConstraints(description, "pad 0 "+v+"% 0"+v+"%");
        layout.setComponentConstraints(description1, "pad 0 "+v+"% 0"+v+"%");
    }
    public void loginRigth(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 "+v+"% 0"+v+"%");
        layout.setComponentConstraints(description, "pad 0 "+v+"% 0"+v+"%");
        layout.setComponentConstraints(description1, "pad 0 "+v+"% 0"+v+"%");
    }
    private void login(boolean login){
        if (this.isLogin !=login) {
            if(login){
                title.setText("Bienvenido al reciclaje");
                description.setText("Ingresa con tus datos personales");
                description1.setText("Y comienza el viaje del reciclaje");
                button.setText("REGISTRATE");
            }else{
                title.setText("¡Listo a reciclar!");
                description.setText("Gracias por confiar en nosotros");
                description1.setText("Ingresa con tu informacion personal");
                button.setText("INICIA SESION");
            }
            this.isLogin = login;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
