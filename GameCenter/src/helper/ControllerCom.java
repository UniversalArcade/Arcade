/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerCom {
    
    private MessageThread messageThread;
    
    public ControllerCom(){
        
    }
    
    public void sendMessage(String message){
        messageThread = new MessageThread();
        messageThread.setMessage(message);
        messageThread.start();
    }
    
    private class MessageThread extends Thread{
       Process p; 
       String message;
       
       public MessageThread(){           
       }
       
       public void setMessage(String message){
           this.message = message;
       }
        
       @Override
       public void run(){
           
        System.out.println("start");
        ProcessBuilder builder = new ProcessBuilder("python","C:\\Users\\Public\\Arcade\\serialtest.py");
        try {
            
            p = builder.start();  
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            OutputStream stdin = p.getOutputStream();

            //String hallo = "{erster:zweiter}";
            
            stdin.write(message.getBytes());
            stdin.flush();
            stdin.close();
            
             
             String ret = in.readLine();
                System.out.println("value is : "+ret);
            
              
            
        } catch (IOException ex) {
            //Logger.getLogger(ProcessBuilderPythonIO.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
    }
    
}
