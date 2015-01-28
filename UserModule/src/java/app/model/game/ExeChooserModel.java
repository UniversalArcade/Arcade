/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.*;
import org.json.simple.JSONObject;

/**
 *
 * @author Martin
 */
public class ExeChooserModel {
    
    private ArrayList<String> listableFileTypes;
    
    public ExeChooserModel(){
        listableFileTypes = new ArrayList();
        listableFileTypes.add("exe");
        listableFileTypes.add("com");
        listableFileTypes.add("bat");
        listableFileTypes.add("hta");
    }
    
    public boolean updateExePath(String path, Game g) throws SQLException{
        
        if(path != null && path != ""){
            g.updateState("exechooser", "complete");
        }
        else{
            g.updateState("exechooser", "incomplete");
        }
        String state = g.stateToJSON();

        try(SQLHelper sql = new SQLHelper()){
            sql.execNonQuery("UPDATE `games` SET executePath = '"+path+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
        }
        
        return true;
    }
    
    public Game getFileStructureAsJSON(Game g) throws SQLException{
        JSONArray jsonarr = listfJSON("C:\\Users\\Public\\Arcade\\Games\\" + g.getGameID() + "\\game");
        g.setFilePathJSON(jsonarr);
        
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery("SELECT executePath FROM games WHERE ID = "+ g.getGameID());
            if(rs.next())
            {
                JSONObject obj = new JSONObject();
                obj.put("file", rs.getString("executePath"));
                
                g.setFullFilePath(obj.toJSONString());
            }
        }
        return g;
    }
    
    public JSONArray listfJSON(String directoryName) {
        JSONArray files = new JSONArray();
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        
        
        for (File file : fList) {
            if (file.isFile()) {
                if(listableFileTypes.contains( FilenameUtils.getExtension(file.getName()).toLowerCase())){
                    Map f = new LinkedHashMap();
                    f.put("type", "file");
                    f.put("name", file.getName());
                    files.add(f);
                }
            } 
            
            else if (file.isDirectory()) {
                Map folder = new LinkedHashMap();
                folder.put("type", "folder");
                folder.put("name", file.getName());
                folder.put("child", listfJSON(file.getAbsolutePath()));
                files.add(folder);
            }
        }
        
        return files;
    }
    
}
