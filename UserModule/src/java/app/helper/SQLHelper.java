// Stellt app.model und admin.model SQL-Funktionen zur Verfügung

package app.helper;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLHelper implements java.lang.AutoCloseable{
   private Statement st;
   private Connection con;
   
   private static final Logger log = Logger.getLogger( SQLHelper.class.getName() );
   
   
   public SQLHelper() throws SQLException {
       openCon();
   }
   
   //open connection
   public void openCon() throws SQLException {
       try {
           Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/arcade","root","");
            st = con.createStatement();
       } catch (ClassNotFoundException ex) {
           throw new SQLException("Konnte Verbindung zur Datenbank nicht herstellen");
       }
   }
   

   
   // SELECT
   public ResultSet execQuery(String statement) throws SQLException{
       //try {
           return st.executeQuery(statement);
       //} catch (SQLException ex) {
           //log.log(Level.WARNING, "bad Query", ex);
       //}
       //return null;
   }
   
   // INSERT, UPDATE, DELETE
   public void execNonQuery(String statement) throws SQLException{ 
       try {
           st.executeUpdate(statement);
           //return true;
       } catch (SQLException ex) {
         //  log.log(Level.WARNING, "bad Query", ex);
         throw new SQLException("Konnte Query nicht durchführen");  
       }
       //return false;
   }
   
   // return last inserted id or -1
   public int getLastID() throws SQLException{
       //try {
           ResultSet id = execQuery("SELECT @@IDENTITY");
           if(id.next()){
               return id.getInt(1);
           }
       //} catch (SQLException ex) {
        //   log.log(Level.WARNING, "Theres no last ID", ex);
       //}
       return -1;
   }

    @Override
    public void close() throws SQLException {
        //try {
           st.close();
           con.close();
       //} catch (SQLException ex) {
         //  log.log(Level.WARNING, "error trying to close connection", ex);
       //}
           
           
           
        //System.out.println("MyAutoClosable closed!");
        //throw new Exception(); 
        //this.closeCon();
        //st.close();
        //con.close();
    }
}
