package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import cn.sjtu.meetingroom.meetingroomcore.Util.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("userServiceImp")
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFactory userFactory;

    public Page<User> showAll(PageRequest pageRequest){
        return userRepository.findAll(pageRequest);
    }
    public User showOne(String id){
        return userRepository.findUserById(id);
    }
    public User register(String enterpriseId, String phone, String password, byte[] faceFile, byte[] featureFile, String name){
        return userFactory.create(enterpriseId, phone, password, faceFile, featureFile, name);
    }
    public User modifyType(String id, Type type){
        User user = userRepository.findUserById(id);
        user.setType(type);
        return userRepository.save(user);
    }
    public User login(String phone, String password){
        return userRepository.findUserByPhoneLikeAndPasswordLike(phone, password);
    }
}
