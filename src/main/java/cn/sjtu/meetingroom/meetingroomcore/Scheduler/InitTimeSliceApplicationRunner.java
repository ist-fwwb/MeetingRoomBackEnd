package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.TimeSliceFactory;
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
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Autowired
    TimeSliceFactory timeSliceFactory;
    @Autowired
    MeetingRoomFactory meetingRoomFactory;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void run(ApplicationArguments args) throws Exception{
        meetingRoomFactory.create();
        List<MeetingRoom> meetingRoomList = meetingRoomRepository.findAll();
        for (MeetingRoom meetingRoom : meetingRoomList){
            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            if (existTimeSlice(calendar, meetingRoom.getId())) continue;
            else {
                calendar.add(Calendar.DATE, -1);
                timeSliceFactory.create(meetingRoom.getId(), calendar);
            }
        }
    }

    private boolean existTimeSlice(Calendar calendar, String id){
        return !timeSliceRepository.findAllByDateLikeAndRoomIdLike(sdf.format(calendar.getTime()), id).isEmpty();
    }
}
