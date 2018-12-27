package cn.sjtu.meetingroom.meetingroomcore.Scheduler;

import cn.sjtu.meetingroom.meetingroomcore.Dao.MeetingRoomRepository;
import cn.sjtu.meetingroom.meetingroomcore.Dao.TimeSliceRepository;
import cn.sjtu.meetingroom.meetingroomcore.Domain.Meeting;
import cn.sjtu.meetingroom.meetingroomcore.Domain.MeetingRoom;
import cn.sjtu.meetingroom.meetingroomcore.Domain.TimeSlice;
import cn.sjtu.meetingroom.meetingroomcore.Util.TimeSliceFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class InitTimeSliceApplicationRunnerTest {

    InitTimeSliceApplicationRunner initTimeSliceApplicationRunner;
    TimeSliceFactory timeSliceFactory;
    MeetingRoomRepository meetingRoomRepository;
    TimeSliceRepository timeSliceRepository;
    List<MeetingRoom> meetingRooms;

    @Before
    public void init(){
        initTimeSliceApplicationRunner = new InitTimeSliceApplicationRunner();
        meetingRoomRepository = mock(MeetingRoomRepository.class);
        timeSliceRepository = mock(TimeSliceRepository.class);
        timeSliceFactory = mock(TimeSliceFactory.class);
        meetingRooms = new ArrayList<>();
        MeetingRoom meetingRoom = mock(MeetingRoom.class);
        when(meetingRoom.getId()).thenReturn("123");
        meetingRooms.add(meetingRoom);
        when(meetingRoomRepository.findAll()).thenReturn(meetingRooms);
        initTimeSliceApplicationRunner.setMeetingRoomRepository(meetingRoomRepository);
        initTimeSliceApplicationRunner.setTimeSliceRepository(timeSliceRepository);
        initTimeSliceApplicationRunner.setTimeSliceFactory(timeSliceFactory);
    }

    @Test
    public void whenExitsThenContinue() throws Exception{
        List<TimeSlice> timeSlices = new ArrayList<>();
        timeSlices.add(new TimeSlice());
        when(timeSliceRepository.findAllByDateLikeAndRoomIdLike(anyString(),anyString())).thenReturn(timeSlices);
        String[] args = new String[0];
        initTimeSliceApplicationRunner.run(new DefaultApplicationArguments(args));
        verify(timeSliceFactory, times(0)).create(anyString(), anyObject());
    }

    @Test
    public void whenNotExitsThenContinue() throws Exception{
        List<TimeSlice> timeSlices = new ArrayList<>();
        when(timeSliceRepository.findAllByDateLikeAndRoomIdLike(anyString(),anyString())).thenReturn(timeSlices);
        String[] args = new String[0];
        initTimeSliceApplicationRunner.run(new DefaultApplicationArguments(args));
        verify(timeSliceFactory, times(1)).create(anyString(), anyObject());
    }
}