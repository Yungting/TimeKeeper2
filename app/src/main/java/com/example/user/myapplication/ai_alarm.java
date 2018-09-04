package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class ai_alarm extends Activity {

    TextView group, ai_manage, alarm_number;
    LinearLayout rington;
    LinearLayout ai_layout;
    LinearLayout repeat_day;
    AlarmManager am;
    CheckBox day_Su, day_M, day_T, day_W, day_Th, day_F, day_S, repeat_checkbox;
    Calendar calendar = Calendar.getInstance();
    String repeat_text;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_number = (TextView) findViewById(R.id.alarm_number);
        alarm_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                new TimePickerDialog(ai_alarm.this, new TimePickerDialog.OnTimeSetListener(){

                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        alarm_number.setText(hourOfDay + ":" + minute1);

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute1);
                        calendar.set(Calendar.SECOND, 0);
                    }
                }, hour, minute, false).show();
            }
        });

        group = findViewById(R.id.group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_group.class);
                startActivity(intent1);
            }
        });

        ai_manage = findViewById(R.id.ai_manage);
        ai_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_manage.class);
                startActivity(intent1);
            }
        });

        rington = findViewById(R.id.rington);
        rington.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_music.class);
                startActivity(intent1);
            }
        });

        //展開
        ai_layout = findViewById(R.id.repeat_layout);
        repeat_day = findViewById(R.id.repeat_day);

        ai_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeat_day.getVisibility() == View.VISIBLE){
                    repeat_day.setVisibility(View.GONE);
                    pickday();
                }else{
                    repeat_day.setVisibility(View.VISIBLE);
                    pickday();
                }
            }
        });
    }

    public void pickday(){
        repeat_text="";
        day_Su = (CheckBox) findViewById(R.id.su);
        day_M = (CheckBox) findViewById(R.id.m);
        day_T = (CheckBox) findViewById(R.id.T);
        day_W = (CheckBox) findViewById(R.id.W);
        day_Th = (CheckBox) findViewById(R.id.Th);
        day_F = (CheckBox) findViewById(R.id.F);
        day_S = (CheckBox) findViewById(R.id.S);

        if (day_Su.isChecked()){ repeat_text = "Su "; }
        if (day_M.isChecked()){ repeat_text = repeat_text+"M  "; }
        if (day_T.isChecked()){ repeat_text = repeat_text+"T  "; }
        if (day_W.isChecked()){ repeat_text = repeat_text+"W  "; }
        if (day_Th.isChecked()){ repeat_text = repeat_text+"Th  "; }
        if (day_F.isChecked()){ repeat_text = repeat_text+"F  "; }
        if (day_S.isChecked()){ repeat_text = repeat_text+"S  "; }
        TextView repeat_show = findViewById(R.id.repeat_show);
        repeat_show.setText(repeat_text);

    }

    public void setAlarm(){
        repeat_checkbox = (CheckBox)findViewById(R.id.repeat_checkbox);
        List<Integer> alarmnum;

        Intent intent = new Intent(getApplication(), ai_alarmalert.class);

        if (repeat_checkbox.isChecked()){
            if (day_Su.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 1);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            }
            if (day_M.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            }
            if (day_T.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 3);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            }
            if (day_W.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 4);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            }
            if (day_Th.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 5);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);}
            if (day_F.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 6);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            }
            if (day_S.isChecked()){
                calendar.set(Calendar.DAY_OF_WEEK, 7);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            }
        }else {
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }

    }
}
