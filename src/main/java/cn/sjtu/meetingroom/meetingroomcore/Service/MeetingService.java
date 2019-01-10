package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

public interface MeetingService {
    public Page<Meeting> findByDateAndRoomId(Date date, String id, PageRequest pageRequest);
}
