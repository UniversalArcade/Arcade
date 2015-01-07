package app.beans;

import java.io.Serializable;
import java.util.HashMap;

public class ButtonLayout implements Serializable{
  
    //private HashMap chars;
    private String[] devices = {"Tastatur"}; // + Joystick
    private String[] keyboard = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0","ENTER","CTRL","SHIFT","ALT","BACKSPACE","TAB","SPACE","MINUS","SEMICOLON","TILDE","COMMA","PERIOD","CAPS_LOCK","RIGHT","LEFT","DOWN","UP"};
    //private String[] mouse = {"Left click", "right click"}; // + ...
    private HashMap buttons;
    private HashMap teensyConversion;
    

    public ButtonLayout(){
        buttons = new HashMap();
        teensyConversion = new HashMap();
        
        buttons.put(devices[0], keyboard);
        //buttons.put(devices[1], mouse);
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
    
    /*
    private void setUpTeensyConversion(){
       teensyConversion.put("A", "KEY_A");
       teensyConversion.put("B", "KEY_B");
       //...
    }
    */
    

}
