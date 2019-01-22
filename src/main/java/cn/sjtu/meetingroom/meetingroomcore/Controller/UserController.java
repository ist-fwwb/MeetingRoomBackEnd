package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Validated
@RequestMapping("/user")
@RestController
@Api(value="User", description = "UserController")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("")
    @ApiOperation(value="get all of the user's detail information")

    public List<User> showAll(@RequestParam(name="type", required = false) Type type,
                              @RequestParam(name="ids", required = false) String[] ids){
        List<User> users = userService.showAll();
        if (ids != null) users = userService.findByIds(ids, users);
        if (type != null) users = userService.findByType(type, users);
        return users;
    }

    @GetMapping("/{id}")
    @ApiOperation(value="get a specified user's detail informatipon")
    public User showOne(@PathVariable(name="id")String id){
        return userService.showOne(id);
    }

    @GetMapping("/{id}/meeting")
    @ApiOperation(value = "get user's all of the meeting which is pending")
    public List<Meeting> getAllMeeting(@PathVariable(name="id") String id){
        return userService.findMeetingsById(id);
    }

    @GetMapping("/{id}/meeting/{date}")
    @ApiOperation(value = "get user's meeting of a specified day")
    public List<Meeting> getMeeting(@PathVariable(name="id") String id, @PathVariable(name="date") String date){
        return userService.findMeetingsByIdAndDate(id, date);
    }


    @PostMapping("")
    @ApiOperation(value="registor a user")
    public User register(@RequestParam(name = "enterpriseId") String enterpriseId,
                         @RequestParam(name = "phone") String phone, @RequestParam(name ="password") String password,
                         @RequestParam(name = "faceFile") String faceFile,
                         @RequestParam(name = "featureFile") String featureFile,
                         @RequestParam(name = "name") String name) throws Exception{
        return userService.register(enterpriseId, phone, password, faceFile, featureFile, name);
    }

    @PostMapping("/login")
    @ApiOperation(value="log in the app with phone and password")
    public User login(@RequestParam(name="phone") String phone, @RequestParam(name="password") String password, @RequestParam(name="deviceId", required = false) String deviceId,
                      HttpServletRequest request){
        User user = userService.login(phone, password, deviceId);
        if (user == null) return null;
        else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return user;
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value="modify a specified user's status")
    public User modify(@RequestBody User user){
        return userService.modify(user);
    }
}
