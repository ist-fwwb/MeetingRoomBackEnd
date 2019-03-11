package cn.sjtu.meetingroom.meetingroomcore.Service.ServieImp;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingNoteRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.UserRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingNoteService;
import cn.sjtu.meetingroom.meetingroomcore.Service.MessageService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MessageFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class MeetingNoteServiceImp implements MeetingNoteService {
    @Autowired
    MeetingNoteRepository meetingNoteRepository;
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageService messageService;
    private RestTemplate template = new RestTemplate();


    @Override
    public MeetingNote saveHTMLType(MeetingNote meetingNote) {
        notifyAttendants(meetingNote);
        return meetingNoteRepository.save(meetingNote);
    }

    @Override
    public MeetingNote saveVOICEType(MeetingNote meetingNote) {
        //TODO 完成和语音转文字服务器的对接
        meetingNoteRepository.save(meetingNote);
        new RestTemplate().getForObject(Util.VoiceTransformURL + "/recognize/" + meetingNote.getId(), String.class);
        notifyAttendants(meetingNote);
        return meetingNote;
    }

    private void notifyAttendants(MeetingNote meetingNote) {
        Meeting meeting = meetingRepository.findMeetingById(meetingNote.getMeetingId());
        User user = userRepository.findUserById(meetingNote.getOwnerId());
        for (String userId : meeting.getAttendants().keySet()){
            if (userId.equals(meetingNote.getOwnerId())) continue;
            messageService.create(userId, meeting.getId(), MessageFactory.createMeetingNoteAddTitle(), MessageFactory.createMeetingNotedAddBody(meeting, meetingNote, user));
        }
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
