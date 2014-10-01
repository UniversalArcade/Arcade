// Trägt Bestellung in DB ein

package app.model;

import app.beans.Costumer;
import app.helper.SQLHelper;
import java.sql.ResultSet;

public class LoginModel {
    SQLHelper sql;
    
    public LoginModel(){
        sql = new SQLHelper();
    }
    
    public int login(Costumer c) throws Exception{
       
        int id = 0;
        
        sql.openCon();
            
           
            ResultSet rs = sql.execQuery("SELECT id FROM user WHERE mail = "+ c.getMail() +" AND password = "+ c.getPassword());
            if(rs.next()){
                id = rs.getInt("id");
            }
           
        sql.closeCon();
        
        return id;
    }
    
    public void logout(){}
}
