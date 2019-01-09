package cn.sjtu.meetingroom.meetingroomcore.Controller;


import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/meeting")
@RestController
@Api(value="Meeting", description = "MeetingController")
public class MeetingController {
    @PostMapping("/")
    @ApiOperation("registor a meeting")
    public void addMeeting(){

    }

    @GetMapping("/")
    @ApiOperation("get all of the meeting")
    public Page<Meeting> getMeeting(){
        return null;
    }
}
