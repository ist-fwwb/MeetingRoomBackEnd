package cn.sjtu.meetingroom.meetingroomcore.Domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class TimeSlice {
    @Id
    String id;

    String roomId;
    List<String> timeSlice;
    @ApiModelProperty(notes = "yyyy-MM-dd")
    String date;

    public TimeSlice(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getTimeSlice() {
        return timeSlice;
    }

    public void setTimeSlice(List<String> timeSlice) {
        this.timeSlice = timeSlice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
