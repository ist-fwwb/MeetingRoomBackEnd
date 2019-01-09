package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {
    public Page<User> showAll(PageRequest pageRequest);
    public User showOne(String id);
    public User register(String enterpriseId, String phone, String password, String faceFile, String featureFile, String name);
    public User modifyType(String id, Type type);
    public User login(String phone, String password);
}
