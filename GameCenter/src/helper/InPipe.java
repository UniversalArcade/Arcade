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
    
  
    private final Object lock = new Object();
    
    public InPipe(){
        Random rand = new Random();
    }
    
    @Override
    public void run(){
        
        while(true)
        {
            try 
            {
                RandomAccessFile pipe = new RandomAccessFile("\\\\.\\pipe\\javaINpipe", "r");
                //String echoText = packetID +":btSET:A,B,C,D," + "\n";
                //String sendText = getMessage();
                // write to pipe
                //pipe.write ( sendText.getBytes() );
                // read response
                
                
                
                //System.out.println("INPIPE: warte auf Message");
                //System.out.println("Pipe groesse:  " + pipe.length());
                
                    String responseText = pipe.readLine();
                    //System.out.println("INPIPE: Habe Message: " + responseText);
                    
                    if(responseText.length() > 3)
                    {
                         //System.out.println("Verarbeite Message");
                        processIncomingCommand(responseText);
                       
                    }
                    
               
                
                //if(pipe.length())
                
                
                //processIncomingCommand(responseText);
                //System.out.println("Response: " + responseText );
                pipe.close();
                Thread.sleep(200); // 300
            } catch (Exception e) {
            // TODO Auto-generated catch block
                
            }
        }
    }
    
    private void processIncomingCommand(String input)
    {
        
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
                        default:
                            System.out.println("666:");
                            //error
                            break;
                    }
               }
            }
        }
    }
}
