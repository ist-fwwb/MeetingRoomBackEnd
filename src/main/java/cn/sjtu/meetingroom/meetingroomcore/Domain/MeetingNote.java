package cn.sjtu.meetingroom.meetingroomcore.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class MeetingNote {
    @Id
    String id;
    String note;
    String meetingId;
    String ownerId;
    Set<String> collectorIds;

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
}
