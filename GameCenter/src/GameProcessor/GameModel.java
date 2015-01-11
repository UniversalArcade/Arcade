package GameProcessor;

import helper.SQLHelper;
import helper.ControllerCom;
import helper.InPipe;
import helper.OutPipe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GameModel implements Observer{
    
    private GameRunnable gt;
    private OverlayThread ot;
    private Thread executionThread;
    private ControllerCom controllerCom;
    private static final Logger log = Logger.getLogger( GameModel.class.getName() );
    private InPipe inpipe;
    private OutPipe outPipe;    
    private String buttonFuncString;
    
   
    public GameModel(){

        buttonFuncString = "";        
        inpipe = new InPipe();
        inpipe.addObserver(this);
        Thread inpipeThread = new Thread(inpipe);
        inpipeThread.setDaemon(true);
        inpipeThread.start();
        
        outPipe = new OutPipe();        
        Thread outPipeThread = new Thread( outPipe );
        outPipeThread.setDaemon(true);
        outPipeThread.start();        
    }
    
    public String getGameInfoByID(int gameID){
        
        //dummy
        gameID = 214;
        
        JSONObject data = new JSONObject();
        
        try(SQLHelper sql = new SQLHelper()){
             ResultSet rs = sql.execQuery("SELECT title,description,buttonConfig,credits FROM games WHERE ID='"+gameID+"'");
            
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
        }
        catch (SQLException ex) {} 
        
        return data.toJSONString();
    }
    
    public LinkedList<Integer> getAllGameIDs(){
        
        LinkedList<Integer> idList = new LinkedList();
      
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery("SELECT ID FROM games WHERE live=1 ORDER BY ID DESC");

            while( rs.next() ){
                idList.add( rs.getInt( "ID" ) );
            }
        }
        catch (SQLException ex) {} 
        
        return idList;
    }
    
    public HashMap<String,String> getAllGameTitles(){
        
        HashMap<String,String> map = new HashMap();
        
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery("SELECT ID, title FROM games WHERE live=1 ORDER BY ID DESC");
          
            while( rs.next() ){
                map.put(String.valueOf(rs.getInt( "ID" )), rs.getString( "title" ));
            }
        }
        catch (SQLException ex) {} 

        return map;
    }
    
    public void executeGameByID(int id){
        
        String buttonLayout = "";
        int permanentStore = 1;
        
        StringBuilder path = new StringBuilder();
        
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery( "SELECT title, SpieleRoot, ExecutePath, isEmulatorGame, buttonConfig, permanentStore FROM generell, games WHERE games.ID = "+ id );
            if( rs.next() ){
                    buttonLayout = rs.getString("buttonConfig");
                    permanentStore = rs.getInt("permanentStore");
                    path.append( rs.getString( "SpieleRoot" ) );    
                    
                    if(rs.getBoolean("isEmulatorGame" )){
                        path.append( "/Mame/MameStarter.exe " );
                        path.append( rs.getString( "title" ) + " ");
                        path.append( id );
                    }
                    else{
                        path.append( "/Games/" + id + "/game/" );
                        path.append( rs.getString( "ExecutePath" ) );
                    }
                    
                    if(path.length() > 0){ // TODO : bessere validierung (String kann auch nur aus SpieleRoot bestehen)

                if(gt != null){
                    System.out.println("NOT NULL!");

                    if(gt.getGameID() != id){
                        killGameThread();
                    }

                    //if( ! (gt.isAlive()) ){
                    if( ! (executionThread == null || executionThread.isAlive()) ){
                        System.out.println("ITS ALIVE!!");
                         gt = new GameRunnable( path.toString(), permanentStore );
                         gt.addObserver(this);
                         gt.setGameID(id);
                         executionThread = new Thread( gt );
                         executionThread.start();
                    }
                }
                else{
                     gt = new GameRunnable( path.toString(), permanentStore );
                     gt.addObserver(this);
                     gt.setGameID(id);
                     executionThread = new Thread( gt );
                     executionThread.start();
                }

                System.out.println("bLay" + buttonLayout);
                ArrayList<HashMap<String,String>> buttons = (ArrayList<HashMap<String,String>>) JSONValue.parse(buttonLayout);
                System.out.println("bLay2" + buttons);

                StringBuilder buttonFunc = new StringBuilder();
                StringBuilder controlmsg = new StringBuilder();
                controlmsg.append("btSET:");
                for(HashMap hm: buttons){
                    controlmsg.append(hm.keySet().toArray()[0]);
                    controlmsg.append(",");
                    buttonFunc.append(hm.values().toArray()[0]);
                    buttonFunc.append(",");
                }

                String buttonConfig = controlmsg.toString();
                buttonConfig = buttonConfig.replace("unused", "0");


                System.out.println("btn3: " + buttonConfig);
                outPipe.setMessage(buttonConfig);



                buttonFuncString = buttonFunc.toString();
                System.out.println("ButtonFunc: " + buttonFunc.toString());

                }
            }  
        }
        catch (SQLException ex) {}
    }
    
    private void killGameThread()
    {
        if(executionThread != null && executionThread.isAlive())
        {
            if(gt != null)
            {
                gt.killProcess();
            }
            executionThread.interrupt();
        }
    }

    private void killOverlay()
    {
        if(ot != null && ot.isAlive())
        {
            ot.killProcess();
            ot.interrupt();
        }
    }
    
    private void showOverlay()
    {
        if(ot != null && ot.isAlive())
        {
            killOverlay();
        }
        else
        {
            if(gt != null && executionThread != null && executionThread.isAlive())
            {
                ot = new OverlayThread(buttonFuncString);
                ot.start();
            }
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        String toDo = (String)arg;
        switch(toDo)
        {
            case("stopGame"):
                killOverlay();
                
                if(gt != null && executionThread != null && executionThread.isAlive()){
                    //gt.killProcess();
                    //gt.interrupt();
                    killGameThread();
                }
                //outPipe.setMessage("btSET:0,0,D,A,ENTER,ENTER,ENTER,ENTER,ENTER,ENTER,");
                break;
            case("showOverlay"):
                showOverlay();
                break;
            case("gameStopped"):
                killOverlay();
                outPipe.setMessage("btSET:0,0,D,A,ENTER,ENTER,ENTER,ENTER,ENTER,ENTER,");
                break;
            default:
                break;
        }
    }
}
