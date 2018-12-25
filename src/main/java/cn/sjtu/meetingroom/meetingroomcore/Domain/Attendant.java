package cn.sjtu.meetingroom.meetingroomcore.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Attendant {
    @Id
    String id;

    String userId;
    Date signInTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return signInTime;
    }

    public void setDate(Date date) {
        this.signInTime = date;
    }
}
