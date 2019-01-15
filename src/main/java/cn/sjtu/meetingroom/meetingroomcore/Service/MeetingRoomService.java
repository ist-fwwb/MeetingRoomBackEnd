package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MeetingRoomService {
    public Page<MeetingRoom> showAll(PageRequest pageRequest);
    public List<MeetingRoom> showAll();
    public MeetingRoom add(MeetingRoom origin);
    public MeetingRoom modify(MeetingRoom meetingRoom);
    public List<MeetingRoom> findByUtils(List<MeetingRoomUtils> utils, List<MeetingRoom> meetingRooms);
    public List<MeetingRoom> findBySize(Size size, List<MeetingRoom> meetingRooms);
    public List<MeetingRoom> findByStartTimeAndEndTime(Integer startTime, Integer endTime, String date, List<MeetingRoom> meetingRooms);
    public MeetingRoom findById(String id);
}
