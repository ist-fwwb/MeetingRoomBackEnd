package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.QueueNodeRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueNodeServiceImp implements QueueNodeService {
    @Autowired
    QueueNodeRepository queueNodeRepository;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Override
    public QueueNode findById(String id) {
        return queueNodeRepository.findById(id);
    }

    @Override
    public List<QueueNode> findAll() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        List<QueueNode> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms){
            res.addAll(queueNodeRepository.findByRoomId(meetingRoom.getId()));
        }
        res.addAll(queueNodeRepository.findByRoomId(Util.ROOMID));
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
    public List<QueueNode> findByDate(String date, List<QueueNode> queueNodes) {
        queueNodes.removeIf((queueNode -> !queueNode.getDate().equals(date)));
        return queueNodes;
    }

    @Override
    @Transactional
    public QueueNode add(QueueNode queueNode) {
        queueNodeRepository.save(queueNode);
        return queueNode;
    }

    @Override
    @Transactional
    public void delete(String id) {
        queueNodeRepository.delete(id);
    }

    @Override
    public List<QueueNode> deleteByDate(String date) {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        List<QueueNode> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms){
            res.addAll(queueNodeRepository.deleteByDate(date, meetingRoom.getId()));
        }
        res.addAll(queueNodeRepository.deleteByDate(date, Util.ROOMID));
        return res;
    }
}
