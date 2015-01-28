// Trägt Bestellung in DB ein

package app.model;

import app.beans.Costumer;
import app.beans.User;

import app.helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
   
   
    public User login(Costumer c) throws SQLException, IllegalArgumentException, SecurityException{
        
        User user = new User();
            try(SQLHelper sql = new SQLHelper()){
                ResultSet rs = sql.execQuery("SELECT id, userlvl,isregistred FROM user WHERE mail='"+c.getMail()+"' AND password='"+c.getPassword()+"'");
                if(!rs.next()) throw new IllegalArgumentException();
                else
                {
                    if(rs.getInt("isregistred") == 0) throw new SecurityException();
                    user.setRegistred(rs.getInt("isregistred"));
                    user.setUserID( rs.getInt("id") );
                    user.setUserLvl( rs.getInt("userlvl") );
                }
            }
        return user;
    }
}
