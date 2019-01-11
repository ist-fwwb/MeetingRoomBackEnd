package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;

public interface TimeSliceService {
    public TimeSlice show(String date, String roomId);
}
