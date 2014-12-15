package app.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class GameComponents implements Serializable{
    
    private LinkedHashMap<String, String> components;

    public GameComponents(){
        components = new LinkedHashMap();
        
        //servletmapping , link name
        components.put("details", "Details");
        components.put("coverupload", "Cover");
        components.put("buttonlayout", "Button Layout");
        components.put("gameupload", "Game upload");
        components.put("exechooser", "Choose exe file");
    }
    
    public LinkedHashMap<String, String> getComponents() {
        return components;
    }
}
