package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MeetingServiceImp implements MeetingService {
    @Autowired
    MeetingReposiroty meetingReposiroty;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    TimeSliceRepository timeSliceRepository;
    @Autowired
    UserRepository userRepository;

    @Value("${meeting.attendantNum.size}")
    int RandomNumberSize;

    public List<Meeting> showAll(){
        return meetingReposiroty.findAll();
    }
    public List<Meeting> findByDate(String date, List<Meeting> meetings){
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (meeting.getDate().equals(date)) res.add(meeting);
        }
        return res;
    }
    public List<Meeting> findByRoomId(String roomId, List<Meeting> meetings){
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (meeting.getRoomId().equals(roomId)) res.add(meeting);
        }
        return res;

    }
    public List<Meeting> findByTime(Integer time, List<Meeting> meetings){
        List<Meeting> res = new ArrayList<>();
        int latestTime = Integer.MAX_VALUE;
        Meeting chosenMeeting = null;
        for (Meeting meeting : meetings){
            int startTime = meeting.getStartTime(), endTime = meeting.getEndTime();
            if (startTime <= time && time < endTime) {
                res.add(meeting); return res;
            }
            else if (startTime >= time && time < latestTime) {
                latestTime = time; chosenMeeting = meeting;
            }
        }
        res.add(chosenMeeting);
        return res;
    }

    public List<Meeting> findByStatus(Status status, List<Meeting> meetings){
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : meetings){
            if (meeting.getStatus().equals(status)) res.add(meeting);
        }
        return res;
    }

    @Override
    public List<Meeting> findByLocation(String location, List<Meeting> meetings) {
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : meetings){
            if (Util.compare(meeting.getLocation(), location)) res.add(meeting);
        }
        return res;
    }

    @Transactional
    public Meeting add(Meeting meeting){
        //TODO add the case that there is not enough space the meeting
        try {
            meetingReposiroty.save(meeting);
            String id = meeting.getId();
            String roomId = meeting.getRoomId();
            String date = meeting.getDate();
            int startTime = meeting.getStartTime();
            int endTime = meeting.getEndTime();
            Map<String, String> attendants = meeting.getAttendants();
            modifyTimeSlice(date, roomId, startTime, endTime, id);
            setLocation(meeting, roomId);
            setAttendants(meeting, attendants);
            meeting.setAttendantNum(Util.generateAttendantNum(RandomNumberSize));
            meeting.setStatus(Status.Pending);
            return meetingReposiroty.save(meeting);
        }
        catch (Exception e) {
            meetingReposiroty.delete(meeting);
            return null;
        }
    }

    @Transactional
    public Meeting attend(String attendantNum, String userId){
        //TODO Awake the host to know it
        Meeting meeting = null;
        if (isAttendantNum(attendantNum)) meeting = meetingReposiroty.findMeetingByAttendantNumLikeAndStatusLike(attendantNum, Status.Pending);
        else meeting = meetingReposiroty.findMeetingById(attendantNum);
        if (meeting != null) {
            Map<String, String> attendants = meeting.getAttendants();
            if (!attendants.containsKey(userId) && userId != null){
                attendants.put(userId, "");
                meetingReposiroty.save(meeting);
            }
        }
        return meeting;
    }

    private void modifyTimeSlice(String date, String roomId, int startTime, int endTime, String id) throws Exception{
        TimeSlice timeSlice = timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(date, roomId);
        List<String> timeSlices = timeSlice.getTimeSlice();
        for (int i=startTime; i<endTime; ++i) {
            if (timeSlices.get(i) != null) throw new Exception();
        }
        for (int i=startTime; i<endTime; ++i) timeSlices.set(i, id);
        timeSliceRepository.save(timeSlice);
    }

    public Meeting findById(String id){
        return meetingReposiroty.findMeetingById(id);
    }

    public void cancelMeeting(String id){
        Meeting meeting = meetingReposiroty.findMeetingById(id);
        meeting.setStatus(Status.Cancelled);
        meetingReposiroty.save(meeting);
        TimeSlice timeSlice = timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(meeting.getDate(), meeting.getRoomId());
        List<String> timeSclices = timeSlice.getTimeSlice();
        for (int i=meeting.getStartTime(); i<meeting.getEndTime(); ++i) {
            timeSclices.set(i, null);
        }
        timeSliceRepository.save(timeSlice);
        //TODO Awake the user waiting for the meeting
    }

    public List<User> findAttendants(String id) {
        Meeting meeting = meetingReposiroty.findMeetingById(id);
        List<User> users = new ArrayList<>();
        for (String uid : meeting.getAttendants().keySet()) {
            users.add(userRepository.findUserById(uid));
        }
        return users;
    }

    @Transactional
    public void exitFromMeeting(String id, String userId){
        Meeting meeting = meetingReposiroty.findMeetingById(id);
        meeting.getAttendants().remove(userId);
        meetingReposiroty.save(meeting);
    }

    @Transactional
    public Meeting modify(Meeting meeting, String id){
        Meeting origin = meetingReposiroty.findMeetingById(id);
        if (meeting == null || origin == null) return null;
        Map<String, String> tmp = new HashMap<>();
        tmp.putAll(origin.getAttendants());
        tmp.putAll(meeting.getAttendants());
        meeting.setAttendants(tmp);
        //TODO MODIFY THE TIME
        return meetingReposiroty.save(meeting);
    }

    private void setLocation(Meeting meeting, String roomId){
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomById(roomId);
        meeting.setLocation(meetingRoom.getLocation());
    }

    private void setAttendants(Meeting meeting, Map<String, String> attendants){
        String hostId = meeting.getHostId();
        if (attendants == null) attendants = new HashMap<>();
        if (attendants.isEmpty()) attendants.put(hostId, "");
        meeting.setAttendants(attendants);
    }

    private boolean isAttendantNum(String attendantNum){
        return attendantNum.length() == RandomNumberSize;
    }

}
