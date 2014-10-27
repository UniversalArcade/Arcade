// Stellt app.model und admin.model SQL-Funktionen zur Verfügung

package app.helper;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLHelper {
   private Statement st;
   private Connection con;
   
   private static final Logger log = Logger.getLogger( SQLHelper.class.getName() );
   
   public SQLHelper(){}
   
   //open connection
   public void openCon(){
       try {
           Class.forName("org.gjt.mm.mysql.Driver");
           try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arcade","root","");
                try {
                    st = con.createStatement();
                } catch (SQLException ex) {
                     log.log(Level.SEVERE, "createStatement", ex);
                }
           } catch (SQLException ex) { 
               log.log(Level.SEVERE, "Drivermanager", ex);
           }
       } catch (ClassNotFoundException ex) {
           log.log(Level.SEVERE, "Class forname", ex);
       }  
   }
   
   // close connection
   public void closeCon(){
       try {
           st.close();
           con.close();
       } catch (SQLException ex) {
           log.log(Level.WARNING, "error trying to close connection", ex);
       }
   }
   
   // SELECT
   public ResultSet execQuery(String statement){
       try {
           return st.executeQuery(statement);
       } catch (SQLException ex) {
           log.log(Level.WARNING, "bad Query", ex);
       }
       return null;
   }
   
   // INSERT, UPDATE, DELETE
   public boolean execNonQuery(String statement){ 
       try {
           st.executeUpdate(statement);
           return true;
       } catch (SQLException ex) {
           log.log(Level.WARNING, "bad Query", ex);
       }
       return false;
   }
   
   // return last inserted id or -1
   public int getLastID(){
       try {
           ResultSet id = execQuery("SELECT @@IDENTITY");
           if(id.next()){
               return id.getInt(1);
           }
       } catch (SQLException ex) {
           log.log(Level.WARNING, "Theres no last ID", ex);
       }
       return -1;
   }
}
