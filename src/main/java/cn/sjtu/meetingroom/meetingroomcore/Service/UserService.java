package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {
    public Page<User> showAll(PageRequest pageRequest);
    public User showOne(String id);
    public User register(String enterpriseId, String phone, String password, String faceFile, String featureFile, String name);
    public User modify(User user);
    public User login(String phone, String password, String deviceId);
    public List<Meeting> findMeetingsByIdAndDate(String id, String date);
    public List<Meeting> findMeetingsByIdAndStatus(String id, Status status);
    public List<User> findByIds(String[] ids, List<User> users);
    public List<User> findByType(Type type, List<User> users);
    public List<User> showAll();
}
