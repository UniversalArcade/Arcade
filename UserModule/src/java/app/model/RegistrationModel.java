
package app.model;



import app.beans.Costumer;
import app.helper.SQLHelper;
import app.helper.MailHelper;
import app.helper.SecurityHelper;
import java.sql.ResultSet;
import java.util.Random;


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
               
            //SecurityHelper.createRandom();
                   
               Random ran = new Random();
               int random = 100000 + ran.nextInt(900000);  
                 
                         
               sql.execNonQuery("INSERT INTO `user` (mail,password,salt,randomValue) VALUES ('"+c.getMail()+"', '"+c.getPassword()+"', '1234','"+random+"')");
               String recipent = c.getMail();
               String uniqueURL = "http://localhost:8080/UserModule/RegistrationController?unique="+random;
               String message = "Vielen Dank für Ihre Registrierung bei der HAW ArcadeStation. Zum freischalten Ihres Accounts klicken Sie bitte auf folgenden Link ....";
               message += "<a href="+uniqueURL+">Hier klicken</a>";
                
               MailHelper.sendMail("Ihre Registrierung bei HAWArcadeStation", recipent, message );                   
            }       
            else{
                c.addError("mail", "E-mail-Adresse existiert bereits");
            }      
        sql.closeCon();
        
        return c;
    }
    
    /**
     *
     * @param URLString
     * @throws Exception
     */
    public void activateUser(String URLString)throws Exception{
       sql.openCon(); 
        ResultSet rs = sql.execQuery("SELECT id FROM user WHERE randomValue='"+URLString+"'");
        System.out.println("randomValue: "+URLString);
            if( !rs.next() ){                    
                System.out.println("RandomValue not found");
            } 
            else {
                sql.execNonQuery("UPDATE user SET istregistriert ='1' WHERE randomValue='"+URLString+"' ");      
        }      
       sql.closeCon();
    }
    // einfügen updateUser () -> wenn daten geändert werden soll auch eine mail versand werden. Wenn mail geändert -> mail an alt und neu. Wenn PW geändert mail an aktuelle adresse.
    // Email Versand auch hier einfügen . Funktonen werden für den Email Versand ausgelagert in MailVersand.java in Helper
    
}
