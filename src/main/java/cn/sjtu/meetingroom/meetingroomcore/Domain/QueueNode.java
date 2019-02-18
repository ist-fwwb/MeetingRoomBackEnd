package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;

import java.io.Serializable;
import java.util.List;

public class QueueNode implements Serializable {
    String id;
    String userId;
    String roomId;
    TimeRange timeRange;
    String date;
    Size size;
    List<MeetingRoomUtils> meetingRoomUtilsList;

    public QueueNode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<MeetingRoomUtils> getMeetingRoomUtilsList() {
        return meetingRoomUtilsList;
    }

    public void setMeetingRoomUtilsList(List<MeetingRoomUtils> meetingRoomUtilsList) {
        this.meetingRoomUtilsList = meetingRoomUtilsList;
    }

    @Override
    public String toString() {
        return "QueueNode{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", timeRange=" + timeRange +
                '}';
    }
}
