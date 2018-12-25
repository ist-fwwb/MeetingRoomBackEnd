package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSliceRepository extends MongoRepository<TimeSlice, String> {
}
