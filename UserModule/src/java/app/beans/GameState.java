/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Martin
 */
public class GameState implements Serializable{
    
    
    //private LinkedHashMap<String, Part> states;
    private ArrayList<Part> states;


    public GameState(){
        //states = new LinkedHashMap();
        states = new ArrayList();
    }
    
     public ArrayList<Part> getStates() {
        return states;
    }
    
    public void addState(String name, String state, String link){
        states.add( new Part(name, state, link) );
    }

    private class Part{
        private String name, state, link;
        
        public Part(String name, String state, String link){
            this.setName(name);
            this.setState(state);
            this.setLink(link);
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
