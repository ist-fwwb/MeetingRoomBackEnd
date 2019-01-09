package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Validated
@RequestMapping("/test")
@RestController
@Api(value = "Test", description = "测试TestController")
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

    @RequestMapping("/testMongoSaveUser")
    public void testMongoSave(HttpServletRequest request){
        User test = new User();
        test.setName("hhh");
        userRepository.save(test);
    }

    @RequestMapping("/testMongoSaveMeetingRoom")
    public void testMongoSaveMeetingRoom(HttpServletRequest request){
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoomRepository.save(meetingRoom);
    }

    @RequestMapping("/testMongoSaveTimeSlice")
    public void testMongoSaveTimeSlice(HttpServletRequest request){
        TimeSlice timeSlice = new TimeSlice();
        System.out.println(timeSliceRepository.save(timeSlice).getId());
    }

    @PostMapping("/")
    public Set<String> testDuplicate(@RequestBody Set<String> macroPath, @RequestParam(name="test") String name){
        return macroPath;
    }

    @GetMapping("/")
    public MeetingRoomUtils[] test(MeetingRoomUtils[] macroPath, @RequestParam(name="test") String name){
        return macroPath;
    }

}
