package GameProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.SQLHelper;

public class CheckNewGamesRunnable extends Observable implements Runnable{
    
    LinkedList<Integer> oldIDs = new LinkedList();
    LinkedList<Integer> newIDs = new LinkedList();
    
    @Override
    public void run() {
        System.out.println("IM THREAD");
        
        oldIDs = getAllGameIDs();
        newIDs = (LinkedList<Integer>)oldIDs.clone();
        
        while(true){
            try {
                Thread.sleep(5000);
                 
                newIDs = getAllGameIDs();
                if(!newIDs.equals(oldIDs)){
                    this.setChanged();
                    this.notifyObservers("ChangedIDs");
                    oldIDs = (LinkedList<Integer>)newIDs.clone();
                }
            } catch (InterruptedException ex) {}
        }
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
}
