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
    
    /* LEGACY
    public void testTitle(Game g){
        if(g.getEmulationGame() > 0){
            if(g.getTitle() != null && g.getTitle().length() > 0){
                SQLHelper sql = new SQLHelper();
                sql.openCon();
                    ResultSet rs = sql.execQuery("SELECT ID FROM games WHERE isEmulationGame=1 AND title=" + g.getTitle());
                    try {
                        if(rs.next()){
                            int id = rs.getInt("ID");
                            if(id != g.getGameID()){
                                g.addError("title", "Ein Emulatorspiel mit diesem Namen existiert bereits");
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DetailsModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
    }
    */
    
    
    public boolean updateDetails(Game g){
        
        g.updateState("details", "complete");
        String state = g.stateToJSON();
        
        SQLHelper sql = new SQLHelper();
        
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET title = '"+g.getTitle()+"', description = '"+g.getDescription()+"', credits = '"+g.getCredits()+"', permanentStore = '"+g.getPermanentStore()+"', isEmulatorGame='"+g.getEmulationGame()+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
          
          /*LEGACY
          if(g.getEmulationGame() == 1){
              ExeChooserModel exeModel = new ExeChooserModel();
              exeModel.updateExePath(g.getTitle(), g);
          }
          */
          
        sql.closeCon();
        
        return success;
    }
    
}
