//beeinhaltet Kundendaten aus Formular
//Grundlegende Fehlerprüfung der eingegebenen Daten
//ggf Fehlermeldung eintragen in error HashMap

package app.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Game implements Serializable {
    
    private boolean inEditMode;
    private int gameID, permanentStore, emulationGame, gameDuration, gameStarts, life;
    private String title, credits, description, fullFilePath;

   
    private JSONArray filePathJSON;
    private HashMap error;
    //private LinkedHashMap buttonLayout;
    private final String _EMPTY = "Bitte Ausfüllen!";
    private HashMap<String, String> states;
    private ArrayList<HashMap<String,String>> buttonLayout;
    

    public Game() {
        error = new HashMap();
        this.setPermanentStore(1);
        this.setLife(1);
        this.setEmulationGame(0);
        this.setInEditMode(false);
        states = new LinkedHashMap();
        //buttonLayout = new LinkedHashMap();
        buttonLayout = new ArrayList();
        //error.keySet().to
    }
    
    public String getFullFilePath() {
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }
    
    
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    public boolean isInEditMode() {
        return inEditMode;
    }

    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
    }
    
    public ArrayList getButtonLayout() {
        return buttonLayout;
    }

    public void flushButtonLayout(){
        buttonLayout.clear();
    }
    
    public void JSONToButtonLayout(String json){
       this.flushButtonLayout(); 
       buttonLayout = (ArrayList<HashMap<String,String>>) JSONValue.parse(json);
    }
    
    public String buttonLayoutToJSON(){
        JSONArray ar = new JSONArray();
        ar.addAll(this.getButtonLayout());
        
        return ar.toJSONString();
    }
    
    public void addButton(String key, String value){
        //buttonLayout.put(key, value);
        HashMap tmp = new HashMap();
        tmp.put(key, value);
        buttonLayout.add(tmp);
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
    
    public void addError(String key, String value){
        error.put(key, value);
    }
    
    public HashMap getErrors(){
        return error;
    }
    
    public void deleteErrors(){
        error.clear();
    }
    
    public HashMap<String,String> getStates() {
        return states;
    }
    
    
    public void addState(String name, String state){
        //states.add( new Part(name, state) );
        states.put(name, state);
    }
    
    public void updateState(String name, String state){
        states.put(name, state);
    }
    
    public String stateToJSON(){
        JSONObject ob = new JSONObject();
       
        for(Map.Entry<String, String> entry : states.entrySet()){
            ob.put(entry.getKey(), entry.getValue());
        }
        return ob.toJSONString();
    }
    
    public void JSONToState(String json){
        
        states = (HashMap<String,String>) JSONValue.parse(json);
    }
}
