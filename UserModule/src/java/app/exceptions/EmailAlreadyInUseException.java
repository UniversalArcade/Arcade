/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.exceptions;

/**
 *
 * @author Martin
 */
public class EmailAlreadyInUseException extends SecurityException{
    
    public EmailAlreadyInUseException(){}
    public EmailAlreadyInUseException(String message){
        super(message);
    }
}
