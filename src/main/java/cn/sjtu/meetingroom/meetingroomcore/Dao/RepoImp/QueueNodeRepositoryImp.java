package cn.sjtu.meetingroom.meetingroomcore.Dao.RepoImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.QueueNodeRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class QueueNodeRepositoryImp implements QueueNodeRepository {
    @Autowired
    RedisTemplate<Object, Object> template;
    @Override
    public List<QueueNode> findByRoomId(String id) {
        return (List<QueueNode>)template.opsForValue().get(id);
    }

    @Override
    public void save(String id, List<QueueNode> queueNodes) {
        if (template.getExpire(id) < 0) template.expireAt(id, Util.getNextDay());
        template.opsForValue().set(id, queueNodes);
    }

    @Override
    public void delete(String id) {
        template.dump(id);
    }

    @Override
    public void dump() {
        Set<Object> keys = template.keys("*");
        for (Object key : keys) template.dump(key);
    }
}
