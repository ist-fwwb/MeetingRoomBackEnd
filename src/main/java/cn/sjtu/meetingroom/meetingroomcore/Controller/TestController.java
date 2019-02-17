package cn.sjtu.meetingroom.meetingroomcore.Controller;

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
    @GetMapping("/testCompre")
    public boolean testCompare(@RequestParam(name="origin") String origin,
                               @RequestParam(name="s") String s){
        return Util.compare(origin , s);
    }
    @GetMapping("/rabbitmq")
    public void testRabbitMQ(@RequestParam(name="roomId") String id){
        amqpTemplate.convertAndSend("node", id);
    }
}
