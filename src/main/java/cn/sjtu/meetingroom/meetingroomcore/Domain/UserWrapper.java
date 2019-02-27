package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserWrapper {
    User user;
    Boolean isValid;

    public static UserWrapper create(User user, UserService userService, String meetingId){
        UserWrapper res = new UserWrapper();
        res.setUser(user);
        res.setValid(userService.isSatisfied(user, meetingId));
        return res;
    }

    public static List<UserWrapper> create(List<User> users, UserService userService, String meetingId){
        List<UserWrapper> res = new ArrayList<>();
        for (User user : users){
            res.add(create(user, userService, meetingId));
        }
        return res;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
