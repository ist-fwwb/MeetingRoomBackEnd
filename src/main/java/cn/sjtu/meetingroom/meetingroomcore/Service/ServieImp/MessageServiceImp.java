package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MessageRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Message;
import cn.sjtu.meetingroom.meetingroomcore.Enum.MessageStatus;
import cn.sjtu.meetingroom.meetingroomcore.Service.MessageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public List<Message> findByUserId(String userId) {
        return messageRepository.findAllByUserIdLikeAndMessageStatusNotLike(userId, MessageStatus.DELETE);
    }

    @Override
    public Message findById(String id) {
        return messageRepository.findMessageById(id);
    }

    @Override
    public void read(String id) {
        Message message = findById(id);
        message.setMessageStatus(MessageStatus.READ);
        messageRepository.save(message);
    }

    @Override
    public void delete(String id) {
        Message message = findById(id);
        message.setMessageStatus(MessageStatus.DELETE);
        messageRepository.save(message);
    }

    @Override
    public void create(String userId, String meetingId, String title, String content) {
        Message message = Message.create(userId, meetingId, title, content);
        messageRepository.save(message);
        amqpTemplate.convertAndSend("message", message);
    }

    @Override
    public void createWithoutMeetingId(String userId, String title, String content) {
        create(userId, "", title, content);
    }
}
