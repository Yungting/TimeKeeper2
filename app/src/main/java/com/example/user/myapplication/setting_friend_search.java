package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.user.myapplication.setting_setup.setting_setup;

public class setting_friend_search extends AppCompatActivity {

    ImageButton search_btn;
    LinearLayout friend_show;
    View timekeeper_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_friend_search);
        friend_show = findViewById(R.id.friend_show);

        search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friend_show.getVisibility() != View.VISIBLE){
                    friend_show.setVisibility(View.VISIBLE);
                }
            }
        });

        //回首頁
        timekeeper_logo = findViewById(R.id.timekeeper_logo);
        timekeeper_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(setting_friend_search.this, mainpage.class);
                startActivity(intent2);
            }
        });

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
                        Intent intent3 = new Intent(setting_friend_search.this, setting_setup.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_friends :
                        Intent intent4 = new Intent(setting_friend_search.this, setting_friend.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });
    }


}
