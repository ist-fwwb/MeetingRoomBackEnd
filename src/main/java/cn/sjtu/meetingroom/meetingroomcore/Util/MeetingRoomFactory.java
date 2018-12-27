package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class MeetingRoomFactory {
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    TimeSliceFactory timeSliceFactory;

    public MeetingRoom create(){
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoomRepository.save(meetingRoom);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        timeSliceFactory.create(meetingRoom.getId(), calendar);
        return meetingRoom;
    }
}
