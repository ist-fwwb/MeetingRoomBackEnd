package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Service.TimeSliceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequestMapping("/timeSlice")
@RestController
@Api(value="TimeSlice", description = "TimeSliceController")
public class TimeSliceController {

    @Autowired
    TimeSliceService timeSliceService;

    @GetMapping("")
    @ApiOperation(value="get the timeSlice's detail information through roomId and date")
    public List<TimeSlice> show(@RequestParam(name="date", required = false) String date,
                                @RequestParam(name="roomId") String roomId){
        List<TimeSlice> timeSlices = timeSliceService.show(roomId);
        if (date != null) timeSlices = timeSliceService.findByDate(date, timeSlices);
        return timeSlices;
    }

}
