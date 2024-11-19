package services;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.ModelMessage;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;



public class ServiceMail 
{
   
      private String subject;
      private String content;
      private ModelMessage ms;
      private Properties mProperties;
      private Session mSession;
      private MimeMessage mCorreo;

      
    public ModelMessage sendMain(String toEmail, String content, String Subject) {
        
        String emailFrom = "martinezxronald@gmail.com";
        String passwordFrom = "xbpzcriphybzoaks";
        mProperties = new Properties();
        //Protocolo de transferencia
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
        
        mSession = Session.getInstance(mProperties, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailFrom, passwordFrom);
             }
        });

        
        mCorreo = new MimeMessage(mSession);
          try {
              mCorreo.setFrom(new InternetAddress(emailFrom));
              //Le enviamos al remitente
              mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
              mCorreo.setSubject(Subject);
              //Enviamos el codigo
              mCorreo.setText(content, "ISO-8859-1","plain");           
          } catch (AddressException ex) {
              Logger.getLogger(ServiceMail.class.getName()).log(Level.SEVERE, null, ex);
             
          } catch (MessagingException ex) {
              Logger.getLogger(ServiceMail.class.getName()).log(Level.SEVERE, null, ex);
              
          }
    
        ms = new ModelMessage(false, "");  
        try {
            // Enviar el correo directamente usando Transport.send()
            Transport.send(mCorreo);
            ms.setSuccess(true);
        }catch (MessagingException ex) {
            Logger.getLogger(ServiceMail.class.getName()).log(Level.SEVERE, "Error al enviar el correo", ex);
            if (ex.getMessage().equals("Invalid Addresses")) {
                ms.setMessage("Correo inválido");
            } else {
                ms.setMessage("Error al enviar el correo");
            }
        }
          return ms;     
 }
   
}


 /*
              ModelMessage ms = new ModelMessage(false, "");
              String from = "martinezxronald@gmail.com";
              Properties prop = new Properties();
              prop.put("mail.smtp.host", "smtp.gmail.com");
              prop.put("mail.smtp.port", "587");
              prop.put("mail.smtp.auth", "true");
              prop.put("mail.smtp.starttls.enable", "true");
              String username = "martinezxronald@gmail.com";
              String password = "xbpzcriphybzoaks";    //  Your email password here
              Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(username, password);
              }
              });
              try {
              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress(from));
              message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
              message.setSubject("Verify Code");
              message.setText(code);
              Transport.send(message);
              ms.setSuccess(true);
              } catch (MessagingException e) {
              if (e.getMessage().equals("Invalid Addresses")) {
              ms.setMessage("Invalid email");
              } else {
              ms.setMessage("Error");
              }
              }
              return ms;
              
              }
          }   */