package cn.sjtu.meetingroom.meetingroomcore.Dao.RepoImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.QueueNodeRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
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
    public List<QueueNode> findByRoomId(String roomId) {
        Object tmp = template.opsForValue().get(roomId);
        if (tmp == null) return new ArrayList<>();
        else {
            List<QueueNode> res = new ArrayList<>();
            for (QueueNode queueNode : (List<QueueNode>) tmp) res.add(queueNode);
            return res;
        }
    }

    @Override
    public void save(String roomId, List<QueueNode> queueNodes) {
        if (template.getExpire(roomId) < 0) template.expireAt(roomId, Util.getNextDay());
        template.opsForValue().set(roomId, queueNodes);
    }

    @Override
    public void delete(String id, String roomId) {
        List<QueueNode> queueNodes = (List<QueueNode>) template.opsForValue().get(roomId);
        queueNodes.removeIf((node) -> node.getId().equals(id));
    }



    @Override
    public void dump() {

    }
}
