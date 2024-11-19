
package test;

import Connection.DataBaseConnection;
import components.Message;
import components.PanelCover;
import components.PanelLoading;
import components.PanelLoginAndRegister;
import components.PanelVerifyCode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JLayeredPane;
import model.ModelLogin;
import model.ModelMessage;
import model.ModelUserr;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import services.ServiceMail;
import services.ServiceUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class login extends javax.swing.JFrame {
    
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat ("##0.###");
    private ServiceUser service;
    private boolean isRegistered=false;
    
    public login() {
        initComponents();
        init();
    }
    
    public void init()
    {
        service = new ServiceUser();
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        ActionListener eventRegister = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                register();            
            }
        };
        ActionListener eventLogin = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                login();           
            }
        };
        ActionListener eventExit = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);           
            }
        };
        
        
        loginAndRegister = new PanelLoginAndRegister(eventRegister,eventLogin,eventExit);
        TimingTarget target  = new TimingTargetAdapter(){
            
            @Override
            public void timingEvent (float fraction){
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if(fraction<=0.5f){
                    size+=fraction*addSize;
                }else{
                    size+=addSize-fraction*addSize;
                }
                if(isLogin){
                    fractionCover=1f-fraction;
                    fractionLogin = fraction;
                    if(fraction>=0.5f){
                        cover.registerRigth(fractionCover*100);
                        
                    }else{
                        cover.loginRigth(fractionLogin*100);
                    }
                        
                }else{
                    fractionCover = fraction;
                    fractionLogin = 1f-fraction;
                    if(fraction<=0.5f){
                        cover.registerLeft(fraction*100);
                    }else{
                        cover.loginLeft((1f-fraction)*100);
                    }
                }
                if(fraction>=0.5f){
                    loginAndRegister.ShowRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }
            
            public void end(){
                isLogin = !isLogin;     
            }
            
        };
        Animator animator = new Animator (800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0); //Movimiento suave        
        bg.setLayout(layout);
        bg.setLayer(loading,JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        
        bg.add(loading,"pos 0 0 100% 100%");
        bg.add(verifyCode,"pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister,"width "+loginSize+"%, pos 1al 0 n 100%");//1al as 100%%%%%
        
        cover.addEvent(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               if(!animator.isRunning()){
                   animator.start();
               }
            }
        });
        
        verifyCode.addEventButtonOK(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(verifyCode.getInputCode());
                try{
                    ModelUserr user = loginAndRegister.getUser();
                    if(service.verifyCodeWithUser(user.getUserID(), verifyCode.getInputCode())){
                        //Si entra al condicional pero atrapa un error.
                        System.out.println("Ya entre");
                        
                        service.doneVerify(user.getUserID());
                        System.out.println("Ya entre2");
                        showMessage(Message.MessageType.SUCCESS,"Registrado correctamente");
                        verifyCode.setVisible(false);
                        isRegistered = true;
                        System.out.println(isRegistered);
                    }else{
                        showMessage(Message.MessageType.ERROR,"Codigo de verificacion incorrecto");
                        isRegistered= false;
                    }
                }catch(Exception ae){
                    showMessage(Message.MessageType.ERROR, "Error");
                    isRegistered = false;
                }
            }
        });
    }
    private void register(){
     //loading.setVisible(true);
       //verifyCode.setVisible(true);
       ModelUserr user = loginAndRegister.getUser();
       System.out.println(user.getUserName());
       try{
           if(user.getUserName().isEmpty()){
               showMessage(Message.MessageType.ERROR,"Usuario vacio");
               return;
           }else if(user.getEmail().isEmpty()){
               showMessage(Message.MessageType.ERROR,"Correo Vacio");
               return;
           }else if(user.getPassword().isEmpty()){
               showMessage(Message.MessageType.ERROR,"Contraseña vacia");
               return;
           }
           else if(service.checkDuplicateUser(user.getUserName())){
               showMessage(Message.MessageType.ERROR,"Nombre ya existente");
           }else if(service.checkDuplicateEmail(user.getEmail())){
               showMessage(Message.MessageType.ERROR,"Correo ya existente");
           }else{   
                    service.insertUser(user);
                    if(isRegistered==false){
                        service.deleteIfStatusNull(user.getUserID());
                    }
                    service.insertUser(user);
                    
                    sendMain(user);
                    System.out.println(user.getUserID());
                    
                    /*
                    if(isRegistered==true){
                     service.insertUser(user);   
                    }else{
                        System.out.println("No ha digitado el codigo.");
                    }       
                   */
           }
       }catch(Exception e){
         e.printStackTrace();
         showMessage(Message.MessageType.ERROR,"Error al registrarse");
       }
       
    }
    


    private void sendMain(ModelUserr user) {
        /*
        final boolean[] result = {false}; // Almacenar el estado de éxito
        CountDownLatch latch = new CountDownLatch(1); // Sincronizador

        new Thread(() -> {
            try {
                loading.setVisible(true);
                ModelMessage ms = new ServiceMail().sendMain(user.getEmail(), user.getVerifyCode());
                if (ms.isSuccess()) {
                    loading.setVisible(false);
                    verifyCode.setVisible(true);
                    result[0] = true; // Indicar éxito
                } else {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, ms.getMessage());
                }
            } finally {
                latch.countDown(); // Notificar que el hilo ha terminado
            }
        }).start();

        try {
            latch.await(); // Esperar a que el hilo termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error esperando la finalización de sendMain.");
        }

        return result[0]; // Devolver el estado
        */
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setVisible(true);
                ModelMessage ms;
                System.out.println("por aqui ando");
                ms = new ServiceMail().sendMain(user.getEmail(), "Ingresa el siguiente codigo a recycling maps: \n"+user.getVerifyCode(),"Codigo de verificacion Recycling Maps");
                System.out.println("me fui");
                if (ms.isSuccess()) {
                    loading.setVisible(false);
                    verifyCode.setVisible(true);
                    
                } else {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, ms.getMessage());
                }
            }
        }).start();

    }
    private void login(){
        ModelLogin userL = loginAndRegister.getUserLogin();
        
        try{
            ModelUserr user = service.login(userL);
            if(user!=null){
                this.dispose();
                map.main(user);
            }
            else if(userL.getEmail().isEmpty()){
               showMessage(Message.MessageType.ERROR,"Usuario vacio");
               
            }else if(userL.getPassword().isEmpty()){
               showMessage(Message.MessageType.ERROR,"Contreaseña Vacia");
            }
            else{
                showMessage(Message.MessageType.ERROR, "Correo o contraseña incorrecto");
            }
            
        }catch(SQLException e){
            showMessage(Message.MessageType.ERROR, "Error en login");
        }
        
       
        
    }
    //Metodo mostrar mensaje
    private void showMessage(Message.MessageType messageType, String message){
    Message ms = new Message();
    ms.showMessage(messageType, message);
    TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isVisible()) {
                    bg.add(ms, "pos 0.5al -30", 0); //  Insert to bg fist index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isVisible()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isVisible()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setVisible(true);
                }
            }
        };
    //Animacion
    
    Animator animator = new Animator(1000, target);
    animator.setResolution(0);
    animator.setAcceleration(0.5f);
    animator.setDeceleration(0.5f);
    new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    animator.start();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCover1 = new components.PanelCover();
        bg = new javax.swing.JLayeredPane();

        javax.swing.GroupLayout panelCover1Layout = new javax.swing.GroupLayout(panelCover1);
        panelCover1.setLayout(panelCover1Layout);
        panelCover1Layout.setHorizontalGroup(
            panelCover1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelCover1Layout.setVerticalGroup(
            panelCover1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setPreferredSize(new java.awt.Dimension(933, 537));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 933, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
       DataBaseConnection dbc = new DataBaseConnection();
       dbc.conectar();
        /* 
       try{
           DataBaseConnection.getInstance().connectToDatabase();
       }catch(SQLException e){
           e.printStackTrace();
       }
*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private components.PanelCover panelCover1;
    // End of variables declaration//GEN-END:variables
}
