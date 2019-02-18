package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/QueueNode")
public class QueueNodeController {
    @Autowired
    QueueNodeService queueNodeService;

    @GetMapping("")
    public List<QueueNode> find(@RequestParam(name="userId", required = false) String userId,
                                @RequestParam(name="roomId", required = false) String roomId,
                                @RequestParam(name="date", required = false) String date){
        List<QueueNode> res = queueNodeService.findAll();
        if (userId != null) res = queueNodeService.findByUserId(userId, res);
        if (roomId != null) res = queueNodeService.findByRoomId(roomId, res);
        if (date != null) res = queueNodeService.findByDate(date, res);
        return res;
    }

    @GetMapping("/{id}")
    public void findById(@PathVariable(name="id") String id){
        queueNodeService.findById(id);
    }

    @PostMapping("")
    @ApiOperation("如果是任意会议室则用‘1’来代替")
    public QueueNode add(@RequestBody QueueNode queueNode){
        return queueNodeService.add(queueNode);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable(name="id") String id){
        queueNodeService.delete(id);
    }
}
