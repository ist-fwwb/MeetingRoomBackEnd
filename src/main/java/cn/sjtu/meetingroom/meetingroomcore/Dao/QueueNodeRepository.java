package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueNodeRepository {
    public QueueNode findById(String id);
    public List<QueueNode> findByRoomId(String roomId);
    public void save(QueueNode queueNode);
    public void delete(String id);
    public void deleteByDate(String date, String roomId);
}
