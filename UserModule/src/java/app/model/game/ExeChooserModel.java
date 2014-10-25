/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.*;

/**
 *
 * @author Martin
 */
public class ExeChooserModel {
    
    public boolean updateExePath(String path, Game g){
        
        SQLHelper sql = new SQLHelper();
        
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET executePath = '"+path+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
    public Game getFileStructureAsJSON(Game g){
        JSONArray jsonarr = listfJSON("C:\\Users\\Public\\Arcade\\Games\\" + g.getGameID() + "\\game");
        g.setFilePathJSON(jsonarr);
        return g;
    }
    
    
    public JSONArray listfJSON(String directoryName) {
        JSONArray files = new JSONArray();
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                Map f = new LinkedHashMap();
                f.put("type", "file");
                f.put("name", file.getName());
                files.add(f);
            } 
            
            else if (file.isDirectory()) {
                Map folder = new LinkedHashMap();
                folder.put("type", "folder");
                folder.put("name", file.getName());
                folder.put("child", listfJSON(file.getAbsolutePath()));
                //folder.put(file.getName(), listfJSON(file.getAbsolutePath()));
                files.add(folder);
            }
        }
        
        return files;
    }
    
}
