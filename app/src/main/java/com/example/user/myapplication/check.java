package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class check extends AppCompatActivity {
    Button sleep, wake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);

        sleep = findViewById(R.id.sleep);
        wake = findViewById(R.id.wake);

        ListView listview = findViewById(R.id.list);
        String[] str = {"AI", "NORMAL"};
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.check_item, R.id.alarm_type, str);
        listview.setAdapter(adapter);

        String[] date = {"20180928", "20180927"};
        ArrayAdapter adapter1 = new ArrayAdapter(this,
                R.layout.check_item, R.id.date, date);
        listview.setAdapter(adapter1);

        String[] time = {"0000000000000000", "00000000000000000"};
        ArrayAdapter adapter2 = new ArrayAdapter(this,
                R.layout.check_item, R.id.time, time);
        listview.setAdapter(adapter2);
    }
}
