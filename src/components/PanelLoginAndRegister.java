
package components;

import com.raven.swing.Button;
import com.raven.swing.MyPasswordField;
import com.raven.swing.MyTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.ModelLogin;
import model.ModelUserr;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Usuario
 */
public class PanelLoginAndRegister extends javax.swing.JLayeredPane {
    
    private ModelUserr user; //Objeto tipo usuario
    private ModelLogin userLogin;
    
    public ModelUserr getUser() {
        return user;
    }
    public ModelLogin getUserLogin(){
        return userLogin;
    }
    
    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin,ActionListener eventExit) {
        initComponents();
        initRegister(eventRegister,eventExit);
        initLogin(eventLogin,eventExit);
        login.setVisible(false);
        register.setVisible(true);
        
    }
    private void initRegister(ActionListener eventRegister,ActionListener eventExit){
        register.setLayout(new MigLayout("wrap","push[center]push","push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel ("Crear Cuenta");
        label.setFont(new Font("sansserif",1,30));
        label.setForeground(new Color(7,164,121));
        register.add(label);
        
        //MyTextField txtUser = new MyTextField();
        //txtUser.setPrefixIcon (new ImageIcon(getClass().getResource("/images/user.png")));
        //txtUser.setHint("Nombre");
        //egister.add(txtUser,"w 60%");
        MyTextField txtUser = createTextField(register, "Nombre", "/images/user.png");
        MyTextField txtEmail = createTextField (register,"Email","/images/mail.png");
        //MyTextField txtEmail = new MyTextField();
        //txtEmail.setPrefixIcon (new ImageIcon(getClass().getResource("/images/mail.png")));
        //txtEmail.setHint("Email");
        //register.add(txtEmail,"w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/images/pass.png")));
        txtPass.setHint("Contraseña");
        register.add(txtPass,"w 60%");  
        
        Button cmd = new Button();
        cmd.setBackground(new Color(7,164,121));
        cmd.setForeground(new Color(250,250,250));

        cmd.addActionListener(eventRegister);
        cmd.setText("REGISTRARSE");
        register.add(cmd,"w 40%,h 40");
        
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = txtUser.getText().trim();
                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPass.getPassword());
                user = new ModelUserr(0,userName,email,password);
                
            }
            
        });
        
        JButton cmdExit = new JButton ("Salir");
        cmdExit.setForeground(new Color(100,100,100));
        cmdExit.addActionListener(eventExit);
        cmdExit.setFont(new Font ("sansserif",1,12));
        cmdExit.setContentAreaFilled(false);
        cmdExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        register.add(cmdExit);
        
    }
    public static MyTextField createTextField(JPanel container, String hint, String iconPath) {
        // Crear una instancia de MyTextField
        MyTextField txtField = new MyTextField();
        
        // Configurar el icono de prefijo
        txtField.setPrefixIcon(new ImageIcon(MyTextField.class.getResource(iconPath)));
        
        // Configurar el texto de sugerencia
        txtField.setHint(hint);
        
        // Agregar el MyTextField al contenedor con un tamaño especificado
        container.add(txtField, "w 60%");
        return txtField;
    }
    
    private void initLogin(ActionListener eventLogin,ActionListener eventExit){
        login.setLayout(new MigLayout("wrap","push[center]push","push[]25[]10[]10[]25[]10[]push"));
        JLabel label = new JLabel ("Iniciar Sesion");
        label.setFont(new Font("sansserif",1,30));
        label.setForeground(new Color(7,164,121));
        login.add(label);
        
        MyTextField txtEmail = createTextField(login,"Correo","/images/mail.png");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/images/pass.png")));
        txtPass.setHint("Contraseña");
        login.add(txtPass,"w 60%");  
        
        JButton cmdForget = new JButton ("¿Olvidaste tu contraseña?");
        cmdForget.setForeground(new Color(100,100,100));
        //cmdForget.addActionListener(eventForget);
        cmdForget.setFont(new Font ("sansserif",1,12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        
        Button cmd = new Button();
        cmd.setBackground(new Color(7,164,121));
        cmd.setForeground(new Color(250,250,250));
        cmd.addActionListener(eventLogin);
        cmd.setText("INICIAR SESION");
        login.add(cmd,"w 40%,h 40");
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEmail = txtEmail.getText().trim();
                String password = String.valueOf(txtPass.getPassword());
                userLogin = new ModelLogin(userEmail,password);
                
            }
            
        });
        JButton cmdExit1 = new JButton ("Salir");
        cmdExit1.setForeground(new Color(100,100,100));
        cmdExit1.addActionListener(eventExit);
        cmdExit1.setFont(new Font ("sansserif",1,12));
        cmdExit1.setContentAreaFilled(false);
        cmdExit1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdExit1);
    }
    
    public void ShowRegister(boolean show){
        if(show){
            register.setVisible(true);
            login.setVisible(false);
        }else{
            register.setVisible(false);
            login.setVisible(true);
        }
            
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents


    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
