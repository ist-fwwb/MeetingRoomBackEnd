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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    int size;

    public Page<Meeting> findByDateAndRoomId(String date, String id, PageRequest pageRequest){
        return meetingReposiroty.findAllByRoomIdEqualsAndDateLike(id, date, pageRequest);
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
        meeting.setAttendantNum(Util.generateAttendantNum(size));
        meeting.setStatus(Status.Pending);
        return meetingReposiroty.save(meeting);
    }
    public Meeting attend(String attendantNum, String userId){
        //TODO
        Meeting meeting = meetingReposiroty.findMeetingByAttendantNumLikeAndStatusLike(attendantNum, Status.Pending);
        Map<String, String> attendants = meeting.getAttendants();
        if (attendants.containsKey(userId)) return meeting;
        else {
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
