package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class TimeSliceScheduler {
    @Autowired
    TimeSliceRepository timeSliceRepository;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // s m h d M w
    @Scheduled(cron = "0 0 1 * * *")
    public void updateLastDayTimeSlice() throws Exception{
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        List<TimeSlice> timeSliceList = timeSliceRepository.findAllByDateLike(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DATE, 8);
        updateTimeSlice(timeSliceList, calendar);
    }

    public void updateTimeSlice(List<TimeSlice> timeSliceList, Calendar calendar){
        for (TimeSlice timeSlice : timeSliceList){
            timeSlice.getTimeSlice().replaceAll((x) -> null);
            timeSlice.setDate(sdf.format(calendar.getTime()));
            timeSliceRepository.save(timeSlice);
        }
    }

    public void setTimeSliceRepository(TimeSliceRepository timeSliceRepository) {
        this.timeSliceRepository = timeSliceRepository;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
}
