package com.example.user.myapplication;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class setting_friend_search extends AppCompatActivity {

    EditText search_friend;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_friend_search);
        search_friend = findViewById(R.id.search_friend);
        button = findViewById(R.id.button);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(new float[]{0,0,50,50,50,50,0,0});
        button.setBackground(drawable);
    }
}
