package cn.sjtu.meetingroom.meetingroomcore.Domain;

import java.io.Serializable;

public class QueueNode implements Serializable {
    String id;
    String userId;
    String roomId;
    TimeRange timeRange;

    public QueueNode() {
    }

    public QueueNode(String id, String userId, String roomId, TimeRange timeRange) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.timeRange = timeRange;
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
