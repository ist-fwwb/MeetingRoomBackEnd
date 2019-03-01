package cn.sjtu.meetingroom.meetingroomcore.Domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResult implements Serializable {
    List<Meeting> meetings;
    List<MeetingNote> meetingNotes;
    List<User> users;
    List<MeetingRoom> meetingRooms;
    int totalCount;

    public static SearchResult crete(JSONObject totalHints) {
        SearchResult res = new SearchResult();
        res.setTotalCount(totalHints.getInteger("total"));
        JSONArray hits = totalHints.getJSONArray("hits");
        for (Object jsonObject : hits){
            JSONObject hit = (JSONObject) jsonObject;
            JSONObject source = hit.getJSONObject("_source");
            source.put("id", hit.getString("_id"));
            switch (hit.getString("_type")){
                case "user":
                    res.getUsers().add(generateObject(source, User.class));
                    break;
                case "meetingRoom":
                    res.getMeetingRooms().add(generateObject(source, MeetingRoom.class));
                    break;
                case "meetingNote":
                    res.getMeetingNotes().add(generateObject(source, MeetingNote.class));
                    break;
                case "meeting":
                    res.getMeetings().add(generateObject(source, Meeting.class));
                    break;
            }
        }
        return res;
    }

    private static <T> T generateObject(JSONObject source, Class<T> tClass){
        return JSON.parseObject(source.toJSONString(), tClass);
    }

    public static SearchResult createNullSearchResult() {
        return new SearchResult();
    }

    private SearchResult(){
        setTotalCount(0);
        setMeetingNotes(new ArrayList<>());
        setMeetings(new ArrayList<>());
        setMeetingRooms(new ArrayList<>());
        setUsers(new ArrayList<>());
    }

    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
        this.meetingRooms = meetingRooms;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public List<MeetingNote> getMeetingNotes() {
        return meetingNotes;
    }

    public void setMeetingNotes(List<MeetingNote> meetingNotes) {
        this.meetingNotes = meetingNotes;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
