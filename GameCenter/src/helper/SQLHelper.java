// Stellt app.model und admin.model SQL-Funktionen zur Verfügung

package helper;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLHelper implements java.lang.AutoCloseable{
   private Statement st;
   private Connection con;
   
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
        return st.executeQuery(statement);
   }
   
   // INSERT, UPDATE, DELETE
   public void execNonQuery(String statement) throws SQLException{ 
        st.executeUpdate(statement);
   }
   
   // return last inserted id or -1
   public int getLastID() throws SQLException{
        ResultSet id = execQuery("SELECT @@IDENTITY");
        if(id.next()){
            return id.getInt(1);
        }
        return -1;
   }

    @Override
    public void close() throws SQLException {
        st.close();
        con.close();
    }
}
