package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingReposiroty;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import cn.sjtu.meetingroom.meetingroomcore.Util.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public User login(String phone, String password, String deviceId){
        User user = userRepository.findUserByPhoneLikeAndPasswordLike(phone, password);
        if (user != null && deviceId != null) {
            user.setDeviceId(deviceId);
            userRepository.save(user);
        }
        return user;
    }
    public List<Meeting> findMeetingsByIdAndDate(String id, String date){
        List<Meeting> tmp = meetingReposiroty.findMeeingsByDate(date);
        return filterAttendantsById(tmp, id);
    }
    public List<Meeting> findMeetingsById(String id){
        List<Meeting> tmp = meetingReposiroty.findMeetingsByStatus(Status.Pending);
        return filterAttendantsById(tmp, id);
    }
    public List<User> findByType(Type type, List<User> users){
        List<User> res = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals(type)) res.add(user);
        }
        return res;
    }
    public List<User> findByIds(String[] ids, List<User> users){
        Map<String, User> recorder = new HashMap<>();
        for (User user : users) recorder.put(user.getId(), user);
        List<User> res = new ArrayList<>();
        for (String id : ids) res.add(recorder.get(id));
        return res;
    }
    public List<User> showAll(){
        return userRepository.findAll();
    }
    private List<Meeting> filterAttendantsById(List<Meeting> tmp, String id){
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : tmp){
            if (meeting.getAttendants().containsKey(id)) res.add(meeting);
        }
        return res;
    }
}
