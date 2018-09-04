package com.example.user.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class normal_alarm_music extends Activity {

    private RecyclerView nrecyclerView;
    private RecyclerView.Adapter nadapter;
    private RecyclerView.LayoutManager nlayoutManager;
    Button go_back;
    ArrayList<normal_music_item> itemlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.normal_alarm_music);

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
        String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
        Integer id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

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

        //返回上一頁
        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 6) {
            Uri i = data.getData();  // getData
            String s = i.getPath(); // getPath
            File k = new File(s);  // set File from path
            if (s != null) {      // file.exists

                String path = MediaStore.MediaColumns.DATA;
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
                values.put(MediaStore.MediaColumns.TITLE, "ring");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
                values.put(MediaStore.MediaColumns.SIZE, k.length());
                values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
                values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                values.put(MediaStore.Audio.Media.IS_ALARM, true);
                values.put(MediaStore.Audio.Media.IS_MUSIC, false);

                Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
                        .getAbsolutePath());
                getContentResolver().delete(
                        uri,
                        MediaStore.MediaColumns.DATA + "=\""
                                + k.getAbsolutePath() + "\"", null);
                Uri newUri = getContentResolver().insert(uri, values);

                try {
                    RingtoneManager.setActualDefaultRingtoneUri(
                            this, RingtoneManager.TYPE_RINGTONE,
                            newUri);
                } catch (Throwable t) {

                }
            }
        }
    }
}
