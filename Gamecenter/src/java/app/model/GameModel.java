package app.model;

import GameProcessor.Taskmanager; 

public class GameModel {
  
    public GameModel(){
    
    }
    
    public void getGameInfoByID(int id){
        // MYSQL Daten von Game x abrufen
        // return Dataset
    }
    
    public void getAllGameNames(){
        // MYSQL Daten aller Games abrufen (ID,Name)
        // return Dataset
    }
    
    public void executeGameByID(int id){
        
        // MYSQL Abfrage Pfad vom Spiel
        String path = "C:/Program Files (x86)/To the Moon/To the Moon.exe";
        // Zeit nehmen starten
        Taskmanager task = new Taskmanager();
        task.executeGame( path );
        
        // Zeit nehmen beenden
        // bei return true (task ausgeführt und beendet) : MYSQL update der Aufrufanzahl und Aufrufdauer
        //  return false loggen
    }
    
}
