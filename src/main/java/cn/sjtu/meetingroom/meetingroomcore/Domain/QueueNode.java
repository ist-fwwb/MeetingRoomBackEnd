package cn.sjtu.meetingroom.meetingroomcore.Domain;

import java.io.Serializable;

public class QueueNode implements Serializable {
    String userId;
    int startTime;
    int endTime;
}
