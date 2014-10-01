// Trägt Bestellung in DB ein

package app.model;


import app.beans.Costumer;
import app.helper.SQLHelper;
import java.security.MessageDigest;

public class RegistrationModel {
    SQLHelper sql;
    
    public RegistrationModel(){
        sql = new SQLHelper();
    }
    
    public void newRegistration(Costumer c) throws Exception{
        sql.openCon();
            sql.execNonQuery("INSERT INTO `user` (mail,password,salt) VALUES ('"+c.getMail()+"', '"+c.getPassword()+"', '1234')");
        sql.closeCon();
    } 
}
