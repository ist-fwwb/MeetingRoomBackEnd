package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Document
public class MeetingRoom {
    @Id
    @ApiModelProperty(hidden = true)
    String id;
    Set<MeetingRoomUtils> utils;
    Size size;
    String location;

    public MeetingRoom() {
    }

    public MeetingRoom(String id, Set<MeetingRoomUtils> utils, Size size, String location) {
        this.id = id;
        this.utils = utils;
        this.size = size;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<MeetingRoomUtils> getUtils() {
        return utils;
    }

    public void setUtils(Set<MeetingRoomUtils> utils) {
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
