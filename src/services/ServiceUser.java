package services;
import Connection.DataBaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import model.ModelUserr;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Random;
import model.ModelLogin;

public class ServiceUser {
    private final Connection con;
    
    public ServiceUser() {
        con = DataBaseConnection.conectar();
    }
    public ModelUserr login (ModelLogin login) throws SQLException{
        ModelUserr data = null;
        PreparedStatement p = con.prepareStatement("select UserID, UserName, Email from `user` where BINARY(Email)=? and BINARY (`Password`)=? and `Status`='Verified' limit 1");
        p.setString(1, login.getEmail());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if(r.next()){
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String Email = r.getString(3);
            data = new ModelUserr(userID,userName,Email,"");
        }
        r.close();
        p.close();
        return data;
    }
    public void insertUser(ModelUserr user) throws SQLException{
        
        PreparedStatement p = con.prepareStatement("insert into `user` (UserName,Email,`Password`,VerifyCode) values (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
        String code=generateVerifyCode();
        p.setString(1,user.getUserName());
        p.setString(2,user.getEmail());
        p.setString(3,user.getPassword());
        p.setString(4,code);
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        r.next();
        int userID=r.getInt(1);
        r.close();
        p.close();
        user.setUserID(userID);
        user.setVerifyCode(code);
        
        
    }
    public void deleteIfStatusNull(int userID) {
    try {
        // Prepara la consulta para eliminar el registro si el Status es NULL
        PreparedStatement p = con.prepareStatement(
            "DELETE FROM `user` WHERE UserID = ? AND Status IS NULL"
        );
        p.setInt(1, userID); // Establece el parámetro de la consulta

        // Ejecuta la consulta
        int rowsAffected = p.executeUpdate();

        // Verifica si se eliminó algún registro
        if (rowsAffected > 0) {
            System.out.println("Eliminado");
        } else {
            System.out.println("No encontrado");
        }

        // Cierra el PreparedStatement
        p.close();
    } catch (Exception e) {
        e.printStackTrace(); // Manejo de excepciones
    }
}
    private String generateVerifyCode()throws SQLException{
        DecimalFormat df = new DecimalFormat("0000000");
        Random ran = new Random();
        String code = df.format(ran.nextInt(1000000)); //Random numer form 0 to 999999 (this is for the code)
        while(checkDuplicateCode(code)){
            code = df.format(ran.nextInt(1000000));
        }
        return code;
    }
    
    private boolean checkDuplicateCode(String code)throws SQLException{
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select UserID from `user` where VerifyCode=? limit 1");
        p.setString(1, code);
        ResultSet r = p.executeQuery();
        if(r.next()){
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
        
    }
    
    public boolean checkDuplicateEmail(String user) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select UserID from `user` where Email=? and `Status`='Verified' limit 1");
        p.setString(1, user);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    
    public boolean checkDuplicateUser(String user)throws SQLException{
        boolean duplicate  = false;
        PreparedStatement p = con.prepareStatement("select UserID from `user` where UserName=? and `Status`='Verified' limit 1");
        p.setString(1, user);
        ResultSet r = p.executeQuery();
        if(r.next()){
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    
    public void doneVerify(int userID) throws SQLException{
        PreparedStatement p = con.prepareStatement("update `user` set VerifyCode='', `Status`='Verified' where UserID=? limit 1");
        p.setInt(1, userID);
        p.execute();
        p.close();
    }
    public boolean verifyCodeWithUser (int userID, String code) throws SQLException{
        boolean verify = false;
        PreparedStatement p = con.prepareStatement("select UserID from `user` where UserID=? and VerifyCode=? limit 1");
        p.setInt(1, userID);
        p.setString(2, code);
        ResultSet r = p.executeQuery();
        if(r.next()){
            verify=true;
        }
        r.close();
        p.close();
        return verify;
    }
    public String findPasswordByEmail(String email) {
        String password = null;
        try {
            // Prepara la consulta SQL
            PreparedStatement p = con.prepareStatement(
                "SELECT Password FROM `user` WHERE Email = ? LIMIT 1"
            );
            p.setString(1, email); // Establece el parámetro del correo

            // Ejecuta la consulta
            ResultSet r = p.executeQuery();

            // Si encuentra un resultado, obtiene la contraseña
            if (r.next()) {
                password = r.getString("Password");
            }

            // Cierra el ResultSet y el PreparedStatement
            r.close();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password; // Retorna la contraseña o null si no encontró
    }
}
