package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public Page<MeetingRoom> findByUtils(List<MeetingRoomUtils> utils, Page<MeetingRoom> meetingRooms){
        List<MeetingRoom> meetingRoomList = meetingRooms.getContent();
        List<MeetingRoom> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRoomList){
            if (isUtilsSatisfy(utils, meetingRoom)) res.add(meetingRoom);
        }
        return new PageImpl(res, new PageRequest(meetingRooms.getNumber(), meetingRooms.getSize()), meetingRooms.getTotalElements());
    }
    public Page<MeetingRoom> findBySize(Size size,PageRequest pageRequest){
        return meetingRoomRepository.findAllBySize(size, pageRequest);
    }
    public MeetingRoom findById(String id){
        return meetingRoomRepository.findMeetingRoomById(id);
    }

    private boolean isUtilsSatisfy(List<MeetingRoomUtils> utils, MeetingRoom meetingRoom){
        Set<MeetingRoomUtils> existUtils = meetingRoom.getUtils();
        return existUtils.containsAll(utils);
    }
}
