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
    
    
    // return
    // -1 : role < userLvl :: input userLvl is less then required
    // 0 : role == userLvl :: input userLvl is exactly as requiered
    // 1: role > userLvl :: input userLvl is more then required
    public int isValid(HttpServletRequest req, String role){

        User user = (User)req.getSession().getAttribute("user");
        if(user != null){
            if(user.getUserID() > 0 && user.getUserLvl() > 0){
                int userLvl = this.getUserLvlFromRole(role);
                if(userLvl > -1 ){
                    if(user.getUserLvl() > userLvl){
                        return 1 ;
                    }
                    else if(user.getUserLvl() == userLvl){
                        return 0;
                    }                    
                }
            }
        }
        return -1;
    }
}
