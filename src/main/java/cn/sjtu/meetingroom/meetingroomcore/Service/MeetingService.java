package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;

import java.util.List;

public interface MeetingService {
    public List<Meeting> showAll();
    public List<Meeting> findByDate(String date, List<Meeting> meetings);
    public List<Meeting> findByRoomId(String roomId, List<Meeting> meetings);
    public List<Meeting> findByTime(Integer time, List<Meeting> meetings);
    public Meeting findById(String id);
    public Meeting add(Meeting meeting);
    public Meeting attend(String attendantNum, String userId);
    public Meeting save(Meeting meeting);
    public void exitFromMeeting(String id, String userId);
    public void cancelMeeting(String id);
    public List<User> findAttendants(String id);
}
