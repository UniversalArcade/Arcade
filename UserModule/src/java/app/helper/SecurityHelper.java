package app.helper;

import java.security.MessageDigest;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class SecurityHelper {
    int random;
    
    /**
     *
     * @return
     */
    public Integer createRandom(){
        Random ran = new Random();
        random = 100000 + ran.nextInt(900000);  
        String randomValue = Integer.toString(random);
        System.out.println("RandomValue "+randomValue);  
        return random;
    }
    
    
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
