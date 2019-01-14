package cn.sjtu.meetingroom.meetingroomcore.Controller;


import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Validated
@RequestMapping("/meeting")
@RestController
@Api(value="Meeting", description = "MeetingController")
public class MeetingController {
    @Autowired
    MeetingService meetingService;
    @PostMapping("/")
    @ApiOperation("registor a meeting, StatusNum: { '200' : 'success', '400' : 'fail' }")
    public ResponseEntity<Meeting> addMeeting(@RequestBody Meeting meeting){
        Meeting res = meetingService.add(meeting);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("StatusNum", "200");
        return new ResponseEntity<Meeting>(res, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation("get all of the meeting through condition")
    public Page<Meeting> getMeetings(@RequestParam(name="pageNumber") int pageNumber,
                                    @RequestParam(name="pageSize") int pageSize,
                                    @RequestParam(name="date") String date,
                                    @RequestParam(name="roomId") String roomId){
        PageRequest pageRequest = Util.createPageRequest(pageNumber, pageSize);
        Page<Meeting> meetings = meetingService.findByDateAndRoomId(date, roomId, pageRequest);
        return meetings;
    }

    @PostMapping("/{attendantNum}/attendants")
    @ApiOperation("attendent to the meeting , StatusNum: { '200' : 'success', '400' : 'fail' }")
    public ResponseEntity<Meeting> attend(@PathVariable(name = "attendantNum") String attendantNum,
                                          @RequestParam(name="userId") String userId){
        Meeting res = meetingService.attend(attendantNum, userId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("StatusNum", "200");
        return new ResponseEntity<Meeting>(res, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("get the detail information of a specified meeting room")
    public Meeting getMeeting(@PathVariable(name="id") String id){
        return meetingService.findById(id);
    }

    @PutMapping("/")
    @ApiOperation("modify the status of the meeting")
    public Meeting modify(@RequestBody Meeting meeting){
        //TODO
        return null;
    }
}
