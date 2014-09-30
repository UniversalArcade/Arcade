//beeinhaltet Kundendaten aus Formular
//Grundlegende Fehlerprüfung der eingegebenen Daten
//ggf Fehlermeldung eintragen in error HashMap

package app.beans;

import java.io.Serializable;
import java.util.HashMap;

public class Game implements Serializable {
    
    private String title, credits, description;
    private HashMap error;
    private final String _EMPTY = "Bitte Ausfüllen!";
    
    public Game() {
        error = new HashMap();
        System.out.println("GAME CONSTRUCTOR");
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(!title.equals("")){
            this.title = title;
        }
        else{
            error.put("title",_EMPTY);
        }
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        if(credits.equals("")){
            System.out.println("CREDITS LEER");
            error.put("credits",_EMPTY);
        }
        else{
            System.out.println("CREDITS VOLL");
            this.credits = credits;
        }
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.equals("")){
            error.put("description",_EMPTY);                        
        }
        else{
            this.description = description;
        }
    }
    

    public HashMap getErrors(){
        return error;
    }
}
