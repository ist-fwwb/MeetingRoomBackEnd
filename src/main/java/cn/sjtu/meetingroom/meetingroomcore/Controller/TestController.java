package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/testMongoFind")
    public User testMongoFind(HttpServletRequest request){
        return userRepository.findByName("hahaha");
    }


    @RequestMapping("/testMongoSave")
    public void testMongoSave(HttpServletRequest request){
        User test = new User();
        test.setName("hahaha");
        userRepository.save(test);
    }

}
