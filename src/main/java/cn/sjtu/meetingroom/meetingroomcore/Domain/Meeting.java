package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Meeting {
    @Id
    String id;

    String heading;
    String description;
    String hostId;
    List<String> attendants;
    boolean needSignIn;
    String attendantNum;  // a four digit number to attend the meeting
    Status status;
    Type type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public List<String> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<String> attendants) {
        this.attendants = attendants;
    }

    public boolean isNeedSignIn() {
        return needSignIn;
    }

    public void setNeedSignIn(boolean needSignIn) {
        this.needSignIn = needSignIn;
    }

    public String getAttendantNum() {
        return attendantNum;
    }

    public void setAttendantNum(String attendantNum) {
        this.attendantNum = attendantNum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
