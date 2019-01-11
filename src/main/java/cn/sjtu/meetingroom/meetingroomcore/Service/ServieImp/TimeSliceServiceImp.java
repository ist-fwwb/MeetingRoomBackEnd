package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Service.TimeSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSliceServiceImp implements TimeSliceService {
    @Autowired
    TimeSliceRepository timeSliceRepository;

    public TimeSlice show(String date, String roomId){
        return timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(date, roomId);
    }
}
