package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSliceWrapper;

import java.util.List;

public interface TimeSliceService {
    public List<TimeSliceWrapper> show(String roomId);
    public List<TimeSliceWrapper> findByDate(String date, List<TimeSliceWrapper> timeSlices);
}
