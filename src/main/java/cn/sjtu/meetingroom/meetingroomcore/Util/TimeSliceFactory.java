package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class TimeSliceFactory {
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Value("${timeSlice.totalDates}")
    int totalDates;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void create(String meetingRoomId, Calendar calendar){
        for (int i=0; i<totalDates; ++i){
            TimeSlice timeSlice = new TimeSlice();
            timeSlice.setRoomId(meetingRoomId);
            calendar.add(Calendar.DATE, 1);
            timeSlice.setDate(sdf.format(calendar.getTime()));
            timeSliceRepository.save(timeSlice);
        }
    }
}
