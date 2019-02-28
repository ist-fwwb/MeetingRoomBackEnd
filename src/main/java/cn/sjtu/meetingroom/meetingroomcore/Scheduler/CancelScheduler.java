package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRepository;
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

@Component
public class CancelScheduler {
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MessageService messageService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "0 10/40 * * * *")
    public void cancelTimeOutMeeting(){
        Date date = new Date();
        List<Meeting> meetings = meetingRepository.findMeeingsByDateAndStatus(sdf.format(date), Status.Pending);
        for (Meeting meeting : meetings){
            if (isTimeOut(meeting.getStartTime(), meeting.getEndTime())) {
                meetingService.cancelMeeting(meeting.getId());
                for (String userId : meeting.getAttendants().keySet()){
                    messageService.create(userId, meeting.getId(), MessageFactory.createMeetingCancelledTitle(), MessageFactory.createMeetingCancelledByTimeOutBody(meeting));
                }
            }
        }
    }

    private boolean isTimeOut(int startTime, int endTime){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int time = hour * 2 + minutes / 30;
        if (time >= startTime && time < endTime) return true;
        else return false;
    }
}
