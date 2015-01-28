/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.exceptions;

import app.beans.Message;

/**
 *
 * @author Martin
 */
public class IllegalButtonCombinationException extends IllegalArgumentException{
    private Message message;
    
    public IllegalButtonCombinationException(){ 
        message = null;
    }
    
    public IllegalButtonCombinationException(Message m){
        message = m;
    }
    
    public Message getCustomMessage(){
        return message;
    }
}
