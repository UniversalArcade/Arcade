package app.beans;

import java.awt.TrayIcon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ButtonLayout implements Serializable{
  
    //private HashMap chars;
    private String[] devices = {"Tastatur"}; // + Joystick, Maus
    private String[] keyboard = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0","ENTER","CTRL","SHIFT","ALT","BACKSPACE","TAB","SPACE","MINUS","SEMICOLON","TILDE","COMMA","PERIOD","CAPS_LOCK","RIGHT","LEFT","DOWN","UP"};
    //private String[] mouse = {"Left click", "right click"}; // + ...
    private HashMap buttons;
    private ArrayList<String> buttonConfig;
    private ArrayList<IllegalButtonCombo> buttonCombos;
    

    public ButtonLayout(){
        buttonConfig = new ArrayList();
        buttons = new HashMap();
        buttons.put(devices[0], keyboard);
        //buttons.put(devices[1], mouse);
    }
    
    private void setUpIllegalButtonCombinations(){
        buttonCombos = new ArrayList();
        buttonCombos.add(new IllegalButtonCombo("ALT","TAB"));
        buttonCombos.add(new IllegalButtonCombo("STRG","TAB"));
        buttonCombos.add(new IllegalButtonCombo("CTRL","ALT","ENTER"));
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
            System.out.println("COMBO: " + combo.getComboString());
            
            for(String button : buttonConfig)
            {
                combo.test(button);
                if(combo.foundIllegal()){
                    
                    
                    errors.addMessage(Message.Type.ERROR, "Die Tastenkombination " + combo.getComboString() + " ist nicht erlaubt!");
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
        
        public IllegalButtonCombo( String key1, String key2, String key3 ){
            combo = new String[3];
            combo[0] = key1;
            combo[1] = key2;
            combo[2] = key3;
            found = 0;
        }
        
        public void test(String testString){
            if(combo[0].equals(testString) || combo[1].equals(testString) || (combo.length == 3 && combo[2].equals(testString)))
            {
                System.out.println("UP BEI : " + testString);
                found++;
                System.out.println("foundcount: " + found);
            }
        }
        
        public String[] getCombo(){
            return combo;
        }
        
        public String getComboString(){
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for(String key : combo)
            {
                builder.append(key);
                if(++i < combo.length) builder.append(" - ");
            }
            return builder.toString();
        }
        
        public boolean foundIllegal(){
            if(found == combo.length){
                return true;
            }
            return false;
        }
    }
    

}
