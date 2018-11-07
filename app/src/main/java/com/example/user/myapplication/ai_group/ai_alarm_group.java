package com.example.user.myapplication.ai_group;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.user.myapplication.Connect_To_Server;
import com.example.user.myapplication.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ai_alarm_group extends Activity {
    private RecyclerView mRecyclerView;
    private ai_Adapter mMyAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm_group);

        //RecyclerView三部曲+LayoutManager
        mRecyclerView = findViewById(R.id.group_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initData();
        mMyAdapter = new ai_Adapter(mList);
        mRecyclerView.setAdapter(mMyAdapter);

        //为RecyclerView添加HeaderView和FooterView
        setFooterView(mRecyclerView);


    }

    //      資料產生
    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            mList.add("item" + i);
        }

    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, view, false);
        mMyAdapter.setFooterView(footer);
    }

}
