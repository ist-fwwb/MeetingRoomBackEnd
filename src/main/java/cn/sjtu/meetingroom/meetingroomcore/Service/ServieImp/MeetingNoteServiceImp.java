package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingNoteRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingNoteService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        //TODO 完成和语音转文字的对接
        meetingNoteRepository.save(meetingNote);
        WebClient.create().get().uri(Util.VoiceTransformURL + "?meetingNoteId=" + meetingNote.getId()).retrieve();
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
    public List<MeetingNote> findByMeetingId(String meetingId, List<MeetingNote> meetingNotes) {
        meetingNotes.removeIf((meetingNote -> {
            return !meetingNote.getMeetingId().equals(meetingId);
        }));
        return meetingNotes;
    }

    @Override
    public List<MeetingNote> findByOwnerId(String ownerId, List<MeetingNote> meetingNotes) {
        meetingNotes.removeIf((meetingNote -> {
            return !meetingNote.getOwnerId().equals(ownerId);
        }));
        return meetingNotes;
    }

    @Override
    public MeetingNote showOne(String id) {
        return meetingNoteRepository.findMeetingNoteById(id);
    }
}
