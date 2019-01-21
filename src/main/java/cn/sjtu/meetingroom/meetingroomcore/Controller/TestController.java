package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

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
    @ResponseHeader(name="head1",description="response head conf")
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

    @RequestMapping("/testCalendar")
    public int testMongoSaveTimeSlice(HttpServletRequest request){
        Calendar calendar = Calendar.getInstance();
                return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
