package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MessageFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.PushFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class NotifyScheduler {
    @Autowired
    MeetingReposiroty meetingReposiroty;
    @Autowired
    MeetingService meetingService;
    @Autowired
    UserRepository userRepository;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "0 50/20 * * * *")
    public void notifyMeetingStart(){
        List<Meeting> meetings = meetingReposiroty.findMeetingsByStatus(Status.Pending);
        meetings = meetingService.findByDate(sdf.format(new Date()), meetings);
        for (Meeting meeting : meetings){
            if (isComing(meeting.getStartTime())){
                Map<String, String> attendants = meeting.getAttendants();
                for (String id : attendants.keySet()) {
                    User user = userRepository.findUserById(id);
                    if (user != null && user.getDeviceId() != null) {
                        PushFactory.push(user.getDeviceId(), MessageFactory.getMeetingStartTitleMessage(), MessageFactory.getMeetingStartBodyMessage(meeting));
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 50/20 * * * *")
    public void notifyMeetingEnd(){
        List<Meeting> meetings = meetingReposiroty.findMeetingsByStatus(Status.Pending);
        meetings = meetingService.findByDate(sdf.format(new Date()), meetings);
        for (Meeting meeting : meetings){
            if (isComing(meeting.getEndTime())){
                User host = userRepository.findUserById(meeting.getHostId());
                if (host != null && host.getDeviceId() != null) {
                    PushFactory.push(host.getDeviceId(), MessageFactory.getMeetingEndTitleMessage(), MessageFactory.getMeetingEndBodyMessage(meeting));
                }
            }
        }
    }

    public boolean isComing(int comingTime){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int time = hour * 2 + minutes / 30 + 1;
        return time == comingTime;
    }
}
