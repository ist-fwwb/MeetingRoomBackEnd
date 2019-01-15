package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import cn.sjtu.meetingroom.meetingroomcore.Util.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userServiceImp")
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFactory userFactory;
    @Autowired
    MeetingReposiroty meetingReposiroty;

    public Page<User> showAll(PageRequest pageRequest){
        return userRepository.findAll(pageRequest);
    }
    public User showOne(String id){
        return userRepository.findUserById(id);
    }
    public User register(String enterpriseId, String phone, String password, String faceFile, String featureFile, String name){
        return userFactory.create(enterpriseId, phone, password, faceFile, featureFile, name);
    }
    public User modify(User user){
        return userRepository.save(user);
    }
    public User login(String phone, String password){
        return userRepository.findUserByPhoneLikeAndPasswordLike(phone, password);
    }
    public List<Meeting> findMeetingsByIdAndDate(String id, String date){
        List<Meeting> res = new ArrayList<>();
        List<Meeting> tmp = meetingReposiroty.findMeeingsByDate(date);
        for (Meeting meeting : tmp){
            if (meeting.getAttendants().containsKey(id)) res.add(meeting);
        }
        return res;
    }
}
