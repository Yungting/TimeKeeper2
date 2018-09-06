package com.example.user.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;

public class normal_alarm_music extends Activity {
    ListView music_list;
    TextView music_list1;
    View view2;
    String index = "Default";
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_alarm_music);

        String selection = MediaStore.Audio.Media.DURATION+">5000";
        cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        String[] str = new String[]{MediaStore.Audio.Media.TITLE , MediaStore.Audio.Media.ARTIST};
        int[] displayViews = new int[]{R.id.music_name};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.music_item, cursor, str, displayViews, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // listview
        music_list = findViewById(R.id.music_list);
        music_list1 = findViewById(R.id.music_list1);
        music_list1.setBackgroundColor(getResources().getColor(R.color.background_gray));

        music_list.setAdapter(adapter);

        music_list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.GRAY);
                view2.setBackgroundColor(Color.WHITE);
            }
        });

        music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int select_item = -1;

            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                //點選某個item並呈現被選取的狀態
                index = cursor.getString(cursor.getColumnIndex("TITLE"));
                music_list1.setBackgroundColor(Color.WHITE);
                Log.d("i", ";:" + i);
            }
        });

        Button music_apply = findViewById(R.id.music_apply);
        music_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_apply = new Intent();
                intent_apply.setClass(normal_alarm_music.this, normal_alarm.class);
                intent_apply.putExtra("index", index);
                startActivity(intent_apply);
            }
        });

//        music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            long select_item = -1;
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
//                //點選某個item並呈現被選取的狀態
//                if ((select_item == -1) || (select_item == l)) {
//                    view.setBackgroundColor(Color.GRAY);
//                    index = cursor.getString(cursor.getColumnIndex("TITLE"));
//                    music_list1.setBackgroundColor(Color.WHITE);
//                    Log.d("i",";:"+i);
//                } else {
//                    view2.setBackgroundColor(Color.WHITE); //將上一次點選的View保存在view2
//                    view.setBackgroundColor(Color.GRAY); //為View加上選取效果
//                    index = cursor.getString(cursor.getColumnIndex("TITLE"));
//                    music_list1.setBackgroundColor(Color.WHITE);
//                }
//                view2 = view; //保存點選的View
//                select_item = l; //保存目前的View位置
//            }
//        });


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
