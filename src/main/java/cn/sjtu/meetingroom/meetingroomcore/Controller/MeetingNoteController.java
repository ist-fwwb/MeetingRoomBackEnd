package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNote;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingNoteWrapper;
import cn.sjtu.meetingroom.meetingroomcore.Service.MeetingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetingNote")
public class MeetingNoteController {
    @Autowired
    MeetingNoteService meetingNoteService;

    @GetMapping("")
    public List<MeetingNoteWrapper> show(@RequestParam(name = "userId") String userId,
                                         @RequestParam(name = "meetingId", required = false) String meetingId,
                                         @RequestParam(name = "ownerId", required = false) String ownerId){
        List<MeetingNote> res = meetingNoteService.showAll();
        if (meetingId != null) res = meetingNoteService.findByMeetingId(meetingId, res);
        if (ownerId != null) res = meetingNoteService.findByOwnerId(ownerId, res);
        return MeetingNoteWrapper.create(res, userId);
    }

    @GetMapping("/{id}")
    public MeetingNote showOne(@PathVariable(name = "id") String id){
        return meetingNoteService.showOne(id);
    }

    @PostMapping("")
    public MeetingNote add(@RequestParam(name = "fileName") String fileName,
                           @RequestBody MeetingNote meetingNote){
        switch (meetingNote.getMeetingNoteType()){
            case HTML:
                return meetingNoteService.saveHTMLType(meetingNote);
            case VOICE:
                return meetingNoteService.saveVOICEType(meetingNote);
            default:
                return null;
        }
    }

    @PostMapping("/{id}/collectors")
    public MeetingNote addCollector(@PathVariable(name = "id") String id,
                                    @RequestBody String userId){
        return meetingNoteService.collect(id, userId);
    }

    @DeleteMapping("/{id}/collectors")
    public MeetingNote deleteCollector(@PathVariable(name = "id") String id,
                                    @RequestBody String userId){
        return meetingNoteService.deleteCollect(id, userId);
    }
}
