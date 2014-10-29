
package app.model;



import app.beans.Costumer;
import app.helper.SQLHelper;
import app.helper.MailHelper;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class RegistrationModel {
    SQLHelper sql;
    
   
    
    public RegistrationModel(){
        sql = new SQLHelper();
    }
    
    public Costumer newRegistration(Costumer c) throws Exception{
        sql.openCon();
            
            //Check if there's another user with that mail
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE mail='"+c.getMail()+"'");
            if( !rs.next() ){
               sql.execNonQuery("INSERT INTO `user` (mail,password,salt) VALUES ('"+c.getMail()+"', '"+c.getPassword()+"', '1234')");
               String recipent = c.getMail();
               MailHelper.MailHelper("Ihre Registrierung bei HAWArcadeStation", recipent, "Vielen Dank für Ihre Registrierung bei der HAW ArcadeStation. Zum freischalte Ihres Accounts klicken Sie bitte auf folgenden Link ....");
               
                   
            }
            else{
                c.addError("mail", "E-mail-Adresse existiert bereits");
            }
            
            
            
        sql.closeCon();
        
        return c;
    } 
    
    // einfügen updateUser () -> wenn daten geändert werden soll auch eine mail versand werden. Wenn mail geändert -> mail an alt und neu. Wenn PW geändert mail an aktuelle adresse.
    // Email Versand auch hier einfügen . Funktonen werden für den Email Versand ausgelagert in MailVersand.java in Helper
    
}
