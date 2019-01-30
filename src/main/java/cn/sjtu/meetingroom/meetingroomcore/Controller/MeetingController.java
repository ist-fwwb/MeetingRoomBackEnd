package cn.sjtu.meetingroom.meetingroomcore.Controller;


import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingWrapper;
import cn.sjtu.meetingroom.meetingroomcore.Domain.User;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Service.UserService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        List<MeetingWrapper> meetingWrappers = new ArrayList<>();
        for (Meeting meeting : meetings) {
            MeetingWrapper meetingWrapper = new MeetingWrapper(meeting);
            meetingWrapper.setHost(userService.showOne(meeting.getHostId()));
            meetingWrappers.add(meetingWrapper);
        }

        return meetingWrappers;
    }

    @GetMapping("/{id}")
    @ApiOperation("get the detail information of a specified meeting room")
    public Meeting getMeeting(@PathVariable(name="id") String id){
        return meetingService.findById(id);
    }

    @GetMapping("/{id}/attendants")
    @ApiOperation("get user information of all attendants")
    public List<User> getMeetingAttendants(@PathVariable(name="id") String id){
        return meetingService.findAttendants(id);
    }

    @PostMapping("")
    @ApiOperation("registor a meeting, StatusNum: { '200' : 'success', '400' : 'fail' }")
    public ResponseEntity<Meeting> addMeeting(@RequestBody Meeting meeting){
        Meeting res = meetingService.add(meeting);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("StatusNum", "200");
        return new ResponseEntity<Meeting>(res, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/{attendantNum}/attendants")
    @ApiOperation("attendent to the meeting By attendantNum or meetingId , StatusNum: { '200' : 'success', '400' : 'fail' }")
    public ResponseEntity<Meeting> attend(@PathVariable(name = "attendantNum") String attendantNum,
                                          @RequestParam(name="userId") String userId){
        Meeting res = meetingService.attend(attendantNum, userId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("StatusNum", "200");
        return new ResponseEntity<Meeting>(res, responseHeaders, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation("modify the meeting")
    public MeetingWrapper modify(@RequestBody Meeting meeting, @PathVariable(name="id") String id){
        boolean isValidate = meetingService.modify(meeting, id);
        MeetingWrapper res = new MeetingWrapper(meetingService.findById(id));
        res.setHost(userService.showOne(meetingService.findById(id).getHostId()));
        if (isValidate) res.setErrorNum("200");
        else res.setErrorNum("400");
        return res;
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation("change a meeting's status")
    public String modifyStatus(@PathVariable(name="id") String id, @PathVariable(name="status") Status status){
        switch (status){
            case Cancelled:
                meetingService.cancelMeeting(id);
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
