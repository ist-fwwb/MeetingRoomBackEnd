package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Message;

import java.util.List;

public interface MessageService {
    List<Message> findByUserId(String userId);
    Message findById(String id);
    void read(String id);
    void delete(String id);
    void create(String userId, String meetingId, String title, String content);
    void createWithoutMeetingId(String userId, String title, String content);
}
