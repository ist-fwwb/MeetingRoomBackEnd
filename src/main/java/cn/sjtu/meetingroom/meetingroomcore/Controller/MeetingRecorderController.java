package cn.sjtu.meetingroom.meetingroomcore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/MeetingRecorder")
public class MeetingRecorderController {
    @GetMapping("")
    public void getMeetingRecorder(){

    }

    @PostMapping("")
    public void addMeetingRecorder(){

    }
}
