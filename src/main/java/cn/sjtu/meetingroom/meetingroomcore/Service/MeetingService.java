package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.*;
import cn.sjtu.meetingroom.meetingroomcore.Enum.Status;

import java.util.List;

public interface MeetingService {
    public List<Meeting> showAll();
    public List<Meeting> findByDate(String date, List<Meeting> meetings);
    public List<Meeting> findByRoomId(String roomId, List<Meeting> meetings);
    public List<Meeting> findByTime(Integer time, List<Meeting> meetings);
    public List<Meeting> findByStatus(Status status, List<Meeting> meetings);
    public List<Meeting> findByLocation(String location, List<Meeting> meetings);
    public Meeting findById(String id);
    public Meeting add(Meeting meeting);
    public Meeting attend(String attendantNum, String userId);
    public Meeting intelligentlyFindMeeting(Meeting origin, List<MeetingRoom> meetingRooms);
    public void exitFromMeeting(String id, String userId);
    public void cancelMeeting(String id);
    public void startMeeting(String id);
    public void stopMeeting(String id);
    public List<User> findAttendants(String id);
    public boolean modify(Meeting meeting, String id);
    public boolean modifyMeetingTime(Meeting meeting, Meeting origin);
    public Meeting addForeignGuest(String id, List<ForeignGuest> foreignGuests);
    public Meeting addAttendantBatch(String meetingId, List<String> userIds);
    public Meeting deleteAttendantBatch(String meetingId, List<String> userIds);
}
