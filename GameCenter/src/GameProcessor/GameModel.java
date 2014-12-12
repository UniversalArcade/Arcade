package GameProcessor;

import GameProcessor.Taskmanager;
import helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameModel {
  
    private static final Logger log = Logger.getLogger( GameModel.class.getName() );
    
    SQLHelper sql;
    public GameModel(){
        sql = new SQLHelper();
    }
    
    public String getGameInfoByID(int gameID){
        
        //dummy
        gameID = 214;
        
        JSONObject data = new JSONObject();
        
        SQLHelper sql = new SQLHelper();        
        sql.openCon();
            
            ResultSet rs = sql.execQuery("SELECT title,description,buttonConfig,credits FROM games WHERE ID='"+gameID+"'");
            try {
                if(rs.next()){
                    data.put("title", rs.getString("title"));
                    data.put("description", rs.getString("description"));
                    data.put("credits", rs.getString("credits"));
                    
                    String buttonList = rs.getString("buttonConfig");

                    JSONArray buttonsArray = new JSONArray();
                    for(String b : buttonList.split(";")){
                        buttonsArray.add(b);
                    } 

                    data.put("buttonConfig", buttonsArray);                        
                }
                else{
                    //error
                }
            } catch (SQLException ex) {}
           
        sql.closeCon();
        
        
        return data.toJSONString();
    }
    
    public LinkedList<Integer> getAllGameIDs(){
        
        LinkedList<Integer> idList = new LinkedList();
      
        sql.openCon();
            ResultSet rs = sql.execQuery("SELECT ID FROM games WHERE live=1");
            //String path = "";
            try {
                while( rs.next() ){
                    idList.add( rs.getInt( "ID" ) );
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "no valid resultset", ex);
            }   
        sql.closeCon();
        
        System.out.println("IDLISTE: " + idList);
       
        return idList;
    }
    
    public void executeGameByID(int id){
        
        StringBuilder path = new StringBuilder();
        sql.openCon();
            ResultSet rs = sql.execQuery( "SELECT SpieleRoot, ExecutePath, isEmulatorGame FROM generell, games WHERE games.ID = "+ id );
            //String path = "";
            try {
                if( rs.next() ){
                    path.append( rs.getString( "SpieleRoot" ) );
                     
                    
                    if(rs.getBoolean("isEmulatorGame" )){
                        path.append( "/Mame/MameStarter.exe " );
                    }
                    else{
                        path.append( "/Games/" + id + "/game/" );
                    }
                    
                    path.append( rs.getString( "ExecutePath" ) );
                    log.info("found path: " + path);
                }
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "no valid resultset", ex);
            }   
        sql.closeCon();
        
        
        if(path.length() > 0){ // TODO : bessere validierung (String kann auch nur aus SpieleRoot bestehen)
            // TODO Zeit nehmen starten
            Taskmanager task = new Taskmanager();
            task.executeGame( path.toString() );
            // TODO Zeit nehmen beenden
            // TODO bei return true (task ausgeführt und beendet) : MYSQL update der Aufrufanzahl und Aufrufdauer
            // TODO return false loggen
        }    
    }   
}
