package cn.sjtu.meetingroom.meetingroomcore.Domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by huangtao on 2019-01-29.
 */
public class MeetingWrapper extends Meeting {

    private User host;
    @ApiModelProperty(notes = "200 delegates ok , 400 delegates fail")
    private String errorNum;

    public String getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    public MeetingWrapper(Meeting meeting) {
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
