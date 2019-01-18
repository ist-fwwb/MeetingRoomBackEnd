package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;

import java.util.List;

public interface TimeSliceService {
    public List<TimeSlice> show(String roomId);
    public List<TimeSlice> findByDate(String date, List<TimeSlice> timeSlices);
}
