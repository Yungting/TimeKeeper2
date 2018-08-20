package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import cdflynn.android.library.crossview.CrossView;

public class mainpage extends Activity{

    ImageButton add_btn, normal_btn, ai_btn, counter_btn;
    LinearLayout normal_layout, ai_layout, counter_layout;
    CrossView crossView;
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
                if(normal_layout.getVisibility() == View.VISIBLE && ai_layout.getVisibility() == View.VISIBLE && counter_layout.getVisibility() == View.VISIBLE){
                    normal_layout.setVisibility(View.GONE);
                    ai_layout.setVisibility(View.GONE);
                    counter_layout.setVisibility(View.GONE);
                }else{
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
    }

    //跳出選單
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.mainpage_menu, popup.getMenu());
        popup.show();
    }
}
