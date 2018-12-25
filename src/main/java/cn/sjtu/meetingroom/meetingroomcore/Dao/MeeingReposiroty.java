package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeeingReposiroty extends MongoRepository<Meeting, String> {
}
