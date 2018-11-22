package com.example.user.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.user.myapplication.ai_manage.ai_manage;
import com.example.user.myapplication.ai_group.ai_alarm_group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.user.myapplication.hideinput.hideSoftInput;
import static com.example.user.myapplication.hideinput.isShouldHideInput;

public class ai_alarm extends Activity {
    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    AlarmManager am2;
    TextView group, ai_manage, alarm_number;
    Calendar calendar2 = Calendar.getInstance();
    int hour,minute, i, requestcode;
    String repeat_text, index, mimeType, audioFilePath, alarmtime, rday, idlist, smartlist;
    CheckBox day_Su, day_M, day_T, day_W, day_Th, day_F, day_S, repeat_checkbox;
    LinearLayout rington;
    LinearLayout repeat_layout,repeat_day;
    int[] repeatday = new int[7];
    int rcode1 = 0;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm);
        Button creat_btn = findViewById(R.id.creat_btn);
        TextView ai_edit_title = findViewById(R.id.ai_edit_title);

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
                ai_edit_title.setText(cursor.getString(5));
                index = cursor.getString(2);
                audioFilePath = cursor.getString(1);
                rday = cursor.getString(0);
                idlist = cursor.getString(9);
                if (idlist != null && !idlist.equals("")){
                    CheckBox groupbox = findViewById(R.id.group_checkbox);
                    groupbox.setChecked(true);
                }
                smartlist = cursor.getString(10);
                if (smartlist != null && !smartlist.equals("0  0  ")){
                    CheckBox aibox = findViewById(R.id.ai_manage_checkbox);
                    aibox.setChecked(true);
                }
                pickday();
                creat_btn.setText("UPDATE");
            }
        }
        alarmpicker();

        rington = findViewById(R.id.rington);

        if (index == null){index = "Default";}
        TextView rington_show = findViewById(R.id.rington_show);
        rington_show.setText(index);
        rington.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_music.class);
                intent1.putExtra("rcode", rcode1);
                intent1.putExtra("audiopath", audioFilePath);
                startActivityForResult(intent1, 1);
            }
        });

        final CheckBox repeat_btn = findViewById(R.id.repeat_checkbox);
        repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!repeat_btn.isChecked()){
                    day_Su = (CheckBox) findViewById(R.id.su);
                    day_M = (CheckBox) findViewById(R.id.m);
                    day_T = (CheckBox) findViewById(R.id.T);
                    day_W = (CheckBox) findViewById(R.id.W);
                    day_Th = (CheckBox) findViewById(R.id.Th);
                    day_F = (CheckBox) findViewById(R.id.F);
                    day_S = (CheckBox) findViewById(R.id.S);
                    day_Su.setChecked(false);
                    day_M.setChecked(false);
                    day_T.setChecked(false);
                    day_W.setChecked(false);
                    day_Th.setChecked(false);
                    day_F.setChecked(false);
                    day_S.setChecked(false);
                    rday = "";
                    pickday();
                }
            }
        });

        group = findViewById(R.id.group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_group.class);
                intent1.putExtra("idlist",idlist);
                startActivityForResult(intent1, 1);
            }
        });

        ai_manage = findViewById(R.id.ai_manage);
        ai_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_manage.class);
                intent1.putExtra("sbar", smartlist);
                Log.d("測試slist","安安"+smartlist);
                startActivityForResult(intent1, 1);
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
                    detectday();
                }else{
                    repeat_day.setVisibility(View.VISIBLE);
                    detectday();
                }
            }
        });

        creat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rcode1 == 0){
                    setAlarm();
                }else {
                    updateAlarm(rcode1);
                }
            }
        });

        final CheckBox check_manage = findViewById(R.id.ai_manage_checkbox);
        check_manage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    smartlist = null;
                }else if (smartlist == null){
                    check_manage.setChecked(false);
                }
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
                new TimePickerDialog(ai_alarm.this, new TimePickerDialog.OnTimeSetListener(){

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
    //alarm兩單位
    public String zeroinclock(int hour, int min){
        String time = "";
        if (hour < 10 && min < 10){
            return time = "0"+hour+" : 0"+min;
        }else if (hour < 10 && min>=10){
            return time = "0"+hour+" : "+min;
        }else if (hour >= 10 && min<10){
            return time = hour+" : 0"+min;
        }else if (hour >= 10 && min >= 10){ return time = hour+" : "+min;}
        return time;
    }
    //星期選擇
    public void pickday(){
        TextView repeat_show = findViewById(R.id.repeat_show);
        day_Su = (CheckBox) findViewById(R.id.su);
        day_M = (CheckBox) findViewById(R.id.m);
        day_T = (CheckBox) findViewById(R.id.T);
        day_W = (CheckBox) findViewById(R.id.W);
        day_Th = (CheckBox) findViewById(R.id.Th);
        day_F = (CheckBox) findViewById(R.id.F);
        day_S = (CheckBox) findViewById(R.id.S);
        repeat_checkbox = findViewById(R.id.repeat_checkbox);

        if (rday != null && !rday.equals("")){
            repeat_checkbox.setChecked(true);
            String[] arrays = rday.trim().split("\\s+");
            for(String s : arrays){
                if (s.equals("Su")){ day_Su.setChecked(true);}
                if (s.equals("M")){ day_M.setChecked(true);}
                if (s.equals("T")){ day_T.setChecked(true);}
                if (s.equals("W")){ day_W.setChecked(true);}
                if (s.equals("Th")){ day_Th.setChecked(true);}
                if (s.equals("F")){ day_F.setChecked(true);}
                if (s.equals("S")){ day_S.setChecked(true);}
            }
            repeat_show.setText(rday);
        }else {
            repeat_show.setText(rday);
            repeat_checkbox.setChecked(false);
        }
    }

    public void detectday(){
        TextView repeat_show = findViewById(R.id.repeat_show);
        day_Su = (CheckBox) findViewById(R.id.su);
        day_M = (CheckBox) findViewById(R.id.m);
        day_T = (CheckBox) findViewById(R.id.T);
        day_W = (CheckBox) findViewById(R.id.W);
        day_Th = (CheckBox) findViewById(R.id.Th);
        day_F = (CheckBox) findViewById(R.id.F);
        day_S = (CheckBox) findViewById(R.id.S);
        repeat_checkbox = findViewById(R.id.repeat_checkbox);

        repeat_text="";
        i = 0;

        if (day_Su.isChecked()){ repeat_text = "Su "; repeatday[i] = 1; i++;}
        if (day_M.isChecked()){ repeat_text = repeat_text+"M  "; repeatday[i] = 2; i++;}
        if (day_T.isChecked()){ repeat_text = repeat_text+"T  "; repeatday[i] = 3; i++;}
        if (day_W.isChecked()){ repeat_text = repeat_text+"W  "; repeatday[i] = 4; i++;}
        if (day_Th.isChecked()){ repeat_text = repeat_text+"Th  "; repeatday[i] = 5; i++;}
        if (day_F.isChecked()){ repeat_text = repeat_text+"F  "; repeatday[i] = 6; i++;}
        if (day_S.isChecked()){ repeat_text = repeat_text+"S  "; repeatday[i] = 7; i++;}

        repeat_show.setText(repeat_text);
        if (repeat_text.equals("")){
            repeat_checkbox.setChecked(false);
        }else {
            repeat_checkbox.setChecked(true);
        }
    }
    //新增鬧鐘
    public void setAlarm(){
        repeat_checkbox = (CheckBox)findViewById(R.id.repeat_checkbox);
        requestcode = (int)System.currentTimeMillis();
        Boolean ifrepeat;
        detectday();
        TextView ai_edit_title = findViewById(R.id.ai_edit_title);
        String edit_text = ai_edit_title.getText().toString();

        Intent intent = new Intent(this, normal_alarmalert.class);
        intent.putExtra("requestcode", requestcode);
        if (repeat_checkbox.isChecked() && !repeat_text.equals("")){
            ifrepeat = true;
        }else {
            ifrepeat = false;
        }

        DB_normal_alarm db = new DB_normal_alarm(this);
        String millis = String.valueOf(calendar2.getTimeInMillis());
        db.insert(repeat_text, audioFilePath, index, requestcode, ifrepeat, edit_text, millis,"ai",1, idlist, smartlist);
        db.close();
        Intent service = new Intent(this, BootService.class);
        service.putExtra("req",requestcode);
        startService(service);
        Intent mIntent = new Intent(this, mainpage.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
    }
    //修改鬧鐘
    public void updateAlarm(int requestcode){
        repeat_checkbox = (CheckBox)findViewById(R.id.repeat_checkbox);
        Boolean ifrepeat;
        detectday();
        TextView ai_edit_title = findViewById(R.id.ai_edit_title);
        String edit_text = ai_edit_title.getText().toString();
        Intent intent = new Intent(this, normal_alarmalert.class);
        intent.putExtra("requestcode", requestcode);
        if (repeat_checkbox.isChecked() && !repeat_text.equals("")){
            ifrepeat = true;
        }else {
            ifrepeat = false;
        }

        DB_normal_alarm db = new DB_normal_alarm(this);
        String millis = String.valueOf(calendar2.getTimeInMillis());
        db.updateall(requestcode, repeat_text, audioFilePath, index, ifrepeat, edit_text, millis,"ai",1, idlist, smartlist);
        db.close();
        Intent service = new Intent(this, BootService.class);
        service.putExtra("req",requestcode);
        startService(service);
        Intent mIntent = new Intent(this, mainpage.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }
        if(resultCode == 2){
            if(requestCode== 1){
                if (data.getExtras() != null){
                    index = data.getExtras().getString("index");
                    mimeType = data.getExtras().getString("mimeType");
                    audioFilePath = data.getExtras().getString("audioFilePath");
                    Log.d("index",":"+index);
                    TextView rington_show = findViewById(R.id.rington_show);
                    rington_show.setText(index);
                }
            }
        }
        if (resultCode == 3){
            if (requestCode == 1){
                CheckBox groupbox = findViewById(R.id.group_checkbox);
                idlist = data.getStringExtra("idlist");
                Log.d("idlist",":"+idlist);
                if (!idlist.equals("")){
                    groupbox.setChecked(true);
                }else {
                    groupbox.setChecked(false);
                }
            }
        }
        if (resultCode == 4) {
            if (requestCode == 1) {
                CheckBox manageb = findViewById(R.id.ai_manage_checkbox);
                smartlist = data.getStringExtra("switchb");
                List<String> slist;
                slist = new ArrayList<>();
                Log.d("smartlist", ":" + smartlist);
                if (smartlist != null && !smartlist.equals("0  0")) {
                    String[] arrays = smartlist.trim().split("\\s+");
                    for (String s : arrays) {
                        slist.add(s);
                    }
                }
                Log.d("測試安安", "@@" + slist);
                if (!slist.get(2).equals("0") || !slist.get(3).equals("0")) {
                    manageb.setChecked(true);
                } else {
                    manageb.setChecked(false);
                }
            }
        }
    }

    //點擊空白處隱藏鍵盤
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        //Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("ＡＩ需要開啟麥克風及九軸的權限，才能進行ＡＩ收集！請麻煩一定要開啟權限喔。");
                    builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri packageURI = Uri.parse("package:" + "com.example.user.timekeeper_testtest");
                            Intent appintent= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(appintent);
                        }
                    });
                    dialog = builder.show();
                    builder.show();

                }else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                            mainpage.BuildDev.RECORD_AUDIO);
                }
            }
        }
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("提供存取權限，才能記錄鬧鐘以及選音樂喔！");
                    builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri packageURI = Uri.parse("package:" + "com.example.user.timekeeper_testtest");
                            Intent appintent= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(appintent);
                        }
                    });
                    dialog = builder.show();
                    builder.show();
                }else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_EXTERNAL_STORAGE);
                }
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);
            }
        }
    }
}
