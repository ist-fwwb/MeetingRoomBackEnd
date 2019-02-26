package cn.sjtu.meetingroom.meetingroomcore.Dao;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingNoteRepository extends MongoRepository<MeetingNote, String> {
    public List<MeetingNote> findAllByMeetingId(String meetingId);
    public MeetingNote findMeetingNoteById(String id);
}
