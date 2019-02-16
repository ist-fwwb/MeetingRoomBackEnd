package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueNodeRepository {
    public List<QueueNode> findByRoomId(String roomId);
    public void save(String roomId, List<QueueNode> queueNodes);
    public void delete(String id, String roomId);
    public void dump();
}
