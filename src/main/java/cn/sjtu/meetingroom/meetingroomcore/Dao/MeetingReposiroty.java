package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingReposiroty extends MongoRepository<Meeting, String> {
    Page<Meeting> findAllByRoomIdEqualsAndDateLike(String id, String date, Pageable pageable);
    Meeting findMeetingByAttendantNumLikeAndStatusLike(String attendantNum, Status status);
    Meeting findMeetingById(String id);
    List<Meeting> findMeeingsByDate(String date);
    List<Meeting> findAllByRoomIdEqualsAndDateLike(String id, String date);
    List<Meeting> findMeetingsByStatus(Status status);
}
