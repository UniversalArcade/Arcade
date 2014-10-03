package app.beans;

import java.io.Serializable;

public class User implements Serializable{
    
    private int userID,userLvl;

    public User(){
        this.reset();
    }
    
    public void reset(){
        this.setUserID(0);
        this.setUserLvl(0);
    }
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(int userLvl) {
        this.userLvl = userLvl;
    }
}
