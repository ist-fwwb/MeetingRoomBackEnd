package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingWrapper extends Meeting {

    public static MeetingWrapper create(Meeting meeting, UserService userService, MeetingRoomService meetingRoomService, String errorNum){
        if (errorNum.equals("400")) {
            MeetingWrapper res = new MeetingWrapper();
            res.setErrorNum(errorNum);
        }
        MeetingWrapper res = new MeetingWrapper(meeting);
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : meeting.getAttendants().entrySet()){
            User user = userService.showOne(entry.getKey());
            map.put(user.getName(), entry.getValue());
        }
        res.setAttendantsName(map);
        res.setHost(userService.showOne(meeting.getHostId()));
        res.setLocation(meetingRoomService.findById(meeting.getRoomId()).getLocation());
        res.setErrorNum(errorNum);
        return res;
    }

    public static List<MeetingWrapper> create(List<Meeting> meetings, UserService userService, MeetingRoomService meetingRoomService, String errorNum){
        List<MeetingWrapper> meetingWrappers = new ArrayList<>();
        for (Meeting meeting : meetings) {
            MeetingWrapper meetingWrapper = MeetingWrapper.create(meeting, userService, meetingRoomService, errorNum);
            meetingWrappers.add(meetingWrapper);
        }

        return meetingWrappers;
    }

    private User host;
    @ApiModelProperty(notes = "200 delegates ok , 400 delegates fail")
    private String errorNum;
    private Map<String, String> attendantsName;

    public MeetingWrapper() {
    }

    public String getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    private MeetingWrapper(Meeting meeting) {
        super(meeting.id, meeting.heading, meeting.description, meeting.roomId, meeting.date, meeting.location,
                meeting.startTime, meeting.endTime, meeting.hostId, meeting.attendants, meeting.needSignIn,
                meeting.attendantNum, meeting.status, meeting.type, meeting.tags, meeting.timestamp, meeting.foreignGuestList);
    }

    public Map<String, String> getAttendantsName() {
        return attendantsName;
    }

    public void setAttendantsName(Map<String, String> attendantsName) {
        this.attendantsName = attendantsName;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }
}
