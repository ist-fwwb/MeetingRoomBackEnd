package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MeetingReposiroty extends MongoRepository<Meeting, String> {
    Page<Meeting> findAllByRoomIdEqualsAndDateLike(String id, Date date, Pageable pageable);
}
