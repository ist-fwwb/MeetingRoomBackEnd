package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    TimeSliceRepository timeSliceRepository;

    @Autowired
    @Qualifier("userServiceImp")
    UserService userService;

    @RequestMapping("/testMongoFindUser")
    public User testMongoFind(HttpServletRequest request){
        return userRepository.findByName("hahaha");
    }


    @RequestMapping("/testMongoSaveUser")
    public void testMongoSave(HttpServletRequest request){
        User test = new User();
        test.setName("hhh");
        userRepository.save(test);
    }

    @RequestMapping("/testMongoSaveMeetingRoom")
    public void testMongoSaveMeetingRoom(HttpServletRequest request){
        MeetingRoom meetingRoom = new MeetingRoom();
        Map<String, String> map = new HashMap<>();
        map.put("hhh", "hhh");
        meetingRoom.setUtils(map);
        meetingRoomRepository.save(meetingRoom);
    }

    @RequestMapping("/testMongoSaveTimeSlice")
    public void testMongoSaveTimeSlice(HttpServletRequest request){
        TimeSlice timeSlice = new TimeSlice();
        System.out.println(timeSliceRepository.save(timeSlice).getId());
    }
}
