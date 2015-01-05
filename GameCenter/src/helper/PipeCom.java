/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.RandomAccessFile;
import java.util.Random;

/**
 *
 * @author Martin
 */
public class PipeCom implements Runnable{
    
    private int packetID;
    private String message;
    private final Object lock = new Object();
    
    public PipeCom(){
        Random rand = new Random();
        packetID = rand.nextInt(5000);
        setMessage("0");
        //message = "0:0";
    }
    
    public synchronized void setMessage(String input)
    {
        synchronized(lock) 
        {
            incrementPacketID();
            message = packetID + ":" + input + "\n";
        }
    }
    
    public String getMessage()
    {
        synchronized(lock) 
        {
            return message;
        }
    }
    
    private void incrementPacketID()
    {
        if(packetID < Integer.MAX_VALUE)
        {
            packetID++;
        }
        else
        {
            packetID = 0;
        }
    }
    
   
    @Override
    public void run(){

        while(true)
        {
            try 
            {
                RandomAccessFile pipe = new RandomAccessFile("\\\\.\\pipe\\testpipe", "rw");

                //String echoText = packetID +":btSET:A,B,C,D," + "\n";
                String sendText = getMessage();

                // write to pipe
                pipe.write ( sendText.getBytes() );

                // read response
                String responseText = pipe.readLine();
                if(responseText != null && responseText.length() > 0)
                {
                    String[] parts = responseText.split(":");
                    if(parts.length > 1)
                    {
                       if(parts[0] != null && parts[0].length() > 0)
                       {
                           int newPacketID = Integer.parseInt(parts[0]);
                           if(newPacketID != packetID)
                           {
                               //packetID = newPacketID;
                               //DO ACTION LIKE CLOSE GAME / SHOW OVERLAY
                           }
                       }
                    }
                }
                //System.out.println("Response: " + responseText );
                pipe.close();
                Thread.sleep(200);
            } catch (Exception e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
