package com.example.pennapps.pennapps2018;

import com.google.gson.annotations.SerializedName;

public class Gym {
    private String name;
    @SerializedName("start")
    private String startTime;
    @SerializedName("end")
    private String endTime;
    @SerializedName("all_day")
    private boolean allDay;

    public Gym(String name, String startTime, String endTime, boolean allDay) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.allDay = allDay;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        if (allDay) {
            return "";
        }
        return startTime;
    }

    public String getEndTime() {
        if (allDay) {
            return "";
        }
        return endTime;
    }
}
