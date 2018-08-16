package com.example.user.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.ArrayList;

public class normal_alarm_music extends Activity {

    private RecyclerView nrecyclerView;
    private RecyclerView.Adapter nadapter;
    private RecyclerView.LayoutManager nlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.normal_alarm_music);

        ArrayList<normal_music_item> itemlist = new ArrayList<>();
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));
        itemlist.add(new normal_music_item("Default"));

        nrecyclerView = findViewById(R.id.music_recyclerview);
        nrecyclerView.setHasFixedSize(true);
        nlayoutManager = new LinearLayoutManager(this);
        nadapter = new normal_music_adapter(itemlist);

        nrecyclerView.setLayoutManager(nlayoutManager);
        nrecyclerView.setAdapter(nadapter);
    }
}
