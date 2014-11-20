/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model;

import app.beans.Costumer;
import app.beans.User;
import app.helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author KM
 */
public class ProfilModel {
    SQLHelper sql;
    String password;
    
    public ProfilModel(){
        sql = new SQLHelper();   
    }

    public void updateUser(User u,Costumer c) throws SQLException  {

        
        sql.openCon();

          ResultSet rs = sql.execQuery("SELECT id,password FROM user WHERE id='"+u.getUserID()+"'");  
          while (rs.next()) {
            password = rs.getString("password");
            //System.out.println(password);
            //System.out.println(c.getPassword());
          }

          if(c.getPassword().equals(password)){
            sql.execNonQuery("UPDATE user SET mail ='"+c.getMail()+"' WHERE id='"+u.getUserID()+"' ");
            //System.out.println("passwort gefunden und mail geändernt");
            
          }

          else if ( !c.getPassword().equals(password)){
             //System.out.println("passwort falsch"); 
             c.addError("password", "Passwort stimmt nicht überein!");
          }
        sql.closeCon();

    }
    
    
}
