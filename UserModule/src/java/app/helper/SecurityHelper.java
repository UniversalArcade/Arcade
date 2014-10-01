/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class SecurityHelper {
    
    public SecurityHelper(){}
    
    public String getSHAHash(String input){
        
        
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA" );
            byte[] digest = md.digest( input.getBytes() );
            
            /*String msg = "";
            for ( byte b : digest )
            msg += b;
            */
            //return msg;
            return digest.toString();
            
            
            
            //return "";
        } catch (Exception ex) {
            Logger.getLogger(SecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return "";
    }
    
}
