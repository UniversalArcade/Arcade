
package app.model;



import app.beans.Costumer;
import app.beans.Message;
import app.exceptions.EmailAlreadyInUseException;
import app.helper.SQLHelper;
import app.helper.MailHelper;
import app.helper.SecurityHelper;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


public class RegistrationModel {

    
    public Costumer newRegistration(Costumer c) throws EmailAlreadyInUseException, SQLException, MessagingException{
        
        try(SQLHelper sql = new SQLHelper()){
            //Check if there's another user with that mail
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE mail='"+c.getMail()+"'");
            if(rs.next()) throw new EmailAlreadyInUseException();
            
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

            try{
                MailHelper.sendMail("Ihre Registrierung bei HAWArcadeStation", c.getMail(), message );
            }
            catch(MessagingException e){
                sql.execNonQuery("DELETE FROM user WHERE mail='" + c.getMail() + "'");
                throw new MessagingException();
            }

            c.setRegistrationComplete();
                 
        }
        return c;
    }

    public void activateUser(String URLString) throws IllegalArgumentException, SQLException, NullPointerException{
       
        if(URLString == null || URLString.length() == 0 ) throw new NullPointerException();
        
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE registerActivationString='"+URLString+"' AND isregistred=0"  );            
            if( !rs.next() ) throw new IllegalArgumentException();                            
            
            sql.execNonQuery("UPDATE user SET isregistred ='1' WHERE registerActivationString='"+URLString+"' ");         
        }        
    }    
}
