package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Service.TimeSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSliceServiceImp implements TimeSliceService {
    @Autowired
    TimeSliceRepository timeSliceRepository;

    public List<TimeSlice> show(String roomId){
        return timeSliceRepository.findAllByRoomIdLike(roomId);
    }
    public List<TimeSlice> findByDate(String date, List<TimeSlice> timeSlices){
        List<TimeSlice> res = new ArrayList<>();
        for (TimeSlice timeSlice : timeSlices){
            if (timeSlice.getDate().equals(date)) {
                res.add(timeSlice);
                break;
            }
        }
        return res;
    }
}
