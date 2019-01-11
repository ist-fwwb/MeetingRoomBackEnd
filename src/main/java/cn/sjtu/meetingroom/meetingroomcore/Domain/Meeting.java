package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingType;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document
public class Meeting {
    @Id
    @ApiModelProperty(hidden = true)
    String id;

    @ApiModelProperty(required = true)
    String heading;
    @ApiModelProperty(required = true)
    String description;
    @ApiModelProperty(required = true)
    String roomId;
    @ApiModelProperty(notes = "yyyy-MM-dd")
    String date;
    @ApiModelProperty(required = false)
    String location;
    @ApiModelProperty(required = true)
    int startTime;
    @ApiModelProperty(required = true)
    int endTime;
    @ApiModelProperty(required = true)
    String hostId;
    @ApiModelProperty(required = false)
    Map<String, String> attendants;
    @ApiModelProperty(required = true)
    boolean needSignIn;
    @ApiModelProperty(notes = "a four digit number", required = false)
    String attendantNum;  // a four digit number to attend the meeting
    @ApiModelProperty(required = false)
    Status status;
    @ApiModelProperty(required = true)
    MeetingType type;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Map<String, String> getAttendants() {
        return attendants;
    }

    public void setAttendants(Map<String, String> attendants) {
        this.attendants = attendants;
    }

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

    public MeetingType getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
