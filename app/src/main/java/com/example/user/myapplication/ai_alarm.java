package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.user.myapplication.ai_alarm_manage.ai_alarm_manage;

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
    String repeat_text, index, mimeType, audioFilePath, alarmtime;
    int[] repeatday = new int[7];
    int hour,minute, i, requestcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar.setTimeInMillis(System.currentTimeMillis());
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        alarm_number.setText(hour+" : "+minute);
        alarm_number = findViewById(R.id.alarm_number);
        alarm_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ai_alarm.this, new TimePickerDialog.OnTimeSetListener(){

                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        alarm_number.setText(hourOfDay + " : " + minute1);
                        alarmtime = hourOfDay+":"+minute1;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            index = bundle.getString("index");
            mimeType = bundle.getString("mimeType");
            audioFilePath = bundle.getString("audioFilePath");
        }
        if (index == null){index = "Default";}
        TextView rington_show = findViewById(R.id.rington_show);
        rington_show.setText(index);
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

        Button creat_btn = findViewById(R.id.creat_btn);
        creat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
    }

    public void pickday(){
        repeat_text="";
        i = 0;
        day_Su = (CheckBox) findViewById(R.id.su);
        day_M = (CheckBox) findViewById(R.id.m);
        day_T = (CheckBox) findViewById(R.id.T);
        day_W = (CheckBox) findViewById(R.id.W);
        day_Th = (CheckBox) findViewById(R.id.Th);
        day_F = (CheckBox) findViewById(R.id.F);
        day_S = (CheckBox) findViewById(R.id.S);

        if (day_Su.isChecked()){ repeat_text = "Su "; repeatday[i] = 1; i++;}
        if (day_M.isChecked()){ repeat_text = repeat_text+"M  "; repeatday[i] = 2; i++;}
        if (day_T.isChecked()){ repeat_text = repeat_text+"T  "; repeatday[i] = 3; i++;}
        if (day_W.isChecked()){ repeat_text = repeat_text+"W  "; repeatday[i] = 4; i++;}
        if (day_Th.isChecked()){ repeat_text = repeat_text+"Th  "; repeatday[i] = 5; i++;}
        if (day_F.isChecked()){ repeat_text = repeat_text+"F  "; repeatday[i] = 6; i++;}
        if (day_S.isChecked()){ repeat_text = repeat_text+"S  "; repeatday[i] = 7; i++;}
        TextView repeat_show = findViewById(R.id.repeat_show);
        repeat_show.setText(repeat_text);
    }

    public void setAlarm(){
        repeat_checkbox = (CheckBox)findViewById(R.id.repeat_checkbox);
        requestcode = (int)System.currentTimeMillis();
        Boolean ifrepeat;
        String ai_edit_title = findViewById(R.id.ai_edit_title).toString();

        Intent intent = new Intent(this, ai_alarmalert.class);

        if (repeat_checkbox.isChecked()){
            PendingIntent pi = PendingIntent.getActivity(this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pi);
            ifrepeat = true;
        }else {
            PendingIntent pi = PendingIntent.getActivity(this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
            ifrepeat = false;
        }

        Intent intent_set = new Intent();
        intent_set.setClass(this, mainpage.class);
        Bundle bundle = new Bundle();
        bundle.putString("repeat_text", repeat_text);
        bundle.putIntArray("repeatday", repeatday);
        bundle.putString("mimeType", mimeType);
        bundle.putString("audioFilePath", audioFilePath);
        bundle.putInt("requestcode", requestcode);
        bundle.putBoolean("ifrepeat", ifrepeat);
        bundle.putString("edit_title", ai_edit_title);
        bundle.putString("alarmtime", alarmtime);
        bundle.putString("type", "normal");
        intent_set.putExtras(bundle);
        startActivity(intent_set);

    }
}
