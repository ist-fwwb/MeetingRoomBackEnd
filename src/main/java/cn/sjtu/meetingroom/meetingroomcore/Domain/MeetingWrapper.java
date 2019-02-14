package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import io.swagger.annotations.ApiModelProperty;

public class MeetingWrapper extends Meeting {

    public static MeetingWrapper create(Meeting meeting, UserService userService, String errorNum){
        MeetingWrapper res = new MeetingWrapper(meeting);
        res.setHost(userService.showOne(meeting.getHostId()));
        res.setErrorNum(errorNum);
        return res;
    }

    private User host;
    @ApiModelProperty(notes = "200 delegates ok , 400 delegates fail")
    private String errorNum;

    public String getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    private MeetingWrapper(Meeting meeting) {
        super(meeting.id, meeting.heading, meeting.description, meeting.roomId, meeting.date, meeting.location,
                meeting.startTime, meeting.endTime, meeting.hostId, meeting.attendants, meeting.needSignIn,
                meeting.attendantNum, meeting.status, meeting.type, meeting.tags, meeting.timestamp);
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }
}
