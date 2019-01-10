package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingRoomService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MeetingRoomUtils;
import cn.sjtu.meetingroom.meetingroomcore.Util.Size;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Validated
@RequestMapping("/meetingroom")
@RestController
@Api(value="MeetingRoom", description = "MeetingRoomController")
public class MeetingRoomController {
    @Autowired
    MeetingRoomService meetingRoomService;

    @GetMapping("/")
    @ApiOperation(value="get the meetingrom's detail information through conditions")
    public Page<MeetingRoom> show(@RequestParam(name="pageNumber") int pageNumber,
                                     @RequestParam(name="pageSize") int pageSize,
                                  @RequestParam(name="utils", required = false) MeetingRoomUtils[] utils,
                                  @RequestParam(name="size", required = false) Size size){
        PageRequest pageRequest = Util.createPageRequest(pageNumber, pageSize);
        Page<MeetingRoom> meetingRooms = meetingRoomService.showAll(pageRequest);
        if (utils == null && size == null) return meetingRooms;
        else if (utils == null) return meetingRoomService.findBySize(size, pageRequest);
        else if (size == null) return meetingRoomService.findByUtils(Arrays.asList(utils), meetingRooms);
        else return meetingRoomService.findByUtils(Arrays.asList(utils), meetingRoomService.findBySize(size, pageRequest));
    }

    @GetMapping("/{id}")
    @ApiOperation(value="get a specified meetingroom")
    public MeetingRoom showOne(@PathVariable(name="id") String id){
        return meetingRoomService.findById(id);
    }

    @PostMapping("/")
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
