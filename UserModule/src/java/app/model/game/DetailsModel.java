/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Martin
 */
public class DetailsModel {
    
    public void updateDetails(Game g) throws SQLException{
        
        g.updateState("details", "complete");
        String state = g.stateToJSON();
        
        try(SQLHelper sql = new SQLHelper()){
            
            if(g.getEmulationGame() > 0){
                
                g.flushButtonLayout();
                g.addButton("W","");
                g.addButton("S","");
                g.addButton("A","");
                g.addButton("D","");
                g.addButton("J","");
                g.addButton("U","");
                g.addButton("K","");
                g.addButton("I","");
                g.addButton("L","");
                g.addButton("O","");
                String buttonLayout = g.buttonLayoutToJSON();
                
                sql.execNonQuery("UPDATE `games` SET buttonConfig = '"+ buttonLayout +"', title = '"+g.getTitle()+"', description = '"+g.getDescription()+"', credits = '"+g.getCredits()+"', permanentStore = '"+g.getPermanentStore()+"', isEmulatorGame='"+g.getEmulationGame()+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
            }
            else{
                sql.execNonQuery("UPDATE `games` SET title = '"+g.getTitle()+"', description = '"+g.getDescription()+"', credits = '"+g.getCredits()+"', permanentStore = '"+g.getPermanentStore()+"', isEmulatorGame='"+g.getEmulationGame()+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
            }
            
        }
        
        
        
        
    }
    
}
