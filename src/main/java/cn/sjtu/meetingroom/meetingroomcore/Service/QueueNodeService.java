package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QueueNodeService {
    public List<QueueNode> findAll();
    public List<QueueNode> findByUserId(String userId, List<QueueNode> queueNodes);
    public List<QueueNode> findByRoomId(String roomId, List<QueueNode> queueNodes);
    public QueueNode add(QueueNode queueNode);
    public void delete(String id, String roomId);
}
