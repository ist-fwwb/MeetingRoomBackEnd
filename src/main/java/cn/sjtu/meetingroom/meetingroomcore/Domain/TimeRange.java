package cn.sjtu.meetingroom.meetingroomcore.Domain;

import java.io.Serializable;

public class TimeRange implements Serializable {
    int start;
    int end;

    public TimeRange() {
    }

    public TimeRange(int start, int end){
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "TimeRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

}
