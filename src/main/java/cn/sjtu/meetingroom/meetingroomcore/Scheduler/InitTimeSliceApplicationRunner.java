package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class InitTimeSliceApplicationRunner implements ApplicationRunner {
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Value("${timeSlice.totalDates}")
    int totalDates;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void run(ApplicationArguments args) throws Exception{
        meetingRoomRepository.save(new MeetingRoom());
        List<MeetingRoom> meetingRoomList = meetingRoomRepository.findAll();
        for (MeetingRoom meetingRoom : meetingRoomList){
            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            for (int i=0; i<totalDates; ++i){
                TimeSlice timeSlice = new TimeSlice();
                timeSlice.setRoomId(meetingRoom.getId());
                calendar.add(Calendar.DATE, 1);
                timeSlice.setDate(sdf.format(calendar.getTime()));
                timeSliceRepository.save(timeSlice);
            }
        }
    }
}
