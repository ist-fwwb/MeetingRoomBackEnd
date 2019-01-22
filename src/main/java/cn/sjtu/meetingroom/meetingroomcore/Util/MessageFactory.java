package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;

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
}
