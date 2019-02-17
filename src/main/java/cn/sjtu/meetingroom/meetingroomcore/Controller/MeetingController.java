package cn.sjtu.meetingroom.meetingroomcore.Controller;


import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingWrapper;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/meeting")
@RestController
@Api(value="Meeting", description = "MeetingController")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    @Autowired
    UserService userService;

    @Autowired
    MeetingRoomService meetingRoomService;

    @GetMapping("")
    @ApiOperation("get all of the meeting through condition, if the condition contains time then by default return the List<Meeting> with a Running status or Pending status")
    public List<MeetingWrapper> getMeetings(
                                    @RequestParam(name="date", required = false) String date,
                                    @RequestParam(name="roomId", required = false) String roomId,
                                     @RequestParam(name="time", required = false) Integer time,
                                    @RequestParam(name="status", required = false) Status status,
                                    @RequestParam(name="location", required = false) String location){
        List<Meeting> meetings = meetingService.showAll();
        if (date != null) meetings = meetingService.findByDate(date, meetings);
        if (roomId != null) meetings = meetingService.findByRoomId(roomId, meetings);
        if (time != null) meetings = meetingService.findByTime(time, meetings);
        if (status != null) meetings = meetingService.findByStatus(status, meetings);
        if (location != null) meetings = meetingService.findByLocation(location, meetings);
        return MeetingWrapper.create(meetings, userService, getErrorNum(true));
    }

    @GetMapping("/{id}")
    @ApiOperation("get the detail information of a specified meeting")
    public Meeting getMeeting(@PathVariable(name="id") String id){
        return meetingService.findById(id);
    }

    @GetMapping("/{id}/attendants")
    @ApiOperation("get user information of all attendants")
    public List<User> getMeetingAttendants(@PathVariable(name="id") String id){
        return meetingService.findAttendants(id);
    }

    @PostMapping("/intelligent")
    @ApiOperation("intelligently register a meeting")
    public Meeting intelligentlyAddMeeting(@RequestBody Meeting origin,
                                           @RequestParam(name="utils", required = false) List<MeetingRoomUtils> utils,
                                           @RequestParam(name="size", required = false) Size size){
        List<MeetingRoom> meetingRooms = meetingRoomService.showAll();
        meetingRooms = meetingRoomService.findByStartTimeAndEndTime(origin.getStartTime(), origin.getEndTime(), origin.getDate(), meetingRooms);
        if (size != null) meetingRooms = meetingRoomService.findBySize(size, meetingRooms);
        if (utils != null) meetingRooms = meetingRoomService.findByUtils(utils, meetingRooms);
        Meeting meeting = meetingService.intelligentlyFindMeeting(origin, meetingRooms);
        return meeting == null ? new Meeting() : meetingService.add(meeting);
    }

    @PostMapping("")
    @ApiOperation("register a meeting")
    public Meeting addMeeting(@RequestBody Meeting meeting){
        return meetingService.add(meeting);
    }

    @PostMapping("/{attendantNum}/attendants")
    @ApiOperation("attend to the meeting By attendantNum or meetingId")
    public Meeting attend(@PathVariable(name = "attendantNum") String attendantNum,
                                          @RequestParam(name="userId") String userId){
        return meetingService.attend(attendantNum, userId);
    }

    @PutMapping("/{id}")
    @ApiOperation("modify the meeting")
    public MeetingWrapper modify(@RequestBody Meeting meeting, @PathVariable(name="id") String id){
        boolean isValidate = meetingService.modify(meeting, id);
        return MeetingWrapper.create(meetingService.findById(id), userService, getErrorNum(isValidate));
    }

    private String  getErrorNum(boolean isValidate) {
        return isValidate ? "200" : "400";
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation("change a meeting's status")
    public String modifyStatus(@PathVariable(name="id") String id, @PathVariable(name="status") Status status){
        switch (status){
            case Cancelled:
                meetingService.cancelMeeting(id);
                break;
            case Stopped:
                meetingService.stopMeeting(id);
                break;
            case Running:
                meetingService.startMeeting(id);
                break;
        }
        return "ok";
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete a meeting")
    public String delete(@PathVariable(name="id") String id){
        meetingService.cancelMeeting(id);
        return "ok";
    }


    @DeleteMapping("/{id}/attendants/{userId}")
    @ApiOperation("exit from a meeting")
    public String exitFromMeeting(@PathVariable(name="id") String id, @PathVariable(name="userId") String userId){
        meetingService.exitFromMeeting(id, userId);
        return "ok";
    }
}
