package cn.sjtu.meetingroom.meetingroomcore.Service;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;

import java.util.List;

public interface MeetingNoteService {
    MeetingNote saveHTMLType(MeetingNote meetingNote);
    MeetingNote saveVOICEType(MeetingNote meetingNote);
    MeetingNote collect(String meetingNoteId, String userId);
    MeetingNote deleteCollect(String meetingNoteId, String userId);
    List<MeetingNote> showAll();
    MeetingNote showOne(String id);
}
