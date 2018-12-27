package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import org.hamcrest.core.AnyOf;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TimeSliceSchedulerTest {

    TimeSliceScheduler timeSliceScheduler;

    TimeSliceRepository timeSliceRepository;

    List<TimeSlice> timeSliceList;

    TimeSlice timeSlice;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void init(){
        timeSliceScheduler = new TimeSliceScheduler();
        timeSliceRepository = mock(TimeSliceRepository.class);
        timeSliceList = new ArrayList<>();
        timeSlice = new TimeSlice();
        timeSlice.setDate(sdf.format(new Date()));
        timeSliceList.add(timeSlice);
        when(timeSliceRepository.findAllByDateLike(anyString())).thenReturn(timeSliceList);
    }

    @Test
    public void updateLastDayTimeSlice() throws Exception {
        timeSliceScheduler.setTimeSliceRepository(timeSliceRepository);
        timeSliceScheduler.updateLastDayTimeSlice();
        verify(timeSliceRepository, times(1)).save(anyObject());
        for (int i=0; i<timeSlice.getTimeSlice().size(); ++i) assertNull(timeSlice.getTimeSlice().get(i));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 7);
        assertEquals(sdf.format(calendar.getTime()), timeSlice.getDate());
    }
}