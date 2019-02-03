package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Page<User> findAll(Pageable pageable);
    User findUserById(String id);
    User findUserByPhoneLike(String phone);
    User findUserByPhoneLikeAndPasswordLike(String phone, String pasword);
}
