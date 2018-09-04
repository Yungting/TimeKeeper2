package com.example.user.myapplication;

public class mainpage_RowModel {
    String title, repeatday, alarm_time,type;
    int state;

    public mainpage_RowModel(String mainText, String subText, String alarm_time, String type, int state) {
        this.title = mainText;
        this.repeatday = subText;
        this.alarm_time = alarm_time;
        this.type = type;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mainText) {
        this.title = mainText;
    }

    public String getRepeatday() {
        return repeatday;
    }

    public void setRepeatday(String subText) {
        this.repeatday = subText;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
