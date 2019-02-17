package cn.sjtu.meetingroom.meetingroomcore.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue nodeQueue(){
        return new Queue("node");
    }

}
