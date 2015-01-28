package GameProcessor;

import java.io.IOException;
import java.util.Observable;


public class GameRunnable extends Observable implements Runnable{
     
     private Process p;
     //rename to running
     private boolean processStarted;
     private String executionPath;
     private int gameID;
     private String permanentStore;
     
     public GameRunnable( String executionPath, int permanentStore){
         this.executionPath = executionPath;
         this.setPermanentStore(permanentStore);
     }
     
     private void setPermanentStore(int permanentStore){
        switch(permanentStore){
            case 0: 
                this.permanentStore = "ArcadeStore";
                break;
            case 1:
                this.permanentStore = "ArcadeNonStore";
                break;
        }
     }
     
     private String getPermanentStore(){
         return this.permanentStore;
     }
     
     @Override
     public void run(){

        //ProcessBuilder builder = new ProcessBuilder("C:/Users/Public/Arcade/starter.exe", executionPath, "/box:"+ getPermanentStore() +" /nosbiectrl /silent");
        ProcessBuilder builder = new ProcessBuilder("starter.exe", executionPath, "/box:"+ getPermanentStore() +" /nosbiectrl /silent");
        try {
            p = builder.start(); // start the Game
            p.waitFor(); // hält den Thread solange an wie process läuft ---> liefert auch rückgabewert?! vielleicht exitValue unnötig
            setChanged(); //Observable
            notifyObservers("gameStopped"); 
        } catch ( InterruptedException ex ) {

        }
        catch (IOException ex) {

        }
    }
    
    public void killProcess(){
        if( p.isAlive() ){
            p.destroyForcibly();
        }
    }
    
    public void setGameID(int id){
        this.gameID = id;
    }
    
    public int getGameID(){
        return this.gameID;
    }
    
    // rename to running
    public boolean isProcessStarted(){
        return processStarted;
    }
}
