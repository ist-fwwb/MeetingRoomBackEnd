package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSliceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TimeSliceWrapperFactory {
    @Autowired
    MeetingReposiroty meetingReposiroty;

    public TimeSliceWrapper create(TimeSlice timeSlice){
        TimeSliceWrapper timeSliceWrapper = new TimeSliceWrapper(timeSlice);
        List<String> timeSlices = timeSliceWrapper.getTimeSlice();
        Map<String, String> meetingNames = timeSliceWrapper.getMeetingNames();
        for (String meetingId : timeSlices){
            if (meetingId != null && !meetingNames.containsKey(meetingId)){
                Meeting meeting = meetingReposiroty.findMeetingById(meetingId);
                if (meeting != null) meetingNames.put(meetingId, meeting.getHeading());
            }
        }
        return timeSliceWrapper;
    }
}
