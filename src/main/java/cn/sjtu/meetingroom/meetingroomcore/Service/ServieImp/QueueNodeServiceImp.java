package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.QueueNodeRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueNodeServiceImp implements QueueNodeService {
    @Autowired
    QueueNodeRepository queueNodeRepository;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Override
    public List<QueueNode> findAll() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        List<QueueNode> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms){
            res.addAll(queueNodeRepository.findByRoomId(meetingRoom.getId()));
        }
        //TODO 将magic number改掉
        res.addAll(queueNodeRepository.findByRoomId("1"));
        return res;
    }

    @Override
    public List<QueueNode> findByRoomId(String roomId) {
        return queueNodeRepository.findByRoomId(roomId);
    }

    @Override
    public List<QueueNode> findByUserId(String userId, List<QueueNode> queueNodes) {
        queueNodes.removeIf((queueNode -> !queueNode.getUserId().equals(userId)));
        return queueNodes;
    }

    @Override
    public List<QueueNode> findByRoomId(String roomId, List<QueueNode> queueNodes) {
        queueNodes.removeIf((queueNode -> !queueNode.getRoomId().equals(roomId)));
        return queueNodes;
    }

    @Override
    public QueueNode add(QueueNode queueNode) {
        List<QueueNode> queueNodes = queueNodeRepository.findByRoomId(queueNode.getRoomId());
        queueNode.setId(String.valueOf(System.currentTimeMillis()));
        queueNodes.add(queueNode);
        queueNodeRepository.save(queueNode.getRoomId(), queueNodes);
        return queueNode;
    }

    @Override
    public void delete(String id, String roomId) {
        queueNodeRepository.delete(id, roomId);
    }

    @Override
    public void deleteAll() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        for (MeetingRoom meetingRoom : meetingRooms){
            queueNodeRepository.dump(meetingRoom.getId());
        }
        //TODO 将magic number改掉
        queueNodeRepository.dump("1");
    }
}
