package app.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    public static String getSHAHash(String input){
        
        
        try {
            String password =input ;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            
            byte byteData[] = md.digest();
            
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            System.out.println("Hex format : " + sb.toString());
            return sb.toString();
         
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    return "";
    }
    
}
