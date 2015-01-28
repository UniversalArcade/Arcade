package GameProcessor;

import java.io.IOException;


public class OverlayThread extends Thread{
     
     private Process p;
     private String parameters;

     public OverlayThread( String parameters ){
         this.parameters = parameters;
     }
     
     @Override
     public void run(){
         
        //processStarted = false; 
        ProcessBuilder builder = new ProcessBuilder("gameoverlay.exe", parameters);
        try {
            p = builder.start(); // start the Game
            p.waitFor(); // hält den Thread solange an wie process läuft
        } 
        catch (IOException | InterruptedException ex) {}
        
    }
    
    public void killProcess(){
        if( p.isAlive() ){
            p.destroyForcibly();
        }
    }
}
