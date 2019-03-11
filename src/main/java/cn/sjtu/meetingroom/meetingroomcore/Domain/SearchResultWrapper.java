package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;

import java.util.List;

public class SearchResultWrapper {

    private List<MeetingWrapper> meetings;
    private List<MeetingNote> meetingNotes;
    private List<User> users;
    private List<MeetingRoom> meetingRooms;
    private int totalCount;

    public static SearchResultWrapper create(SearchResult searchResult, UserService userService, MeetingRoomService meetingRoomService) {

        SearchResultWrapper res = new SearchResultWrapper(searchResult);
        List<MeetingWrapper> meetingWrappers = MeetingWrapper.create(searchResult.getMeetings(), userService, meetingRoomService, "200");
        res.setMeetings(meetingWrappers);
        return res;
    }

    public void setMeetings(List<MeetingWrapper> meetingWrappers) {
        this.meetings = meetingWrappers;
    }

    public List<MeetingWrapper> getMeetings() {
        return meetings;
    }

    private SearchResultWrapper(SearchResult searchResult) {
        this.meetingNotes = searchResult.meetingNotes;
        this.users = searchResult.users;
        this.meetingRooms = searchResult.meetingRooms;
        this.totalCount = searchResult.totalCount;
    }
}
