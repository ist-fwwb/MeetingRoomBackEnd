package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSliceRepository extends MongoRepository<TimeSlice, String> {
    List<TimeSlice> findAllByDateLike(String date);
    List<TimeSlice> findAllByDateLikeAndRoomIdLike(String date, String roomId);
    Page<TimeSlice> findAllByDateLike(String date, Pageable pageable);
}
