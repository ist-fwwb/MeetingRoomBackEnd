package cn.sjtu.meetingroom.meetingroomcore.Domain;

import java.util.ArrayList;
import java.util.List;

public class MeetingNoteWrapper {
    MeetingNote meetingNote;
    Boolean isCollected;

    public static MeetingNoteWrapper create(MeetingNote meetingNote, String userId){
        MeetingNoteWrapper res = new MeetingNoteWrapper();
        res.setMeetingNote(meetingNote);
        res.setCollected(meetingNote.getCollectorIds().contains(userId));
        return res;
    }

    public static List<MeetingNoteWrapper> create(List<MeetingNote> meetingNotes, String userId){
        List<MeetingNoteWrapper> res = new ArrayList<>();
        for (MeetingNote meetingNote : meetingNotes){
            res.add(create(meetingNote, userId));
        }
        return res;
    }

    public MeetingNote getMeetingNote() {
        return meetingNote;
    }

    public void setMeetingNote(MeetingNote meetingNote) {
        this.meetingNote = meetingNote;
    }

    public Boolean getCollected() {
        return isCollected;
    }

    public void setCollected(Boolean collected) {
        isCollected = collected;
    }
}
