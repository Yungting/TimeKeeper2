package com.example.user.myapplication;

public class mainpage_RowModel {
    String repeat_txt, mime_type, normal_edit_title, type, alarmtime;
    int request_code, state;
    boolean ifrepeat;

    public mainpage_RowModel(String repeat_txt , String mime_type, int request_code, boolean ifrepeat, String normal_edit_title , String alarmtime, String type, int state) {
        this.repeat_txt = repeat_txt;
        this.mime_type = mime_type;
        this.request_code = request_code;
        this.ifrepeat = ifrepeat;
        this.normal_edit_title = normal_edit_title;
        this.alarmtime = alarmtime;
        this.type = type;
        this.state = state;
    }

    public String getNormal_edit_title() {
        return normal_edit_title;
    }
    public String getMime_type(){return  mime_type;}
    public int getRequestcode() {
        return request_code;
    }
    public boolean isIfrepeat(){return  ifrepeat;}
    public String getRepeat(){ return repeat_txt;}
    public String getAlarm_time() {
        return alarmtime;
    }
    public String getType() {
        return type;
    }
    public int getState() {
        return state;
    }


    public String getRepeatday() {
        return normal_edit_title;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setState(int state) {
        this.state = state;
    }
}
