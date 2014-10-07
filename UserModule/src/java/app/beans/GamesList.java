/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author KM
 */
public class GamesList implements Serializable{
    
    private ArrayList<Game> games;

  
    
    public GamesList(){
        games = new ArrayList();
    }
    
     public ArrayList<Game> getGames() {
        return games;
     }
    
    public void addGame(int gameID, String title){
        games.add( new Game(gameID,title) );
    }
    
    public class Game{
        private int gameID;
        private String title;

        public Game(int gameID, String title){
            this.setGameID(gameID);
            this.setTitle(title);
        }
        
        public int getGameID() {
            return gameID;
        }

        public void setGameID(int gameID) {
            this.gameID = gameID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }  
}
