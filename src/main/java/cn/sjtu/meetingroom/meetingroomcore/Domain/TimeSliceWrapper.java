package cn.sjtu.meetingroom.meetingroomcore.Domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

public class TimeSliceWrapper extends TimeSlice{
    @ApiModelProperty(notes = "A map which key is the meetingId and value is the meeting head")
    private Map<String, String> meetingNames;

    public TimeSliceWrapper(TimeSlice timeSlice){
        super(timeSlice.getId(), timeSlice.getRoomId(), timeSlice.getTimeSlice(), timeSlice.getDate());
        meetingNames = new HashMap<>();
    }

    public Map<String, String> getMeetingNames() {
        return meetingNames;
    }

    public void setMeetingNames(Map<String, String> meetingNames) {
        this.meetingNames = meetingNames;
    }
}
