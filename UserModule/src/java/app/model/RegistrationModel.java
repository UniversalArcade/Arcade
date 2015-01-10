
package app.model;



import app.beans.Costumer;
import app.beans.Message;
import app.helper.SQLHelper;
import app.helper.MailHelper;
import app.helper.SecurityHelper;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistrationModel {

    
    public Costumer newRegistration(Costumer c){
        
        try(SQLHelper sql = new SQLHelper()){
            //Check if there's another user with that mail
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE mail='"+c.getMail()+"'");
            
                if( !rs.next() ){
                    
            
                    BigInteger random = new BigInteger( 49, 0, new Random() );
                    
                    

                    sql.execNonQuery("INSERT INTO `user` (mail,password,salt,registerActivationString) VALUES ('"+c.getMail()+"', '"+c.getPassword()+"', '1234','"+random+"')");

                    String uniqueURL = "http://localhost:8080/UserModule/RegistrationController?unique="+random;
                    String message = "<html><body>Vielen Dank für Ihre Registrierung bei der HAW ArcadeStation." ;
                    message += "<br/>*************************************** <br/>";
                    message += "<br/>";
                    message += "<br/>";
                    message += "<br/>Zum freischalten Ihres Accounts klicken Sie bitte auf folgenden Link ";
                    message += "<br/><a href="+uniqueURL+">Account aktivieren </a><br/>";
                    message += "<br/>";
                    message += "<br/>";
                    message += "<br/>***************************************</body></html> ";

                    MailHelper.sendMail("Ihre Registrierung bei HAWArcadeStation", c.getMail(), message );
                }
                else{
                    c.addError("mail", "E-mail-Adresse existiert bereits");
                }      
         }
         catch(SQLException e){}   

        return c;
    }

    public void activateUser(String URLString){
       
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE registerActivationString='"+URLString+"'");
        
            //System.out.println("registerActivationString: "+URLString);
            if( !rs.next() ){
                System.out.println("registerActivationString not found");                            
            } 
            else {
                sql.execNonQuery("UPDATE user SET isregistred ='1' WHERE registerActivationString='"+URLString+"' ");         
            }    
         }
         catch(SQLException e){}
         

    }
    
}
