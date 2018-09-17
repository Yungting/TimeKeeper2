package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.myapplication.setting_setup.setting_setup;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cdflynn.android.library.crossview.CrossView;

public class mainpage extends Activity implements RecyclerTouchListener.RecyclerTouchListenerHelper{

    ImageButton add_btn, normal_btn, ai_btn, counter_btn;
    LinearLayout normal_layout, ai_layout, counter_layout;
    CrossView crossView;

    //recyclerview
    RecyclerView mRecyclerView;
    MainAdapter mAdapter;
    String[] dialogItems;
    List<Integer> unclickableRows, unswipeableRows;
    private RecyclerTouchListener onTouchListener;
    private int openOptionsPosition;
    private OnActivityTouchListener touchListener;
    int[] requestcode = new int[50];
    String[] alarmtype = new String[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainpage);

        add_btn = findViewById(R.id.add_btn);
        ai_btn = findViewById(R.id.ai_btn);
        ai_layout = findViewById(R.id.ai_layout);
        normal_btn = findViewById(R.id.normal_btn);
        normal_layout = findViewById(R.id.normal_layout);
        counter_btn = findViewById(R.id.counter_btn);
        counter_layout = findViewById(R.id.counter_layout);
        crossView = findViewById(R.id.cross_view);

        //設定增加的子按鈕顯示或隱藏
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crossView.toggle();
                if (normal_layout.getVisibility() == View.VISIBLE && ai_layout.getVisibility() == View.VISIBLE && counter_layout.getVisibility() == View.VISIBLE) {
                    normal_layout.setVisibility(View.GONE);
                    ai_layout.setVisibility(View.GONE);
                    counter_layout.setVisibility(View.GONE);
                } else {
                    normal_layout.setVisibility(View.VISIBLE);
                    ai_layout.setVisibility(View.VISIBLE);
                    counter_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        normal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mainpage.this, normal_alarm.class);
                startActivity(intent1);
            }
        });

        ai_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mainpage.this, ai_alarm.class);
                startActivity(intent1);
            }
        });

        //recyclerview
        unclickableRows = new ArrayList<>();
        unswipeableRows = new ArrayList<>();
        dialogItems = new String[25];
        for (int i = 0; i < 25; i++) {
            dialogItems[i] = String.valueOf(i + 1);
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MainAdapter(this, getData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Intent intent;
                        if (alarmtype[position].equals("normal")){
                            intent = new Intent(mainpage.this, normal_alarm.class);
                        }else {intent = new Intent(mainpage.this, ai_alarm.class);}
                        intent.putExtra("requestcode", requestcode[position]);
                        startActivity(intent);
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
//                        ToastUtil.makeToast(getApplicationContext(), "Button in row " + (position + 1) + " clicked!");
                    }
                })
                .setLongClickable(true, new RecyclerTouchListener.OnRowLongClickListener() {
                    @Override
                    public void onRowLongClicked(int position) {
//                        ToastUtil.makeToast(getApplicationContext(), "Row " + (position + 1) + " long clicked!");
                    }
                })
                .setSwipeOptionViews(R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        String message = "";
                        if (viewID == R.id.delete) {
                            message += "Change";
                        }
                    }
                });
    }

    //recyclerview
    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }

    private List<mainpage_RowModel> getData() {
        List<mainpage_RowModel> list = new ArrayList<>(25);
        DB_normal_alarm db = new DB_normal_alarm(this);

        //cursor
        if (db!= null){
            Cursor cursor = db.select();
            if (cursor.getCount()>0){
                int i = 0;
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                    Calendar cal = Calendar.getInstance();
                    Long t = Long.parseLong(cursor.getString(6));
                    cal.setTimeInMillis(t);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    int min = cal.get(Calendar.MINUTE);
                    String time = "";
                    if (hour < 10 && min < 10){
                        time = "0"+hour+":0"+min;
                    }else if (hour < 10 && min>=10){
                        time = "0"+hour+":"+min;
                    }else if (hour >= 10 && min<10){
                        time = hour+":0"+min;
                    }else if (hour >= 10 && min >= 10){time = hour+":"+min;}
                    list.add(new mainpage_RowModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), Boolean.parseBoolean(cursor.getString(4))
                            ,cursor.getString(5), time, cursor.getString(7), cursor.getInt(8)));
                    requestcode[i] = cursor.getInt(3);
                    alarmtype[i] = cursor.getString(7);
                    i++;
                }
            }
        }
        return list;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) touchListener.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
        LayoutInflater inflater;
        List<mainpage_RowModel> modelList;

        public MainAdapter(Context context, List<mainpage_RowModel> list) {
            inflater = LayoutInflater.from(context);
            modelList = new ArrayList<>(list);
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.mainpage_alarm_row, parent, false);
            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MainViewHolder holder, final int position) {
            holder.bindData(modelList.get(position));

            //點擊刪除layout後的動作
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeData(position);
                }
            });


        }

        //刪除鬧鐘
        public void removeData(int position) {
            modelList.remove(position);
            //删除动画
            notifyItemRemoved(position);
            notifyDataSetChanged();
            DB_normal_alarm db = new DB_normal_alarm(mainpage.this);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(mainpage.this, ai_alarmalert.class);
            PendingIntent pi = PendingIntent.getActivity(mainpage.this, requestcode[position], intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pi);
            db.delete(requestcode[position]);
            db.close();
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView mainText, repeatday, alarm_time;
            Button alarm_btn;
            LinearLayout alarm, rowFG;
            RelativeLayout delete;
            int state, requestcode;
            String type;

            public MainViewHolder(View itemView) {
                super(itemView);
                mainText = itemView.findViewById(R.id.mainText);
                repeatday = itemView.findViewById(R.id.repeatday);
                alarm_time = itemView.findViewById(R.id.alarm_time);
                delete = itemView.findViewById(R.id.delete);
                rowFG = itemView.findViewById(R.id.rowFG);

                // 開啟/關閉鬧鐘
                alarm = itemView.findViewById(R.id.alarm);
                alarm_btn = itemView.findViewById(R.id.rowButton);
                alarm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DB_normal_alarm db = new DB_normal_alarm(mainpage.this);
                        if( type.equals("ai")){
                            switch (state) {
                                case 1:
                                    alarm_btn.setBackground(getResources().getDrawable(R.drawable.ai_close));
                                    alarm.setBackground(getResources().getDrawable(R.drawable.mainpage_alarm_background_close));
                                    state = 0;
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent(mainpage.this, ai_alarmalert.class);
                                    PendingIntent pi = PendingIntent.getActivity(mainpage.this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    alarmManager.cancel(pi);
                                    break;

                                case 0:
                                    alarm_btn.setBackground(getResources().getDrawable(R.drawable.ai_open));
                                    alarm.setBackground(getResources().getDrawable(R.drawable.mainpage_alarm_background));
                                    state = 1;
                                    AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent1 = new Intent(mainpage.this, ai_alarmalert.class);
                                    PendingIntent pi1 = PendingIntent.getActivity(mainpage.this, requestcode, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                                    break;
                            }
                        }else {
                            switch (state) {
                                case 1:
                                    alarm.setBackground(getResources().getDrawable(R.drawable.mainpage_alarm_background_close));
                                    state = 0;
                                    db.updatestate(requestcode, state);
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent = new Intent(mainpage.this, ai_alarmalert.class);
                                    PendingIntent pi = PendingIntent.getActivity(mainpage.this, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    alarmManager.cancel(pi);
                                    break;

                                case 0:
                                    alarm.setBackground(getResources().getDrawable(R.drawable.mainpage_alarm_background));
                                    state = 1;
                                    db.updatestate(requestcode, state);
                                    Cursor cursor = db.selectbycode(requestcode);
                                    if (cursor != null && cursor.moveToFirst()){
                                        Boolean ifrepeat = Boolean.parseBoolean(cursor.getString(4));
                                        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                        Intent intent1 = new Intent(mainpage.this, ai_alarmalert.class);
                                        intent1.putExtra("requestcode", requestcode);
                                        PendingIntent pi1 = PendingIntent.getActivity(mainpage.this, requestcode, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                                        if (ifrepeat){
                                            Log.d("case",":repear");
                                            alarm.setRepeating(AlarmManager.RTC_WAKEUP, Long.parseLong(cursor.getString(6)), 24*60*60*1000, pi1);
                                        }else {
                                            if (System.currentTimeMillis() > Long.parseLong(cursor.getString(6))){
                                                Log.d("case",":settmr");
                                                alarm.set(AlarmManager.RTC_WAKEUP, Long.parseLong(cursor.getString(6))+24*60*60*1000, pi1);
                                            }else{
                                                Log.d("case",":settoday");
                                                alarm.set(AlarmManager.RTC_WAKEUP, Long.parseLong(cursor.getString(6)), pi1);
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                });
            }

            public void bindData(mainpage_RowModel rowModel) {
                mainText.setText(rowModel.getNormal_edit_title());
                repeatday.setText(rowModel.getRepeat());
                alarm_time.setText(rowModel.getAlarm_time());
                type = rowModel.getType();
                state = rowModel.getState();
                requestcode = rowModel.getRequestcode();

                if (type.equals("normal")){
                    alarm_btn.setBackground(getResources().getDrawable(R.drawable.normal));
                } else if (type.equals("ai")) {
                    alarm_btn.setBackground(getResources().getDrawable(R.drawable.ai_open));
                }
                if (state == 0){
                    alarm.setBackground(getResources().getDrawable(R.drawable.mainpage_alarm_background_close));
                }else{
                    alarm.setBackground(getResources().getDrawable(R.drawable.mainpage_alarm_background));
                }
            }
        }
    }

    //跳出選單
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.mainpage_menu, popup.getMenu());
        popup.show();

        //點擊選單選項，然後換頁
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_setup :
                        Intent intent3 = new Intent(mainpage.this, setting_setup.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_friends :
                        Intent intent4 = new Intent(mainpage.this, setting_friend.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });
    }
}