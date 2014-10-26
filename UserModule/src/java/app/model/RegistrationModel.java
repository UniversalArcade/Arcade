
package app.model;



import app.beans.Costumer;
import app.helper.SQLHelper;
import app.helper.MailHelper;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class RegistrationModel {
    SQLHelper sql;
    MailHelper mhp;
    String mail ,fromEmail , password, toEmail;
   
    
    public RegistrationModel(){
        sql = new SQLHelper();
    }
    
    public Costumer newRegistration(Costumer c) throws Exception{
        sql.openCon();
            
            //Check if there's another user with that mail
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE mail='"+c.getMail()+"'");
            if( !rs.next() ){
               sql.execNonQuery("INSERT INTO `user` (mail,password,salt) VALUES ('"+c.getMail()+"', '"+c.getPassword()+"', '1234')");
               //MailVersand TLS
                    fromEmail = "hawarcadestation@googlemail.com"; 
                    password = "hawarcade"; 
                    toEmail = c.getMail();              
                    //System.out.println("TLSEmail Start");
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
                    props.put("mail.smtp.port", "587"); //TLS Port
                    props.put("mail.smtp.auth", "true"); //enable authentication
                    props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
                    Authenticator auth = new Authenticator() {                              
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(fromEmail, password);
                                }
                            };
                    Session session = Session.getInstance(props, auth);
                    MailHelper.sendEmail(session, toEmail,"Ihre Registrierung bei HAWArcadeStation", "Sie haben sich bei der HAW Aracde Station registriert. Bitte klicken Sie auf folgenden Link um die Registrierung abzuschließen.");
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
