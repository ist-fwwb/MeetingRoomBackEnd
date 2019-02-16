package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
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
                                @RequestParam(name="roomId", required = false) String roomId){
        List<QueueNode> res = queueNodeService.findAll();
        if (userId != null) res = queueNodeService.findByUserId(userId, res);
        if (roomId != null) res = queueNodeService.findByRoomId(roomId, res);
        return res;
    }

    @PostMapping("")
    public QueueNode add(@RequestBody QueueNode queueNode){
        return queueNodeService.add(queueNode);
    }

    @DeleteMapping("/{id}")
    public void remove(@RequestParam(name="roomId") String roomId,
                       @PathVariable(name="id") String id){
        queueNodeService.delete(id, roomId);
    }
}
