package cn.sjtu.meetingroom.meetingroomcore.Receiver;

import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Message;
import cn.sjtu.meetingroom.meetingroomcore.Util.PushFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "message")
public class UserMessageReceiver {
    @Autowired
    UserRepository userRepository;
    @RabbitHandler
    public void sendMessage(Message message){
        try{
            PushFactory.push(userRepository.findUserById(message.getUserId()).getDeviceId(), message.getTitle(), message.getContent());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
