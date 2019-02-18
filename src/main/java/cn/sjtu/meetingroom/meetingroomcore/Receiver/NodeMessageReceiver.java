package cn.sjtu.meetingroom.meetingroomcore.Receiver;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
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
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @RabbitHandler
    public void process(Meeting cancelledMeeting){
        try{
            //System.out.println("get message");
            QueueNode queueNode = findSatisfiedQueueNode(cancelledMeeting.getRoomId(), cancelledMeeting);
            if (isSatisfiedQueueNodeNotExisted(queueNode)) queueNode = findSatisfiedQueueNode(Util.ROOMID, cancelledMeeting);
            if (!isSatisfiedQueueNodeNotExisted(queueNode)) {
                //System.out.println("find queuenode");
                Meeting meeting = transformQueueNode2Meeting(queueNode, cancelledMeeting);
                meeting = meetingService.add(meeting);
                if (isMeetingAddedSuccessfully(meeting)) {
                    queueNodeService.delete(queueNode.getId(), queueNode.getRoomId());
                    notifyUser(queueNode, meeting);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    private QueueNode findSatisfiedQueueNode(String redisRoomId, Meeting cancelledMeeting){
        TimeSlice timeSlice = timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(cancelledMeeting.getDate(), cancelledMeeting.getRoomId());
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomById(cancelledMeeting.getRoomId());
        List<QueueNode> queueNodes = queueNodeService.findByRoomId(redisRoomId);
        for (QueueNode queueNode : queueNodes){
            if (between(queueNode.getTimeRange(), timeSlice.getTimeSlice()) &&
                    (queueNode.getSize() == null || queueNode.getSize().equals(meetingRoom.getSize())) &
                    meetingRoom.getUtils().containsAll(queueNode.getMeetingRoomUtilsList())) return queueNode;
        }
        return null;
    }

    private Meeting transformQueueNode2Meeting(QueueNode queueNode, Meeting cancelledMeeting){
        Meeting meeting = new Meeting();
        meeting.setRoomId(cancelledMeeting.getRoomId());
        meeting.setStartTime(queueNode.getTimeRange().getStart());
        meeting.setEndTime(queueNode.getTimeRange().getEnd());
        meeting.setHostId(queueNode.getUserId());
        meeting.setDate(cancelledMeeting.getDate());
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

    private boolean isSatisfiedQueueNodeNotExisted(QueueNode queueNode) {
        return queueNode == null;
    }

    private boolean isMeetingAddedSuccessfully(Meeting meeting){
        return meeting != null;
    }

    private boolean between(TimeRange timeRange, List<String> timeSlices){
        for (int i=timeRange.getStart(); i<timeRange.getEnd(); ++i) {
            if (timeSlices.get(i) != null) return false;
        }
        return true;
    }
}
