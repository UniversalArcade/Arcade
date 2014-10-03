// Trägt Bestellung in DB ein

package app.model;


import app.beans.Costumer;
import app.helper.SQLHelper;
import java.security.MessageDigest;
import java.sql.ResultSet;

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
            }
            else{
                c.addError("mail", "E-mail-Adresse existiert bereits");
            }
            
            
            
        sql.closeCon();
        
        return c;
    } 
}
