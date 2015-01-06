/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.RandomAccessFile;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author Martin
 */
public class OutPipe extends Observable implements Runnable {
    
    private String message;
    private final Object lock = new Object();
    
    public OutPipe(){
       
    }
    
    public synchronized void setMessage(String input)
    {
        synchronized(lock) 
        {
            message = input + "\n";
        }
    }
    
    public String getMessage()
    {
        synchronized(lock) 
        {
            String tmp = message;
            message = null;
            return tmp;
        }
    }
    
    @Override
    public void run(){

        while(true)
        {
           
            try 
            {
                Thread.sleep(1);
                
                String sendText = getMessage();
                
                if(sendText != null && sendText.length() > 0)
                {
                    RandomAccessFile pipe = new RandomAccessFile("\\\\.\\pipe\\javaOUTpipe", "rw");
                //String echoText = packetID +":btSET:A,B,C,D," + "\n";
               
                // write to pipe
                
                    pipe.write ( sendText.getBytes() );
                    // read response

                    //String responseText = pipe.readLine();
                    //processIncomingCommand(responseText);
                    //System.out.println("Response: " + responseText );
                    pipe.close();
                    Thread.sleep(300);
                }
                
                
               
            } catch (Exception e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
