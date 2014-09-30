/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package app.model;
import helper.SQLHelper;
import app.beans.Game;
/**
 *
 * @author KM
 */
public class GameManagerModel {
    SQLHelper sql;
    public GameManagerModel(){
        sql = new SQLHelper();
    }
    
    public void insertNewGame(Game g, int userID){
        //int userID = 1;
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID,title,description,credits) VALUES ('"+userID+"', '"+g.getTitle()+"', '"+g.getDescription()+"', '"+g.getCredits()+"')");
        sql.closeCon();
    }
    
    public void updateGame(){
    
    }
    
    public void deleteGame(){
    
    }
    
}
