package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ListView;

import java.util.List;
import java.util.jar.Attributes;

public class ai_alarm_music extends Activity {
    ListView music_list;
    Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm_music);

        music_list = findViewById(R.id.music_list);
        String[] str = {"Default", "Default", "Default", "Default", "Default", "Default", "Default", "Default", "Default", "Default"};
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.music_item, R.id.music_name, str);
        music_list.setAdapter(adapter);

        music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View view2; //保存點選的View
            int select_item=-1; //一開始未選擇任何一個item所以為-1
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //點選某個item並呈現被選取的狀態
                if ((select_item == -1) || (select_item==i)){
                    view.setBackgroundColor(Color.GRAY); //為View加上選取效果
                }else{
                    view2.setBackgroundColor(Color.WHITE); //將上一次點選的View保存在view2
                    view.setBackgroundColor(Color.YELLOW); //為View加上選取效果
                }
                view2 = view; //保存點選的View
                select_item = i;//保存目前的View位置
            }

//                //點選某個item並呈現被選取的狀態
//                if ((select_item == -1) || (select_item == i)) {
//                    view.setBackgroundColor(Color.YELLOW); //為View加上選取效果
//                } else {
//                    view2.setBackground(null); //將上一次點選的View保存在view2
//                    view.setBackgroundColor(Color.YELLOW); //為View加上選取效果
//                }
//                view2 = view; //保存點選的View
//                select_item = i;//保存目前的View位置
//            }
        });

//        music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                adapter.setChecked(position);
//                adapter.notifyDataSetInvalidated();
//            }
//        });

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
