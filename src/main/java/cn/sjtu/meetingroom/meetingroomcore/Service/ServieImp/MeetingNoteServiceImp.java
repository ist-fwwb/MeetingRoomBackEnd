package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingNoteRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MeetingNoteServiceImp implements MeetingNoteService {
    @Autowired
    MeetingNoteRepository meetingNoteRepository;

    @Override
    public MeetingNote saveHTMLType(MeetingNote meetingNote) {
        return meetingNoteRepository.save(meetingNote);
    }

    @Override
    public MeetingNote saveVOICEType(MeetingNote meetingNote) {
        meetingNoteRepository.save(meetingNote);
        return meetingNote;
    }

    @Override
    public MeetingNote collect(String meetingNoteId, String userId) {
        try{
            MeetingNote meetingNote = meetingNoteRepository.findMeetingNoteById(meetingNoteId);
            meetingNote.getCollectorIds().add(userId);
            return meetingNoteRepository.save(meetingNote);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public MeetingNote deleteCollect(String meetingNoteId, String userId) {
        try{
            MeetingNote meetingNote = meetingNoteRepository.findMeetingNoteById(meetingNoteId);
            meetingNote.getCollectorIds().remove(userId);
            return meetingNoteRepository.save(meetingNote);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MeetingNote> showAll() {
        return meetingNoteRepository.findAll();
    }

    @Override
    public MeetingNote showOne(String id) {
        return meetingNoteRepository.findMeetingNoteById(id);
    }
}
