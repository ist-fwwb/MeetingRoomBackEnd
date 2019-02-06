package cn.sjtu.meetingroom.meetingroomcore.Config;

import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {
    @Autowired
    RedisTemplate<Object, Object> template;
    @Test
    public void whenCreateAndStoreTheObjectIntoRedisThenGetObject(){
        template.opsForValue().set("123", new User());
        System.out.println(template.opsForValue().get("123").toString());
    }
    @Test
    public void whenStoreTheListThenGetList(){
        List<User> test = new ArrayList<>();
        test.add(new User()); test.add(new User());
        template.opsForValue().set("123", test);
        test = (List<User>)template.opsForValue().get("123");
        System.out.println(test.get(0).toString());
        System.out.println(test.get(1).toString());
    }

}