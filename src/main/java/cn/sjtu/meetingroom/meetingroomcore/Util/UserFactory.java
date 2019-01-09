package cn.sjtu.meetingroom.meetingroomcore.Util;

import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    @Autowired
    UserRepository userRepository;

    public User create(String enterpriseId, String phone, String password, String faceFile, String featureFile, String name){
        User user = new User();
        user.setName(name);
        user.setEnterpriceId(enterpriseId);
        user.setPhone(phone);
        user.setPassword(password);
        user.setFaceFile(faceFile);
        user.setFeatureFile(featureFile);
        user.setType(Type.UNACTIVATE);
        userRepository.save(user);
        return user;
    }
}
