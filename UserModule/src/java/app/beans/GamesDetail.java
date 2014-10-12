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
    
    private ArrayList<Details> details;

  
    
    public GamesDetail(){
        details = new ArrayList();
    }
    
     public ArrayList<Details> getDetails() {
        return details;
     }
    
    public void addDetails(int gameID, String title){
        details.add( new Details(gameID,title) );
    }

   
    
    public class Details{
        private int gameID;
        private String title;

        public Details(int gameID, String title){
            this.setDetailsGameID(gameID);
            this.setDetailsTitle(title);
        }
        
        public int getDetailsGameID() {
            return gameID;
        }

        public void setDetailsGameID(int gameID) {
            this.gameID = gameID;
        }

        public String getDetailsTitle() {
            return title;
        }

        public void setDetailsTitle(String title) {
            this.title = title;
        }
    }  
}
