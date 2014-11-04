
package app.model;



import app.beans.Costumer;
import app.helper.SQLHelper;
import app.helper.MailHelper;
import app.helper.SecurityHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistrationModel {
    SQLHelper sql;
    
   
    
    public RegistrationModel(){
        sql = new SQLHelper();   
    }
    
    public Costumer newRegistration(Costumer c){
        sql.openCon();
           
            //Check if there's another user with that mail
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE mail='"+c.getMail()+"'");
            try {
                if( !rs.next() ){

                    //SecurityHelper.createRandom();

                    Random ran = new Random();
                    int random = 100000 + ran.nextInt(900000);


                    sql.execNonQuery("INSERT INTO `user` (mail,password,salt,randomValue) VALUES ('"+c.getMail()+"', '"+c.getPassword()+"', '1234','"+random+"')");

                    String uniqueURL = "http://localhost:8080/UserModule/RegistrationController?unique="+random;
                    String message = "Vielen Dank für Ihre Registrierung bei der HAW ArcadeStation." ;
                    message += "<br/>*************************************** <br/>";
                    message += "<br/>Folgenden Daten sind von Ihnen hinterlegt : " ;
                    message += "<br/>Email ="+ c.getMail()+"";
                    message += "<br/>Zum freischalten Ihres Accounts klicken Sie bitte auf folgenden Link ";
                    message += "<br/><a href="+uniqueURL+">Account aktivieren </a><br/>";
                    message += "<br/>*************************************** ";

                    MailHelper.sendMail("Ihre Registrierung bei HAWArcadeStation", c.getMail(), message );
                }
                else{
                    c.addError("mail", "E-mail-Adresse existiert bereits");
                }      
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        sql.closeCon();
        
        return c;
    }
    
    /**
     *
     * @param URLString
     * @throws Exception
     */
    public void activateUser(String URLString){
       sql.openCon(); 
        ResultSet rs = sql.execQuery("SELECT id FROM user WHERE randomValue='"+URLString+"'");
        try {
            //System.out.println("randomValue: "+URLString);
            if( !rs.next() ){
                System.out.println("RandomValue not found");
            } 
            else {
                sql.execNonQuery("UPDATE user SET isregistred ='1' WHERE randomValue='"+URLString+"' ");
            }      
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       sql.closeCon();
    }
    // einfügen updateUser () -> wenn daten geändert werden soll auch eine mail versand werden. Wenn mail geändert -> mail an alt und neu. Wenn PW geändert mail an aktuelle adresse.
    // Email Versand auch hier einfügen . Funktonen werden für den Email Versand ausgelagert in MailVersand.java in Helper
    
}
