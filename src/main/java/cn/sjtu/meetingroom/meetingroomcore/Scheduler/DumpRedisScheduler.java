package cn.sjtu.meetingroom.meetingroomcore.Scheduler;


import cn.sjtu.meetingroom.meetingroomcore.Service.QueueNodeService;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DumpRedisScheduler {
    @Autowired
    QueueNodeService queueNodeService;

    @Scheduled(cron="0 0 23 * * *")
    public void dumpRedis(){
        System.out.println("At " + new Date() + " dump the redis");
        queueNodeService.deleteByDate(Util.getDate());
    }
}
