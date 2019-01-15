package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

public interface MeetingService {
    public Page<Meeting> findByDateAndRoomId(String date, String id, PageRequest pageRequest);
    public Meeting findById(String id);
    public Meeting add(Meeting meeting);
    public Meeting attend(String attendantNum, String userId);
    public void exitFromMeeting(String id, String userId);
    public void cancelMeeting(String id);
}
