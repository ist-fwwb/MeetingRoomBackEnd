package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
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
    TimeSliceRepository timeSliceRepository;
    @Autowired
    MeetingRoomFactory meetingRoomFactory;

    public Page<MeetingRoom> showAll(PageRequest pageRequest){
        return meetingRoomRepository.findAll(pageRequest);
    }
    public List<MeetingRoom> showAll(){
        return meetingRoomRepository.findAll();
    }
    public MeetingRoom add(MeetingRoom meetingRoom){
        return meetingRoomFactory.create(meetingRoom);
    }
    public MeetingRoom modify(MeetingRoom meetingRoom){
        return meetingRoomRepository.save(meetingRoom);
    }
    public List<MeetingRoom> findByUtils(List<MeetingRoomUtils> utils, List<MeetingRoom> meetingRooms){
        List<MeetingRoom> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms){
            if (isUtilsSatisfy(utils, meetingRoom)) res.add(meetingRoom);
        }
        return res;
    }
    public List<MeetingRoom> findBySize(Size size, List<MeetingRoom> meetingRooms){
        List<MeetingRoom> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms){
            if (isSizeSatisfy(size, meetingRoom)) res.add(meetingRoom);
        }
        return res;
    }
    public MeetingRoom findById(String id){
        return meetingRoomRepository.findMeetingRoomById(id);
    }

    public List<MeetingRoom> findByStartTimeAndEndTime(Integer startTime, Integer endTime,String date,  List<MeetingRoom> meetingRooms){
        List<MeetingRoom> res = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms){
            if (isStartAndEndSatisfy(startTime, endTime, date, meetingRoom)) res.add(meetingRoom);
        }
        return res;
    }
    private boolean isUtilsSatisfy(List<MeetingRoomUtils> utils, MeetingRoom meetingRoom){
        Set<MeetingRoomUtils> existUtils = meetingRoom.getUtils();
        return existUtils.containsAll(utils);
    }

    private boolean isSizeSatisfy(Size size, MeetingRoom meetingRoom){
        return meetingRoom.getSize().equals(size);
    }

    private boolean isStartAndEndSatisfy(Integer startTime, Integer endTime, String date, MeetingRoom meetingRoom){
        TimeSlice timeSlice = timeSliceRepository.findTimeSliceByDateLikeAndRoomIdLike(date, meetingRoom.getId());
        List<String> timeSlices = timeSlice.getTimeSlice();
        for (int i=startTime; i<endTime; ++i) {
            if (timeSlices.get(i) != null) return false;
        }
        return true;
    }
}
