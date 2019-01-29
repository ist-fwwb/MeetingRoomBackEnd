package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RequestMapping("/meetingroom")
@RestController
@Api(value="MeetingRoom", description = "MeetingRoomController")
public class MeetingRoomController {
    @Autowired
    MeetingRoomService meetingRoomService;

    @GetMapping("")
    @ApiOperation(value="get the meetingrom's detail information through conditions")
    public List<MeetingRoom> show(@RequestParam(name="utils", required = false) MeetingRoomUtils[] utils,
                                  @RequestParam(name="size", required = false) Size size,
                                  @RequestParam(name="startTime", required = false) Integer startTime,
                                  @RequestParam(name="endTime", required = false) Integer endTime,
                                  @RequestParam(name="date", required = false) String date,
                                  @RequestParam(name="location", required = false) String location){
        List<MeetingRoom> meetingRooms = meetingRoomService.showAll();
        if (utils != null) meetingRooms = meetingRoomService.findByUtils(Arrays.asList(utils), meetingRooms);
        if (size != null) meetingRooms = meetingRoomService.findBySize(size, meetingRooms);
        if (startTime != null && endTime != null && date != null) meetingRooms = meetingRoomService.findByStartTimeAndEndTime(startTime, endTime, date, meetingRooms);
        if (location != null) meetingRooms = meetingRoomService.findByLocation(location, meetingRooms);
        return meetingRooms;
    }

    @GetMapping("/{id}")
    @ApiOperation(value="get a specified meetingroom")
    public MeetingRoom showOne(@PathVariable(name="id") String id){
        return meetingRoomService.findById(id);
    }

    @PostMapping("")
    @ApiOperation(value="add a meetingroom (id should be null)")
    public MeetingRoom add(@RequestBody MeetingRoom meetingRoom){
        return meetingRoomService.add(meetingRoom);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="modify a meetingroom")
    public MeetingRoom modify(@RequestBody MeetingRoom meetingRoom){
        return meetingRoomService.modify(meetingRoom);
    }
}
