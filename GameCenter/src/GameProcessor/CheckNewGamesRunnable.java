/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.SQLHelper;

/**
 *
 * @author Martin
 */
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
                    System.out.println("gesendet");
                    this.setChanged();
                    this.notifyObservers("ChangedIDs");
                    oldIDs = (LinkedList<Integer>)newIDs.clone();
                }
            } catch (InterruptedException ex) {}
        }
    }
    
    public LinkedList<Integer> getAllGameIDs(){
        
        SQLHelper sql = new SQLHelper();
        LinkedList<Integer> idList = new LinkedList();
      
        sql.openCon();
            ResultSet rs = sql.execQuery("SELECT ID FROM games WHERE live=1 ORDER BY ID DESC");
            //String path = "";
            try {
                while( rs.next() ){
                    idList.add( rs.getInt( "ID" ) );
                }
            } catch (SQLException ex) {}   
        sql.closeCon();
        
        System.out.println("IDLISTE CheckThread: " + idList);
       
        return idList;
    }
}
