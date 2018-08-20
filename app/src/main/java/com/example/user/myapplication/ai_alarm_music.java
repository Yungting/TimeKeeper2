package com.example.user.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ai_alarm_music extends Activity {
    ListView music_list;
    Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm_music);

        music_list = findViewById(R.id.music_list);
        String[] str = {"Default","Default","Default","Default","Default","Default","Default","Default","Default","Default"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.music_item, R.id.music_name, str);
        music_list.setAdapter(adapter);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
