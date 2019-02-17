package cn.sjtu.meetingroom.meetingroomcore.Receiver;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.*;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingType;
import cn.sjtu.meetingroom.meetingroomcore.Util.MessageFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.PushFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RabbitListener(queues = "node")
public class NodeMessageReceiver {
    @Autowired
    MeetingService meetingService;
    @Autowired
    QueueNodeService queueNodeService;
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Autowired
    UserRepository userRepository;

    @RabbitHandler
    public void process(String roomId){
        try{
            TimeSlice timeSlice = timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(Util.getDate(), roomId);
            QueueNode queueNode = findSatisfiedQueueNode(roomId, timeSlice);
            if (!isSatisfiedQueueNodeExisted(queueNode)) queueNode = findSatisfiedQueueNode("1", timeSlice);
            if (!isSatisfiedQueueNodeExisted(queueNode)) return;
            Meeting meeting = transformQueueNode2Meeting(queueNode, roomId);
            meeting = meetingService.add(meeting);
            if (idMeetingAddedSuccessfully(meeting)) {
                queueNodeService.delete(queueNode.getId(), queueNode.getRoomId());
                notifyUser(queueNode, meeting);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    private QueueNode findSatisfiedQueueNode(String roomId, TimeSlice timeSlice){
        //TODO 找到合适的QueueNode
        List<QueueNode> queueNodes = queueNodeService.findByRoomId(roomId);
        for (QueueNode queueNode : queueNodes){
            if (between(queueNode.getTimeRange(), timeSlice.getTimeSlice())) return queueNode;
        }
        return null;
    }

    private Meeting transformQueueNode2Meeting(QueueNode queueNode, String roomId){
        Meeting meeting = new Meeting();
        meeting.setRoomId(roomId);
        meeting.setStartTime(queueNode.getTimeRange().getStart());
        meeting.setEndTime(queueNode.getTimeRange().getEnd());
        meeting.setHostId(queueNode.getUserId());
        meeting.setDate(Util.getDate());
        meeting.setType(MeetingType.COMMON);
        meeting.setNeedSignIn(false);
        return meeting;
    }

    private void notifyUser(QueueNode queueNode, Meeting meeting){
        User user = userRepository.findUserById(queueNode.getUserId());
        if (haveDeviceId(user)) {
            PushFactory.push(user.getDeviceId(), "恭喜你成功预定会议", MessageFactory.getMeetingStartBodyMessage(meeting));
        }
    }

    private boolean haveDeviceId(User user){
        return user.getDeviceId() != null;
    }

    private boolean isSatisfiedQueueNodeExisted(QueueNode queueNode) {
        return queueNode != null;
    }

    private boolean idMeetingAddedSuccessfully(Meeting meeting){
        return meeting != null;
    }

    public boolean between(TimeRange timeRange, List<String> timeSlices){
        for (int i=timeRange.getStart(); i<timeRange.getEnd(); ++i) {
            if (timeSlices.get(i) != null) return false;
        }
        return true;
    }
}
