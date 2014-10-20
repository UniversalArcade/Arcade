package app.helper;

import app.beans.User;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class Permission {
    
    private HashMap roleMap;
       
    //TODO : Get Roles from web.xml 
    public Permission(){
        roleMap = new HashMap();
        roleMap.put("user",100);
        roleMap.put("mod",200);
        roleMap.put("admin",300);
    }

    public int getUserLvlFromRole(String role){
        if(roleMap.containsKey(role)){
            return (int) roleMap.get(role);
        }
        return -1;
    }
    
    public boolean isValid(HttpServletRequest req, String role){

        User user = (User)req.getSession().getAttribute("user");
        if(user != null){
            if(user.getUserID() > 0 && user.getUserLvl() > 0){
                int userLvl = this.getUserLvlFromRole(role);
                if(userLvl > -1 && user.getUserLvl() >= userLvl){
                    return true;
                }
            }
        }
        return false;
    }
    
    //TODO : prüft ob angeforderter Userlvl exakt dem eigenen entspricht ( == )
    public boolean isExactly(){
     return true;
    }
}
