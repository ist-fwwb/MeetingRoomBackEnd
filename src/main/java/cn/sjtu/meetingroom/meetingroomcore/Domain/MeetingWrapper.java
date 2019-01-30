package cn.sjtu.meetingroom.meetingroomcore.Domain;

/**
 * Created by huangtao on 2019-01-29.
 */
public class MeetingWrapper extends Meeting {

    private User host;

    public MeetingWrapper(Meeting meeting) {
        super(meeting.id, meeting.heading, meeting.description, meeting.roomId, meeting.date, meeting.location,
                meeting.startTime, meeting.endTime, meeting.hostId, meeting.attendants, meeting.needSignIn,
                meeting.attendantNum, meeting.status, meeting.type, meeting.tags);
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }
}
