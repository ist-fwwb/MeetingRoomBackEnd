package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomServiceImp implements MeetingRoomService {
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    MeetingRoomFactory meetingRoomFactory;
    public Page<MeetingRoom> showAll(PageRequest pageRequest){
        return meetingRoomRepository.findAll(pageRequest);
    }
    public MeetingRoom add(MeetingRoom meetingRoom){
        return meetingRoomFactory.create(meetingRoom);
    }
    public MeetingRoom modify(MeetingRoom meetingRoom){
        return meetingRoomRepository.save(meetingRoom);
    }
    public Page<MeetingRoom> findByUtils(List<MeetingRoomUtils> utils, PageRequest pageRequest){
        return null;
    }
    public MeetingRoom findById(String id){
        return meetingRoomRepository.findMeetingRoomById(id);
    }
}
