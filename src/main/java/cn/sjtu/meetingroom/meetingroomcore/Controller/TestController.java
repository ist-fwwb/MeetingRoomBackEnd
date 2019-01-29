package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import io.swagger.annotations.Api;
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
    @GetMapping("/testCompre")
    public boolean testCompare(@RequestParam(name="origin") String origin,
                               @RequestParam(name="s") String s){
        return Util.compare(origin , s);
    }
}
