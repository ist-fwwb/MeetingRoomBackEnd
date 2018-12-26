package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeSliceScheduler {
    @Scheduled(fixedRate = 5000)
    public void test(){
        System.out.println(new Date()
        );
    }
}
