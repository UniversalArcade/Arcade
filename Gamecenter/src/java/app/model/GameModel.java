package app.model;

import GameProcessor.Taskmanager;
import helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameModel {
  
    private static final Logger log = Logger.getLogger( GameModel.class.getName() );
    
    SQLHelper sql;
    public GameModel(){
        sql = new SQLHelper();
    }
    
    public void getGameInfoByID(int id){
        // MYSQL Daten von Game x abrufen
        // return Dataset
    }
    
    public void getAllGameNames(){
        // MYSQL Daten aller Games abrufen (ID,Name)
        // return Dataset
    }
    
    public void executeGameByID(int id){
        
        //get path of game with id 'id' 
        
        
        sql.openCon();
            ResultSet rs = sql.execQuery( "SELECT CONCAT(SpieleRoot, ExecutePath) AS ExecutePath FROM generell, games WHERE games.id = "+ id );
            String path = "";
            try {
                rs.next();
                path = rs.getString( "ExecutePath" );
                log.info("found path: " + path);
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "no valid resultset", ex);
            }
        sql.closeCon(); 
        
        /*
        String path = "";
        
        if(id == 1){
            path = "C:/Users/Public/Arcade/Mame/startLinkForDefender.bat";
        }
        else if(id == 2){
            path = "C:/Users/Public/Arcade/Mame/startLinkForDigDug.bat";
        }*/
        
        
        if(path.length() > 0){ // TODO : bessere validierung (String kann auch nur aus SpieleRoot bestehen)
            // TODO Zeit nehmen starten
            Taskmanager task = new Taskmanager();
            task.executeGame( path );
            // TODO Zeit nehmen beenden
            // TODO bei return true (task ausgeführt und beendet) : MYSQL update der Aufrufanzahl und Aufrufdauer
            // TODO return false loggen
        }    
    }   
}
