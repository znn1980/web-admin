package com.ifacebox.web.common.model;

/**
 * @author znn
 */
public class TimeBetween extends Pagination {
    protected String startTime;
    protected String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
