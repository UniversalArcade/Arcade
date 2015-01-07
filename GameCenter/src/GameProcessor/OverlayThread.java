//TODO:  Observer Pattern

package GameProcessor;

import java.io.IOException;


public class OverlayThread extends Thread{
     
     private Process p;
     //rename to running
     private boolean processStarted;
     private String parameters;
     private int gameID;
     
     
     public OverlayThread( String parameters ){
         this.parameters = parameters;
     }
     
     public void run(){
         
        //processStarted = false; 
        ProcessBuilder builder = new ProcessBuilder("C:/Users/Public/Arcade/Gameoverlay/gameoverlay.exe", parameters);
        try {
            p = builder.start(); // start the Game
            
            //processStarted = true;
            System.out.println( "Process gestarted" );
            
            try {
                int returnval = p.waitFor(); // hält den Thread solange an wie process läuft ---> liefert auch rückgabewert?! vielleicht exitValue unnötig
                //processStarted = false;
                System.out.println( "Overlay waitfor " + returnval );
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
            //p.destroy(); // close process
            p.destroyForcibly();
            System.out.println("Overlay terminated");
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
