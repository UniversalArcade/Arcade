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
public class GamesDetail implements Serializable{
    private int gameID, userID, gameStarts, gameDuration, permanentStore;
    private String title, description, buttonConfig, credits;

    public int getGameStarts() {
        return gameStarts;
    }

    public void setGameStarts(int gameStarts) {
        this.gameStarts = gameStarts;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getPermanentStore() {
        return permanentStore;
    }

    public void setPermanentStore(int permanentStore) {
        this.permanentStore = permanentStore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getButtonConfig() {
        return buttonConfig;
    }

    public void setButtonConfig(String buttonConfig) {
        this.buttonConfig = buttonConfig;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
