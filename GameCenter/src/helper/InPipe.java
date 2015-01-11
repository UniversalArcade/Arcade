/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.PipedInputStream;
import java.io.RandomAccessFile;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author Martin
 */
public class InPipe extends Observable implements Runnable {
    
  
    public InPipe(){
        Random rand = new Random();
    }
    
    @Override
    public void run(){
        
        while(true)
        {
            try(RandomAccessFile pipe = new RandomAccessFile("\\\\.\\pipe\\javaINpipe", "r")){

                String responseText = pipe.readLine();
                
                if(responseText.length() > 3)
                {
                    processIncomingCommand(responseText);
                }

                Thread.sleep(100); // 300
            } 
            catch (Exception e) {}
        }
    }
    
    private void processIncomingCommand(String input)
    {
        System.out.println("INPUT: " + input);
        if(input != null && input.length() > 0)
        {
            String[] parts = input.split(":");
            if(parts.length > 1)
            {
               if(parts[0] != null && parts[0].length() > 0)
               {
                    switch(parts[0])
                    {
                        case("spFunc"):
                            switch(parts[1])
                            {
                                 case("0"):
                                    setChanged(); //Observable
                                    notifyObservers("showOverlay"); 

                                    break;
                                 case("1"):
                                     setChanged(); //Observable
                                     notifyObservers("stopGame");                                        
                                     break;
                                default:
                                    //error
                                    break;
                            }
                            break;
                        case("Conn"):
                            switch(parts[1])
                            {
                                case("0"):
                                    setChanged(); //Observable
                                    notifyObservers("ConnLost"); 

                                    break;
                                 case("1"):
                                     System.out.println("SENDE CONNEST");
                                     
                                     setChanged(); //Observable
                                     notifyObservers("ConnEstablished");                                        
                                     break;
                            }
                            break;
                            
                            
                    }
               }
            }
        }
    }
}
