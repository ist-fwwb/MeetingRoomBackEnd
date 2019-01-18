package cn.sjtu.meetingroom.meetingroomcore.Controller;


import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    @ApiOperation("get all of the meeting through condition, if the condition contains time then return the Page<Meeting> with a Running status or Pending status")
    public List<Meeting> getMeetings(
                                    @RequestParam(name="date", required = false) String date,
                                    @RequestParam(name="roomId", required = false) String roomId,
                                     @RequestParam(name="time", required = false) Integer time){
        List<Meeting> meetings = meetingService.showAll();
        if (date != null) meetings = meetingService.findByDate(date, meetings);
        if (roomId != null) meetings = meetingService.findByRoomId(roomId, meetings);
        if (time != null) meetings = meetingService.findByTime(time, meetings);
        return meetings;
    }

    @GetMapping("/{id}")
    @ApiOperation("get the detail information of a specified meeting room")
    public Meeting getMeeting(@PathVariable(name="id") String id){
        return meetingService.findById(id);
    }

    @PostMapping("/")
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
    @ApiOperation("modify the status of the meeting")
    public Meeting modify(@RequestBody Meeting meeting){
        //TODO
        return null;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("cancel a meeting")
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
