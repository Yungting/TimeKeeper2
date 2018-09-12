package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.user.myapplication.R;
import com.example.user.myapplication.ai_alarmalert;
import com.example.user.myapplication.normal_alarm_music;

import java.util.Calendar;
import java.util.List;

public class normal_alarm extends Activity {
    AlarmManager am2;
    TextView alarm_number;
    Calendar calendar2 = Calendar.getInstance();
    int hour,minute, i, requestcode;
    String repeat_text, index, mimeType, audioFilePath, alarmtime;
    CheckBox day_Su, day_M, day_T, day_W, day_Th, day_F, day_S, repeat_checkbox;
    LinearLayout rington;
    LinearLayout repeat_layout,repeat_day;
    int[] repeatday = new int[7];
    int rcode1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.normal_alarm);
        TextView normal_edit_title = findViewById(R.id.normal_edit_title);

        Intent intentcode = getIntent();
        if (intentcode!= null){
            rcode1 = intentcode.getIntExtra("requestcode", 0);
        }
        am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_number = (TextView) findViewById(R.id.alarm_number);
        if (rcode1 == 0){
            calendar2.setTimeInMillis(System.currentTimeMillis());
        }else{
            DB_normal_alarm db = new DB_normal_alarm(this);
            Cursor cursor = db.selectbycode(rcode1);
            if (cursor != null && cursor.moveToFirst()){
                Long t = Long.parseLong(cursor.getString(6));
                calendar2.setTimeInMillis(t);
                normal_edit_title.setText(cursor.getString(5));
                index = cursor.getString(2);
                audioFilePath = cursor.getString(1);
                Log.d("index",":"+cursor.getString(1));
            }

        }
        alarmpicker();

        rington = findViewById(R.id.rington);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (bundle.getString("from") != null){
                index = bundle.getString("index");
                mimeType = bundle.getString("mimeType");
                audioFilePath = bundle.getString("audioFilePath");
            }
        }

        if (index == null){index = "Default";}
        TextView rington_show = findViewById(R.id.rington_show);
        rington_show.setText(index);
        rington.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(normal_alarm.this, normal_alarm_music.class);
                intent1.putExtra("rcode", rcode1);
                startActivity(intent1);
            }
        });

        //展開
        repeat_layout = findViewById(R.id.repeat_layout);
        repeat_day = findViewById(R.id.repeat_day);

        repeat_layout.setOnClickListener(new View.OnClickListener() {
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

    //////////Method//////////
    public void alarmpicker(){
        hour = calendar2.get(Calendar.HOUR_OF_DAY);
        minute = calendar2.get(Calendar.MINUTE);
        alarm_number.setText(zeroinclock(hour, minute));
        alarm_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(normal_alarm.this, new TimePickerDialog.OnTimeSetListener(){

                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        alarm_number.setText(zeroinclock(hourOfDay, minute1));
                        alarmtime = hourOfDay+":"+minute1;
                        calendar2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar2.set(Calendar.MINUTE, minute1);
                        calendar2.set(Calendar.SECOND, 0);
                    }
                }, hour, minute, false).show();
            }
        });
    }

    public String zeroinclock(int hour, int min){
        String time = "";
        if (hour < 10 && minute < 10){
            return time = "0"+hour+" : 0"+minute;
        }else if (hour < 10 && minute>=10){
            return time = "0"+hour+" : "+minute;
        }else if (hour >= 10 && minute<10){
            return time = hour+" : 0"+minute;
        }else if (hour >= 10 && minute >= 10){ return time = hour+" : "+minute;}
        return time;
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
        TextView normal_edit_title = findViewById(R.id.normal_edit_title);
        String edit_text = normal_edit_title.getText().toString();

        Intent intent = new Intent(this, ai_alarmalert.class);
        intent.putExtra("requestcode", requestcode);
        if (repeat_checkbox.isChecked()){
            PendingIntent pi = PendingIntent.getActivity(this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), 60*1000, pi);
            ifrepeat = true;
        }else {
            PendingIntent pi = PendingIntent.getActivity(this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am2.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pi);
            ifrepeat = false;
        }

        DB_normal_alarm db = new DB_normal_alarm(this);
        String millis = String.valueOf(calendar2.getTimeInMillis());
        db.insert(repeat_text, audioFilePath, index, requestcode, ifrepeat, edit_text, millis,"normal",1);

        Intent intent_set = new Intent();
        intent_set.setClass(this, mainpage.class);
        startActivity(intent_set);
    }

    public void updateAlarm(){

    }

}
