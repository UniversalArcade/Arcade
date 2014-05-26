// Observer Pattern

package GameProcessor;


public class Taskmanager {

    public void executeGame( String executionPath ) {
        GameThread gt = new GameThread( executionPath );
        gt.start();
        
        
        // ändern buttonbelegung Controller
        
        // Warten auf Abbruch vom Controller oder Abbruch durch Game
    }
}

