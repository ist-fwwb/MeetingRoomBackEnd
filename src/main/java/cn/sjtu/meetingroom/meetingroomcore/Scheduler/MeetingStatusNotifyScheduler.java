package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Enum.Status;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Service.MessageService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class MeetingStatusNotifyScheduler {
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    MeetingService meetingService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageService messageService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "0 50/20 * * * *")
    public void notifyMeetingStart(){
        List<Meeting> meetings = meetingRepository.findMeetingsByStatus(Status.Pending);
        meetings = meetingService.findByDate(sdf.format(new Date()), meetings);
        for (Meeting meeting : meetings){
            if (isComing(meeting.getStartTime())){
                Map<String, String> attendants = meeting.getAttendants();
                for (String id : attendants.keySet()) {
                    messageService.create(id, meeting.getId(), MessageFactory.getMeetingStartTitleMessage(), MessageFactory.getMeetingStartBodyMessage(meeting));
                }
            }
        }
    }

    @Scheduled(cron = "0 55/25 * * * *")
    public void notifyMeetingEnd(){
        List<Meeting> meetings = meetingRepository.findMeetingsByStatus(Status.Running);
        meetings = meetingService.findByDate(sdf.format(new Date()), meetings);
        for (Meeting meeting : meetings){
            if (isComing(meeting.getEndTime())){
                meeting.setEndTime(meeting.getEndTime() + 1);
                if (meetingService.modifyMeetingTime(meeting, meetingRepository.findMeetingById(meeting.getId()))){
                    //TODO 延时成功
                    messageService.create(meeting.getHostId(), meeting.getId(), MessageFactory.getMeetingEndTitleMessage(), MessageFactory.getMeetingEndBodyMessage(meeting));
                }
                else {
                    //TODO 延时失败，强行关闭
                    meetingService.stopMeeting(meeting.getId());
                    messageService.create(meeting.getHostId(), meeting.getId(), MessageFactory.getMeetingEndTitleMessage(), MessageFactory.getMeetingEndBodyMessage(meeting));
                }
            }
        }
    }

    private boolean isComing(int comingTime){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int time = hour * 2 + minutes / 30 + 1;
        return time == comingTime;
    }
}
