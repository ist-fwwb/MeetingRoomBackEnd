package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;
import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;

public class MessageFactory {
    public static String createMeetingStartTitleMessage(){
        return "会议开始通知";
    }
    public static String createMeetingStartBodyMessage(Meeting meeting){
        StringBuilder sb = new StringBuilder();
        sb.append("会议基本信息如下：\n");
        sb.append("会议主题："); sb.append(meeting.getHeading()); sb.append('\n');
        sb.append("会议简述："); sb.append(meeting.getDescription()); sb.append('\n');
        sb.append("会议地点："); sb.append(meeting.getLocation()); sb.append('\n');
        sb.append("会议时间："); sb.append(Util.parseIntToTime(meeting.getStartTime())); sb.append('~'); sb.append(Util.parseIntToTime(meeting.getEndTime()));
        return sb.toString();
    }
    public static String getMeetingEndTitleMessage(){
        return "会议结束通知";
    }
    public static String createMeetingEndBodyMessageSuccess(){
        return "由于在接下来半小时该会议室无人使用，所以已经为您自动续钟30分钟，祝开会愉快~";
    }
    public static String createMeetingEndBodyMessageFail() {
        return "由于在接下来该会议室已经被他人预定，所以已经为您自动结束会议，请您尽快离开！";
    }
    public static String createQueueSuccessTitle(){
        return "排队成功通知";
    }
    public static String createQueueSuccessMessage(Meeting meeting){
        StringBuilder sb = new StringBuilder();
        sb.append("恭喜你排队的会议预定成功");
        sb.append(createMeetingStartBodyMessage(meeting));
        return sb.toString();
    }
    public static String createQueueFailTitle(){
        return "排队失败通知";
    }
    public static String createQueueFailureMessage(QueueNode queueNode){
        StringBuilder sb = new StringBuilder();
        sb.append("抱歉，您的主题为");sb.append(queueNode.getHeading());sb.append("的会议排队失败，已经为您自动取消");
        return sb.toString();
    }

    public static String createMeetingCancelledTitle() {
        return "会议取消通知";
    }

    public static String createMeetingCancelledByTimeOutBody(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append("抱歉， 您的主题为");sb.append(meeting.getHeading());sb.append("的会议由于超时10分钟无人开启，已经被自动取消");
        return sb.toString();
    }

    public static String createMeetingCancelledByHostBody(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append("您的主题为");sb.append(meeting.getHeading());sb.append("的会议已经被会议发起者取消");
        return sb.toString();
    }

    public static String createMeetingNoteAddTitle() {
        return "会议笔记更新通知";
    }

    public static String createMeetingNotedAddBody(Meeting meeting, MeetingNote meetingNote, User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("用户名为");sb.append(user.getName());sb.append("用户");
        sb.append("为您参与的主题为");sb.append(meeting.getHeading());sb.append("的会议添加了会议笔记\n");
        sb.append("会议笔记的主题为");sb.append(meetingNote.getTitle());
        return sb.toString();
    }

    public static String createMeetingModifedTitle() {
        return "会议信息修改通知";
    }

    public static String createMeetingModifiedBody(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append("您参与的主题为");sb.append(meeting.getHeading());sb.append("的会议内容发生修改\n");
        sb.append(createMeetingStartBodyMessage(meeting));
        return sb.toString();
    }

    public static String createAttendMeetingTitle() {
        return "参与会议通知";
    }

    public static String createAttendMeetingBody(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append("您已加入主题为"); sb.append(meeting.getHeading()); sb.append("的会议");
        sb.append(createMeetingStartBodyMessage(meeting));
        return sb.toString();
    }

    public static String createExitMeetingTitle() {
        return "退出会议通知";
    }

    public static String createExitMeetingBody(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append("您已退出主题为"); sb.append(meeting.getHeading()); sb.append("的会议");
        return sb.toString();
    }

    public static String createAttendMeetingTitleHost() {
        return "用户参与会议通知";
    }

    public static String createExitMeetingTitleHost() {
        return "用户退出会议通知";
    }

    public static String createAttendMeetingBodyHost(Meeting meeting, User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("用户"); sb.append(user.getName());
        sb.append("加入了主题为"); sb.append(meeting.getHeading()); sb.append("的会议\n");
        sb.append(createMeetingStartBodyMessage(meeting));
        return sb.toString();
    }

    public static String createExitMeetingBodyHost(Meeting meeting, User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("用户"); sb.append(user.getName());
        sb.append("退出了主题为"); sb.append(meeting.getHeading()); sb.append("的会议\n");
        sb.append(createMeetingStartBodyMessage(meeting));
        return sb.toString();
    }
}
