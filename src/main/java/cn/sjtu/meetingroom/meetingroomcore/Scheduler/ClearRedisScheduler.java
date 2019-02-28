package cn.sjtu.meetingroom.meetingroomcore.Scheduler;


import cn.sjtu.meetingroom.meetingroomcore.Domain.QueueNode;
import cn.sjtu.meetingroom.meetingroomcore.Service.MessageService;
import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
import cn.sjtu.meetingroom.meetingroomcore.Util.MessageFactory;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ClearRedisScheduler {
    @Autowired
    QueueNodeService queueNodeService;
    @Autowired
    MessageService messageService;

    @Scheduled(cron="0 0 23 * * *")
    public void process(){
        System.out.println("At " + new Date() + " dump the redis");
        List<QueueNode> queueNodes = queueNodeService.deleteByDate(Util.getDate());
        for (QueueNode queueNode : queueNodes){
            messageService.createWithoutMeetingId(queueNode.getUserId(), MessageFactory.createQueueFailTitle(), MessageFactory.createQueueFailureMessage(queueNode));
        }
    }
}
