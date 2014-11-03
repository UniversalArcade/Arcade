package app.beans;

import java.io.Serializable;

public class User implements Serializable{
    
    private int userID,userLvl,isregistred;
 
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
    
    public int getRegistred(){
        return isregistred;
    }
    
    public void setRegistred(int isregistred){
        this.isregistred= isregistred;
    }
}
