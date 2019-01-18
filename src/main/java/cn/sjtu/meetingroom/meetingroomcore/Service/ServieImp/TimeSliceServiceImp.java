package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSliceWrapper;
import cn.sjtu.meetingroom.meetingroomcore.Service.TimeSliceService;
import cn.sjtu.meetingroom.meetingroomcore.Util.TimeSliceWrapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSliceServiceImp implements TimeSliceService {
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Autowired
    TimeSliceWrapperFactory timeSliceWrapperFactory;

    public List<TimeSliceWrapper> show(String roomId){
        List<TimeSlice> schedule = timeSliceRepository.findAllByRoomIdLike(roomId);
        List<TimeSliceWrapper> res = new ArrayList<>();
        for(TimeSlice timeSlice : schedule) res.add(timeSliceWrapperFactory.create(timeSlice));
        return res;
    }
    public List<TimeSliceWrapper> findByDate(String date, List<TimeSliceWrapper> timeSlices){
        List<TimeSliceWrapper> res = new ArrayList<>();
        for (TimeSliceWrapper timeSlice : timeSlices){
            if (timeSlice.getDate().equals(date)) {
                res.add(timeSlice);
                break;
            }
        }
        return res;
    }
}
