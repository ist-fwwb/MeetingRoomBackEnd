package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MeetingServiceImp implements MeetingService {
    @Autowired
    MeetingReposiroty meetingReposiroty;
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    public Page<Meeting> findByDateAndRoomId(Date date, String id, PageRequest pageRequest){
        return meetingReposiroty.findAllByRoomIdEqualsAndDateLike(id, date, pageRequest);
    }
}
