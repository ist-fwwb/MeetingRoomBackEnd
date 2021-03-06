package cn.sjtu.meetingroom.meetingroomcore.Dao.RepoImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.QueueNodeRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QueueNodeRepositoryImp implements QueueNodeRepository {
    @Autowired
    RedisTemplate<Object, Object> template;

    @Override
    public QueueNode findById(String id) {
        return (QueueNode) template.opsForValue().get(id);
    }

    @Override
    public List<QueueNode> findByRoomId(String roomId) {
        List<QueueNode> tmp = (List<QueueNode>)template.opsForValue().get(roomId);
        return tmp == null ? new ArrayList<>() : tmp;
    }


    @Override
    public void save(QueueNode queueNode) {
        if (queueNode.getId() == null || queueNode.getId().isEmpty()) queueNode.setId(String.valueOf(System.currentTimeMillis()) + queueNode.getUserId());
        saveById(queueNode);
        saveByRoomId(queueNode);
    }

    private void saveById(QueueNode queueNode) {
        template.opsForValue().set(queueNode.getId(), queueNode);
    }

    private void saveByRoomId(QueueNode queueNode) {
        List<QueueNode> queueNodes = findByRoomId(queueNode.getRoomId());
        queueNodes.add(queueNode);
        template.opsForValue().set(queueNode.getRoomId(), queueNodes);
    }

    @Override
    public void delete(String id) {
        QueueNode queueNode = (QueueNode) template.opsForValue().get(id);
        if (queueNode == null) return;
        String roomId = queueNode.getRoomId();
        List<QueueNode> queueNodes = (List<QueueNode>) template.opsForValue().get(roomId);
        queueNodes.removeIf((node) -> node.getId().equals(id));
        template.opsForValue().set(roomId, queueNodes);
        template.opsForValue().set(id, null);
    }

    @Override
    public List<QueueNode> deleteByDate(String date, String roomId) {
        List<QueueNode> queueNodes = (List<QueueNode>) template.opsForValue().get(roomId);
        queueNodes.removeIf((node) -> !node.getDate().equals(date));
        for (QueueNode queueNode : queueNodes) delete(queueNode.getId());
        return queueNodes;
    }
}
