package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class MeetingRoom {
    @Id
    String id;

    Map<String, String> utils;
    Size size;
    String location;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getUtils() {
        return utils;
    }

    public void setUtils(Map<String, String> utils) {
        this.utils = utils;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
