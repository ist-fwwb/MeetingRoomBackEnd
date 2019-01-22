package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
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
    MeetingReposiroty meetingReposiroty;
    @Autowired
    MeetingService meetingService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "0 10/40 * * * *")
    public void cancelTimeOutMeeting(){
        Date date = new Date();
        List<Meeting> meetings = meetingReposiroty.findMeeingsByDateAndStatus(sdf.format(date), Status.Pending);
        for (Meeting meeting : meetings){
            if (isTimeOut(meeting.getStartTime(), meeting.getEndTime())) meetingService.cancelMeeting(meeting.getId());
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
