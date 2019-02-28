package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;

public class MessageFactory {
    public static String getMeetingStartTitleMessage(){
        return "您的会议还有十分钟即将开始";
    }
    public static String getMeetingStartBodyMessage(Meeting meeting){
        StringBuilder sb = new StringBuilder();
        sb.append("会议主题："); sb.append(meeting.getHeading()); sb.append('\n');
        sb.append("会议简述："); sb.append(meeting.getDescription()); sb.append('\n');
        sb.append("会议地点："); sb.append(meeting.getLocation()); sb.append('\n');
        sb.append("会议时间："); sb.append(Util.parseIntToTime(meeting.getStartTime())); sb.append('~'); sb.append(Util.parseIntToTime(meeting.getEndTime()));
        return sb.toString();
    }
    public static String getMeetingEndTitleMessage(){
        return "您的会议还有十分钟即将结束";
    }
    public static String getMeetingEndBodyMessage(Meeting meeting){
        return null;
    }
    public static String createQueueSuccessTitle(){
        return "排队成功通知";
    }
    public static String createQueueFailTitle(){
        return "排队失败通知";
    }
    public static String createQueueSuccessMessage(Meeting meeting){
        StringBuilder sb = new StringBuilder();
        sb.append("恭喜你排队的会议预定成功");
        sb.append("会议基本信息如下：\n");
        sb.append(getMeetingStartBodyMessage(meeting));
        return sb.toString();
    }
    public static String createQueueFailureMessage(QueueNode queueNode){
        StringBuilder sb = new StringBuilder();
        sb.append("抱歉，您的主题为");sb.append(queueNode.getHeading());sb.append("会议排队失败，已经为您自动取消");
        return sb.toString();
    }

}
