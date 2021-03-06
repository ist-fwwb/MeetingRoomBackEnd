package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import io.swagger.annotations.Api;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/test")
@RestController
@Api(value = "Test", description = "测试TestController")
public class TestController {
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    MeetingRepository meetingRepository;
    @GetMapping("/testCompre")
    public boolean testCompare(@RequestParam(name="origin") String origin,
                               @RequestParam(name="s") String s){
        return Util.compare(origin , s);
    }
    @GetMapping("/rabbitmq")
    public void testRabbitMQ(@RequestParam(name="meetingId") String id){
        amqpTemplate.convertAndSend("node", meetingRepository.findMeetingById(id));
    }
    @GetMapping("/testClone")
    public boolean testClone(@RequestParam(name = "meetingId") String meetingId){
        Meeting origin = meetingRepository.findMeetingById(meetingId);
        origin.setEndTime(-1);
        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        return origin.getEndTime() == meeting.getEndTime();
    }
}
