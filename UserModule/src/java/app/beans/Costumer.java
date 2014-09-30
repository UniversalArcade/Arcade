//beeinhaltet Kundendaten aus Formular
//Grundlegende Fehlerprüfung der eingegebenen Daten
//ggf Fehlermeldung eintragen in error HashMap

package app.beans;

import java.io.Serializable;
import java.util.HashMap;

public class Costumer implements Serializable {
    
    private String mail, password, passwordWDH;
    private HashMap error;
    private final String _EMPTY = "Bitte Ausfüllen!";
    
    public Costumer() {
        error = new HashMap();
    }
    
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if(!mail.equals("")){
            this.mail = mail;
        }
        else{
            error.put("mail",_EMPTY);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!password.equals("")){
            this.password = password;
            if(password.length() < 3){
                error.put("password", " Passwort nicht lang genug (mind 6 Zeichen) ");
            }
        }
        else{
            error.put("password",_EMPTY);
        }
    }
    
    public String getPasswordWDH() {
        return passwordWDH;
    }

    public void setPasswordWDH(String passwordWDH) {
        if(!passwordWDH.equals("")){
            //are pw's matching?
            if(passwordWDH.equals(getPassword())){
                this.passwordWDH = passwordWDH;
            }
            else{
                error.put("passwordWDH","passwörter stimmen nicht überein");
            }
        }
        else{
            error.put("passwordWDH",_EMPTY);
        }
    }
    

    public HashMap getErrors(){
        return error;
    }
}
