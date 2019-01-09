package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MeetingRoomService {
    public Page<MeetingRoom> showAll(PageRequest pageRequest);
    public MeetingRoom add(MeetingRoom origin);
    public MeetingRoom modify(MeetingRoom meetingRoom);
    public Page<MeetingRoom> findByUtils(List<MeetingRoomUtils> utils, PageRequest pageRequest);
    public MeetingRoom findById(String id);
}
