package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;

import com.example.user.myapplication.setting_setup.setting_setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class setting_friend extends AppCompatActivity {
    Button add_friend_btn;
    View timekeeper_logo;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_friend);

        //設定friend的照片與名稱
        int[] image = {R.drawable.ai_open};
        String[] str = {"Tina", "AAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAa","B","C","D","B","C","D","B","C","D"};

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[0]);
            item.put("text", str[i]);
            items.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.setting_friend_item, new String[]{"image", "text"},
                new int[]{R.id.friend_photo, R.id.friend_name});
        gridView = findViewById(R.id.friend_list);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
            }
        });

        //點選 ADD FRIEND 按鈕
        add_friend_btn = findViewById(R.id.add_friend_btn);
        add_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(setting_friend.this, setting_friend_showqrcode.class);
                startActivity(intent1);
            }
        });

        //回首頁
        timekeeper_logo = findViewById(R.id.timekeeper_logo);
        timekeeper_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(setting_friend.this, mainpage.class);
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
                        Intent intent3 = new Intent(setting_friend.this, setting_setup.class);
                        startActivity(intent3);
                        return true;

                    case R.id.action_friends :
                        Intent intent4 = new Intent(setting_friend.this, setting_friend.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });
    }
}
