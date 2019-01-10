package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger.readers.operation.ResponseHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

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
    public ResponseEntity<Date> test(MeetingRoomUtils[] macroPath, @RequestParam(name="test") String name){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("hahah", "hhh");
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }

}
