package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.Message;
import cn.sjtu.meetingroom.meetingroomcore.Enum.MessageStatus;
import cn.sjtu.meetingroom.meetingroomcore.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping("")
    public List<Message> getMessages(@RequestParam(name = "userId") String userId){
        return messageService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable(name = "id") String id){
        return messageService.findById(id);
    }

    @PutMapping("/{id}/messageStatus")
    public void changeStatus(@PathVariable(name = "id") String id,
                             @RequestParam(name = "status") MessageStatus messageStatus){
        switch (messageStatus){
            case READ:
                messageService.read(id);
                return;
            case DELETE:
                messageService.delete(id);
                return;
            default:
                return;
        }
    }

}
