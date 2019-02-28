package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Enum.Status;
import cn.sjtu.meetingroom.meetingroomcore.Enum.Type;
import cn.sjtu.meetingroom.meetingroomcore.Util.UserFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    MeetingRepository meetingRepository;

    public Page<User> showAll(PageRequest pageRequest){
        return userRepository.findAll(pageRequest);
    }
    public User showOne(String id){
        return userRepository.findUserById(id);
    }

    @Override
    public boolean isSatisfied(User user, Meeting meeting) {
        List<Meeting> meetings = meetingRepository.findMeeingsByDateAndStatus(meeting.getDate(), Status.Pending);
        for (Meeting m : meetings){
            if (m.getAttendants().containsKey(user.getId()) && meeting.isOverLapped(m)) return false;
        }
        return true;
    }

    public User register(String enterpriseId, String phone, String password, String faceFile, String featureFile, String name){
        User user = userFactory.create(enterpriseId, phone, password, faceFile, featureFile, name);
        if (user != null)
            WebClient.create().get().uri(Util.UserRegistryURL+"?fileName="+featureFile).retrieve();
        return user;
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
        List<Meeting> tmp = meetingRepository.findMeeingsByDate(date);
        return filterAttendantsById(tmp, id);
    }
    public List<Meeting> findMeetingsById(String id){
        List<Meeting> tmp = meetingRepository.findAll();
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

    @Override
    public boolean isSatisfied(User user, String meetingId) {
        return isSatisfied(user, meetingRepository.findMeetingById(meetingId));
    }

    private List<Meeting> filterAttendantsById(List<Meeting> tmp, String id){
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : tmp){
            if (meeting.getAttendants() != null && meeting.getAttendants().containsKey(id)) res.add(meeting);
        }
        return res;
    }

    @Override
    public List<User> findByFeatureFileName(String featureFileName, List<User> users) {
        List<User> res = new ArrayList<>();
        for (User user : users){
            if (user.getFeatureFile().equals(featureFileName)) res.add(user);
        }
        return res;

    }
}
