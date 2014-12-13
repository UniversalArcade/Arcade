package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;


public class ButtonLayoutModel {
    
     public boolean updateButtonLayout(String buttons, Game g){
        
        g.updateState("buttonlayout", "complete");
        String state = g.stateToJSON();
         
         SQLHelper sql = new SQLHelper();
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET buttonConfig = '"+buttons+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
}
