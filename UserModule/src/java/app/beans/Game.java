//beeinhaltet Kundendaten aus Formular
//Grundlegende Fehlerprüfung der eingegebenen Daten
//ggf Fehlermeldung eintragen in error HashMap

package app.beans;

import java.io.Serializable;
import java.util.HashMap;
import org.json.simple.JSONArray;

public class Game implements Serializable {
    
    private boolean inEditMode;

    
    private int gameID, permanentStore, emulationGame, gameDuration, gameStarts;

   
    private String title, credits, description, buttonConfig;
    private JSONArray filePathJSON;
    private HashMap error;
    private final String _EMPTY = "Bitte Ausfüllen!";
    
    
    
    
    
    public Game() {
        error = new HashMap();
        this.setPermanentStore(1);
        this.setEmulationGame(0);
        this.setInEditMode(false);
    }
    
    public boolean isInEditMode() {
        return inEditMode;
    }

    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
    }
    
    public String getButtonConfig() {
        return buttonConfig;
    }

    public void setButtonConfig(String buttonConfig) {
        this.buttonConfig = buttonConfig;
    }
    
    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getGameStarts() {
        return gameStarts;
    }

    public void setGameStarts(int gameStarts) {
        this.gameStarts = gameStarts;
    }
    

    public int getPermanentStore() {
        return permanentStore;
    }

    public void setPermanentStore(int permanentStore) {
        this.permanentStore = permanentStore;
    }
    
    public int getEmulationGame() {
        return emulationGame;
    }

    public void setEmulationGame(int emulationGame) {
        this.emulationGame = emulationGame;
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
        this.credits = credits;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public HashMap getErrors(){
        return error;
    }
    
    public void deleteErrors(){
        error.clear();
    }
}
