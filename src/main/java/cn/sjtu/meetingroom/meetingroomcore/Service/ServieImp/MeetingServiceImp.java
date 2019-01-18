package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public Meeting add(Meeting meeting){
        //TODO
        meetingReposiroty.save(meeting);
        String id = meeting.getId();
        String roomId = meeting.getRoomId();
        String date = meeting.getDate();
        int startTime = meeting.getStartTime();
        int endTime = meeting.getEndTime();
        Map<String, String> attendants = meeting.getAttendants();
        modifyTimeSlice(date, roomId, startTime, endTime, id);
        setLocation(meeting, roomId);
        setAttendents(meeting, attendants);
        meeting.setAttendantNum(Util.generateAttendantNum(RandomNumberSize));
        meeting.setStatus(Status.Pending);
        return meetingReposiroty.save(meeting);
    }
    public Meeting attend(String attendantNum, String userId){
        //TODO
        Meeting meeting = meetingReposiroty.findMeetingByAttendantNumLikeAndStatusLike(attendantNum, Status.Pending);
        if (meeting == null) meeting = meetingReposiroty.findMeetingById(attendantNum);
        Map<String, String> attendants = meeting.getAttendants();
        if (!attendants.containsKey(userId)){
            attendants.put(userId, null);
            meetingReposiroty.save(meeting);
        }
        return meeting;
    }

    private void modifyTimeSlice(String date, String roomId, int startTime, int endTime, String id){
        TimeSlice timeSlice = timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(date, roomId);
        List<String> timeSlices = timeSlice.getTimeSlice();
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
        //TODO
    }

    public void exitFromMeeting(String id, String userId){
        Meeting meeting = meetingReposiroty.findMeetingById(id);
        meeting.getAttendants().remove(userId);
        meetingReposiroty.save(meeting);
    }

    private void setLocation(Meeting meeting, String roomId){
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomById(roomId);
        meeting.setLocation(meetingRoom.getLocation());
    }

    private void setAttendents(Meeting meeting, Map<String, String> attendants){
        String hostId = meeting.getHostId();
        if (attendants == null) attendants = new HashMap<>();
        if (attendants.isEmpty()) attendants.put(hostId, null);
        meeting.setAttendants(attendants);
    }

}
