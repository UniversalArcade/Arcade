// Trägt Bestellung in DB ein

package app.model;

import app.beans.Costumer;
import app.beans.User;

import app.helper.SQLHelper;
import java.sql.ResultSet;

public class LoginModel {
    SQLHelper sql;
    
    public LoginModel(){
        sql = new SQLHelper();
    }
    
    public User login(Costumer c) throws Exception{
        
        User user = new User();
        
        sql.openCon();
            
            ResultSet rs = sql.execQuery("SELECT id, userlvl,isregistred FROM user WHERE mail='"+c.getMail()+"' AND password='"+c.getPassword()+"'");
        
            if(rs.next()){
                user.setUserID( rs.getInt("id") );
                user.setUserLvl( rs.getInt("userlvl") );
                user.setRegistred(rs.getInt("isregistred"));
                 System.out.println("Registred Status:" + user.getRegistred());
            }
           
        sql.closeCon();
        
        return user;
    }
}
