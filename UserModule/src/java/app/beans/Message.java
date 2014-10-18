
package app.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{

    // hashmap mit "pageName" : ArrayList
    // jede page hat <c:set name="pageName" value="details..." />
    // page checkt ob pageName als index in message Hashmap vorhanden ist
    // ArrayList vom type Klasse
    // Klasse hat String type und String Msg
    // Type ist success oder warning oder error
    
    private ArrayList<MessageElement> message;
    
    public Message(){
        message = new ArrayList();
    }
    
    public Message(String message){
        this();
        this.addMessage("success", message);
    }
    
    public Message(String type, String message){
        this();
        this.addMessage(type, message);
    }
    
    public void addMessage(String type, String message){
       this.message.add( new MessageElement(type, message) );
    }
    
    public ArrayList<MessageElement> getMessages(){
        ArrayList<MessageElement> clone = (ArrayList<MessageElement>)this.message.clone();
        this.dispose();
        return clone; 
    }
    
    public void dispose(){
        this.message = new ArrayList();
    }
    
    public class MessageElement{
        private String type;
        private String message;
        
        public MessageElement(String type, String message){
            this.setType(type);
            this.setMessage(message);
        }
        
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
