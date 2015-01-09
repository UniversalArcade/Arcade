/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameProcessor;

import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class CheckNewGamesRunnable extends Observable implements Runnable{
    
    LinkedList ids = new LinkedList();
    
    @Override
    public void run() {
        System.out.println("IM THREAD");
        
        ids.add(311);
        ids.add(214);
        ids.add(287);
        ids.add(290);
        ids.add(292);
        ids.add(294);
        ids.add(297);
        ids.add(301);
        
        try {
            Thread.sleep(5000);
            System.out.println("gesendet");
            this.setChanged();
            this.notifyObservers(ids);
            
            while(true){Thread.sleep(1000);}
            
        } catch (InterruptedException ex) {
            
        }
        
        
        
    }
}
