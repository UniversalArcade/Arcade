package app.beans;

import java.awt.TrayIcon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ButtonLayout implements Serializable{
  
    //private HashMap chars;
    private String[] devices = {"Tastatur"}; // + Joystick
    private String[] keyboard = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0","ENTER","CTRL","SHIFT","ALT","BACKSPACE","TAB","SPACE","MINUS","SEMICOLON","TILDE","COMMA","PERIOD","CAPS_LOCK","RIGHT","LEFT","DOWN","UP"};
    //private String[] mouse = {"Left click", "right click"}; // + ...
    private HashMap buttons;
    private HashMap teensyConversion;
    private ArrayList<String> buttonConfig;
    private ArrayList<IllegalButtonCombo> buttonCombos;
    

    public ButtonLayout(){
        buttonConfig = new ArrayList();
        buttons = new HashMap();
        teensyConversion = new HashMap();
        
        buttons.put(devices[0], keyboard);
        //buttons.put(devices[1], mouse);
        setUpIllegalButtonCombinations();
    }
    
    private void setUpIllegalButtonCombinations(){
        buttonCombos = new ArrayList();
        buttonCombos.add(new IllegalButtonCombo("ALT","TAB"));
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
    
    public void addButtonForIllegalTest(String button){
        buttonConfig.add(button);
    }
    
    public Message testForIllegalCombinations(){
        setUpIllegalButtonCombinations();
        Message errors = new Message();
        boolean found = false;
        
        for(IllegalButtonCombo combo : buttonCombos){
            for(String button : buttonConfig)
            {
                combo.test(button);
                if(combo.foundIllegal()){
                    errors.addMessage(Message.Type.ERROR, "Die Tastenkombination " + combo.getCombo()[0] + " - " + combo.getCombo()[1] + " ist nicht erlaubt!");
                    found = true;
                }
            }
        }
        
        if(found){
            return errors;
        }
        return null;
    }
    
    private class IllegalButtonCombo{
        private String[] combo;
        private int found;
        
        public IllegalButtonCombo( String key1, String key2 ){
            combo = new String[2];
            combo[0] = key1;
            combo[1] = key2;
            found = 0;
        }
        
        public void test(String testString){
            if(combo[0].equals(testString) || combo[1].equals(testString))
            {
                found++;
            }
        }
        
        public String[] getCombo(){
            return combo;
        }
        
        
        public boolean foundIllegal(){
            if(found == 2){
                return true;
            }
            return false;
        }
    }
    

}
