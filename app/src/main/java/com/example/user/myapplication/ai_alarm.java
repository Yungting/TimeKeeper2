package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ai_alarm extends Activity {

    TextView group;
    LinearLayout rington;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm);

        group = findViewById(R.id.group);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_group.class);
                startActivity(intent1);
            }
        });

        rington = findViewById(R.id.rington);
        rington.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ai_alarm.this, ai_alarm_music.class);
                startActivity(intent1);
            }
        });
    }
}
