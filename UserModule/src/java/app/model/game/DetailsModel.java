/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;
/**
 *
 * @author Martin
 */
public class DetailsModel {
    
    public boolean updateDetails(Game g){
        SQLHelper sql = new SQLHelper();
        
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET title = '"+g.getTitle()+"', description = '"+g.getDescription()+"', credits = '"+g.getCredits()+"', permanentStore = '"+g.getPermanentStore()+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
}
