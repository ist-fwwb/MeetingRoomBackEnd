package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomRepository extends MongoRepository<MeetingRoom, String> {
}
