package app.beans;

import java.io.Serializable;
import java.util.HashMap;

public class ButtonLayout implements Serializable{
  
    //private HashMap chars;
    private String[] devices = {"Tastatur", "Maus"}; // + Joystick
    private String[] keyboard = {"ENTER","SHIFT","FOO"}; // + ...
    private String[] mouse = {"Left click", "right click"}; // + ...
    private HashMap buttons;
    private HashMap teensyConversion;
    

    public ButtonLayout(){
        buttons = new HashMap();
        teensyConversion = new HashMap();
        
        buttons.put(devices[0], keyboard);
        buttons.put(devices[1], mouse);
    }
    
    
    
    
    public String[] getDevices() {
        return devices;
    }

    public void setDevices(String[] devices) {
        this.devices = devices;
    }
    
    public HashMap getButtons() {
        return buttons;
    }

    public void setButtons(HashMap buttons) {
        this.buttons = buttons;
    }
    

    private void setUpTeensyConversion(){
       teensyConversion.put("A", "KEY_A");
       teensyConversion.put("B", "KEY_B");
       //...
    }
    

}
