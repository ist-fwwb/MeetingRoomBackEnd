package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;

import java.util.List;

public interface MeetingNoteService {
    MeetingNote saveHTMLType(MeetingNote meetingNote);
    MeetingNote saveVOICEType(MeetingNote meetingNote);
    MeetingNote collect(String meetingNoteId, String userId);
    MeetingNote deleteCollect(String meetingNoteId, String userId);
    List<MeetingNote> showAll();
    List<MeetingNote> findByMeetingId(String meetingId, List<MeetingNote> meetingNotes);
    List<MeetingNote> findByOwnerId(String ownerId, List<MeetingNote> meetingNotes);
    MeetingNote showOne(String id);
}
