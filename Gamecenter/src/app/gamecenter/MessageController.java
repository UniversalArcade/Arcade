/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gamecenter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class MessageController {

    
    public String processMessage(String msg){
        
        
        String response = "";
        
        //Object obj = JSONValue.parse(msg);
        JSONObject jobj = (JSONObject) JSONValue.parse(msg);
        String action = jobj.get("action").toString();
        int gameID = Integer.parseInt( jobj.get("gameID").toString() );
        
        System.out.println("ACTION: " + action);

        switch(action){
            case "getGameInfo":
                GameModel model = new GameModel();
                String gameInfo =  model.getGameInfoByID(gameID);
                System.out.println("JSON gameInfo: " + gameInfo);
                
                response = gameInfo;
            break;
            case "startGame":
                GameModel gameModel = new GameModel();
                gameModel.executeGameByID( gameID );
            break;    
        }

        return response;
    }
    
    
}
