package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Message;
import cn.sjtu.meetingroom.meetingroomcore.Enum.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    public Message findMessageById(String id);
    public List<Message> findAllByUserIdLikeAndMessageStatusNotLike(String userId, MessageStatus messageStatus);
}
