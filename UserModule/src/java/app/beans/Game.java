//beeinhaltet Kundendaten aus Formular
//Grundlegende Fehlerprüfung der eingegebenen Daten
//ggf Fehlermeldung eintragen in error HashMap

package app.beans;

import java.io.Serializable;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class Game implements Serializable {
    
    private int gameID, newGameStep;
    private String title, credits, description;
    private JSONArray filePathJSON;
    private HashMap error;
    private final String _EMPTY = "Bitte Ausfüllen!";
    
    public Game() {
        newGameStep = 1;
        error = new HashMap();
    }
    
    public void reset(){
        this.gameID = -1;
        this.newGameStep = 1;
        this.title = "";
        this.credits = "";
        this.description = "";
        this.error.clear();
    }
    
    public JSONArray getFilePathJSON() {
        return filePathJSON;
    }

    public void setFilePathJSON(JSONArray filePathJSON) {
        this.filePathJSON = filePathJSON;
    }
    
     public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        if(gameID > 0){
            this.gameID = gameID;
        }
        else{
            error.put("gameID", "Fehler beim erstellen des Spiels");
        }
    }
    
    public int getNewGameStep() {
        return newGameStep;
    }

    public void setNewGameStep(int newGameStep) {
        this.newGameStep = newGameStep;
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
    
    public void deleteErrors(){
        error.clear();
    }
}
