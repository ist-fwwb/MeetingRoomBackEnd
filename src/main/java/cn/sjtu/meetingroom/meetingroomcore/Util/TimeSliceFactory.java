package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class TimeSliceFactory {
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Value("${timeSlice.totalDates}")
    int totalDates;
    @Value("${timeSlice.slice}")
    int slice;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void create(String meetingRoomId, Calendar calendar){
        for (int i=0; i<totalDates; ++i){
            TimeSlice timeSlice = new TimeSlice();
            List<String> occupied = new ArrayList<>();
            int totalSlices = 24 * (60 / slice);
            for (int j=0; j<totalSlices; ++j){
                occupied.add(null);
            }
            timeSlice.setTimeSlice(occupied);
            timeSlice.setRoomId(meetingRoomId);
            calendar.add(Calendar.DATE, 1);
            timeSlice.setDate(sdf.format(calendar.getTime()));
            timeSliceRepository.save(timeSlice);
        }
    }
}
