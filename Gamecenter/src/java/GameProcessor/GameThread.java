//TODO:  Observer Pattern

package GameProcessor;

import java.io.IOException;


public class GameThread extends Thread{
     
     private Process p;
     //rename to running
     private boolean processStarted;
     private String executionPath; 
     
     
     public GameThread( String executionPath ){
         this.executionPath = executionPath;
     }
     
     public void run(){
         
        processStarted = false; 
        ProcessBuilder builder = new ProcessBuilder( executionPath );
        try {
            p = builder.start(); // start the Game
            
            processStarted = true;
            System.out.println( "Process gestarted" );
            
            try {
                int returnval = p.waitFor(); // hält den Thread solange an wie process läuft ---> liefert auch rückgabewert?! vielleicht exitValue unnötig
                processStarted = false;
                System.out.println( "WaitForValue " + returnval );
                System.out.println( "Exitvalue " + p.exitValue() );  // wenn Programm normal beendet dann exitvalue = 0
            } catch ( InterruptedException ex ) {
                System.out.println( "ERROR bei waitfor()" );
            }
            
        } catch (IOException ex) {
            System.out.println( "ERROR Process nicht gestartet" );
            // Wird auch aufgerufen bei File not found...
        }
    }
    
    public void killProcess(){
        if( isProcessStarted() ){
            p.destroy(); // close process
            System.out.println("terminated");
        }
    }
    
    // rename to running
    public boolean isProcessStarted(){
        return processStarted;
    }
}
