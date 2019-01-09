package cn.sjtu.meetingroom.meetingroomcore.Controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/meeting")
@RestController
@Api(value="Meeting", description = "MeetingController")
public class MeetingController {
    @PostMapping("/")
    @ApiOperation("registory a meeting")
    public void addMeeting(){

    }
}
