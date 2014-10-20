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
    private boolean registrationComplete;

   
    
    public Costumer() {
        this.registrationComplete = false;
        this.error = new HashMap();
    }
    
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        //TODO regex auf @haw-hamburg.de
        //ein@ im gesamten string
        //keine sonderzeichen außer . -_
        //haw mail sowieso nur vorname.nachname@haw-hambur.de
        //außer bei doppelten Namen...konvention???
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
        // code strippen (html tags...)
        // anzahl zeichen 6 - 30
        // mindestens ein sonderzeichen
        // mindestens eine zahl
        // passwortcheck als JS im Frontend (trivial)
        
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
    
    public boolean isRegistrationComplete() {
        return registrationComplete;
    }

    public void setRegistrationComplete() {
        this.registrationComplete = true;
    }
    
   
    public void addError(String key, String value){
        error.put(key,value);
    }
    
    public HashMap getErrors(){
        return error;
    }
    
    
}
