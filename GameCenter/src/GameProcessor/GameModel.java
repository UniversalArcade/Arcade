package GameProcessor;

import helper.SQLHelper;
import helper.ControllerCom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GameModel {
    
    private GameThread gt; 
    private Thread executionThread;
    private ControllerCom controllerCom;
    private static final Logger log = Logger.getLogger( GameModel.class.getName() );
    
    
    SQLHelper sql;
    public GameModel(){
        sql = new SQLHelper();
        controllerCom = new ControllerCom();
        
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
        
        String buttonLayout = "";
        
        StringBuilder path = new StringBuilder();
        sql.openCon();
            ResultSet rs = sql.execQuery( "SELECT SpieleRoot, ExecutePath, isEmulatorGame, buttonConfig FROM generell, games WHERE games.ID = "+ id );
            //String path = "";
            try {
                if( rs.next() ){
                    buttonLayout = rs.getString("buttonConfig");
                    
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

            if(gt != null){
                System.out.println("NOT NULL!");
                
                if(gt.getGameID() != id){
                    gt.killProcess();
                    gt.interrupt();
                }
                
                if( !gt.isAlive() ){
                    System.out.println("ITS ALIVE!!");
                     gt = new GameThread( path.toString() );
                     gt.setGameID(id);
                     gt.start();
                }
            }
            else{
                 gt = new GameThread( path.toString() );
                 gt.setGameID(id);
                 gt.start();
            }
            
            System.out.println("bLay" + buttonLayout);
            ArrayList<HashMap<String,String>> buttons = (ArrayList<HashMap<String,String>>) JSONValue.parse(buttonLayout);
            System.out.println("bLay2" + buttons);
            
            
            int i = 0;
            StringBuilder controlmsg = new StringBuilder();
            controlmsg.append("{");
            for(HashMap hm: buttons){
                controlmsg.append(hm.keySet().toArray()[0]);
                
                if(++i < buttons.size()){
                    controlmsg.append("~");
                }
                
            }
            controlmsg.append("}");
            
            System.out.println("btn3: " + controlmsg.toString());
            
            
            controllerCom.sendMessage(controlmsg.toString());
            
            // TODO Zeit nehmen beenden
            // TODO bei return true (task ausgeführt und beendet) : MYSQL update der Aufrufanzahl und Aufrufdauer
            // TODO return false loggen
        }    
    }   
}
