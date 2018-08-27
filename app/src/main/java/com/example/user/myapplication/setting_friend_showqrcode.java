package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class setting_friend_showqrcode extends AppCompatActivity {

    Button qrcode_scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_friend_showqrcode);

        qrcode_scanner = findViewById(R.id.qrcode_scanner);
        qrcode_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(setting_friend_showqrcode.this, setting_friend_search.class);
                startActivity(intent1);
            }
        });
    }
}
