package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Enum.MeetingNoteType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class MeetingNote {
    @Id
    String id;
    MeetingNoteType meetingNoteType;
    String voiceFileName;
    String title;
    String note;
    String meetingId;
    String ownerId;
    Set<String> collectorIds;

    public String getVoiceFileName() {
        return voiceFileName;
    }

    public void setVoiceFileName(String voiceFileName) {
        this.voiceFileName = voiceFileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Set<String> getCollectorIds() {
        return collectorIds;
    }

    public void setCollectorIds(Set<String> collectorIds) {
        this.collectorIds = collectorIds;
    }

    public MeetingNoteType getMeetingNoteType() {
        return meetingNoteType;
    }

    public void setMeetingNoteType(MeetingNoteType meetingNoteType) {
        this.meetingNoteType = meetingNoteType;
    }
}
