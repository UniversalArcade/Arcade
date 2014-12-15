//TODO:  Observer Pattern

package GameProcessor;

import java.io.IOException;


public class GameThread extends Thread{
     
     private Process p;
     //rename to running
     private boolean processStarted;
     private String executionPath;
     private int gameID;
     
     
     public GameThread( String executionPath ){
         this.executionPath = executionPath;
     }
     
     public void run(){
         
        //processStarted = false; 
        ProcessBuilder builder = new ProcessBuilder("C:/Users/Public/Arcade/starter.exe", executionPath, "/box:ArcadeStore /nosbiectrl /silent");
        try {
            p = builder.start(); // start the Game
            
            //processStarted = true;
            System.out.println( "Process gestarted" );
            
            try {
                int returnval = p.waitFor(); // hält den Thread solange an wie process läuft ---> liefert auch rückgabewert?! vielleicht exitValue unnötig
                //processStarted = false;
                System.out.println( "WaitForValue " + returnval );
                //System.out.println( "Exitvalue " + p.exitValue() );  // wenn Programm normal beendet dann exitvalue = 0
            } catch ( InterruptedException ex ) {
                System.out.println( "ERROR bei waitfor()" );
                //processStarted = false;
            }
            
        } catch (IOException ex) {
            System.out.println( "ERROR Process nicht gestartet" );
            //processStarted = false;
            // Wird auch aufgerufen bei File not found...
        }
    }
    
    public void killProcess(){
        if( p.isAlive() ){
            p.destroy(); // close process
            //p.destroyForcibly();
            System.out.println("terminated");
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
