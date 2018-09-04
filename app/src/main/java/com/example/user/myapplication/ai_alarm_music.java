package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.File;

public class ai_alarm_music extends Activity {
    ListView music_list;
    Button go_back;
    Cursor cursor;
    public static int STATE_SELECT_ALBUM = 0;
    public static int STATE_SELECT_SONG = 1;

    int currentState = STATE_SELECT_ALBUM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm_music);

        String selection = MediaStore.Audio.Media.DURATION+">5000";

        cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        music_list = findViewById(R.id.music_list);
        String[] str = new String[]{MediaStore.Audio.Media.TITLE , MediaStore.Audio.Media.ARTIST};
        int[] displayViews = new int[]{R.id.music_name};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.music_item, cursor, str, displayViews, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        music_list.setAdapter(adapter);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (currentState == STATE_SELECT_ALBUM) {
//传入在列表中选定唱片集的位置，同时Cursor对象利用该位置，通过moveToPosition获知是哪个唱片集
            if (cursor.moveToPosition(position)) {

                String[] columns = {MediaStore.Audio.Media.DATA,//音频文件的实际路径
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.MIME_TYPE,
                };

                String where = android.provider.MediaStore.Audio.Media.ALBUM
                        + "=?";
                //字符数组，其中每个字符串对应一个使用的“?”符号
                String whereVal[] = {cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Albums.ALBUM))};

                String orderBy = android.provider.MediaStore.Audio.Media.TITLE;
                //查询只属于特定唱片集的媒体文件
                cursor = getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
                        where, whereVal, orderBy);

                String[] displayFields = new String[]{MediaStore.Audio.Media.DISPLAY_NAME};
                int[] displayViews = new int[]{android.R.id.text1};
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.music_item, cursor, displayFields, displayViews, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                music_list.setAdapter(adapter);

                currentState = STATE_SELECT_SONG;
            }
        } else if (currentState == STATE_SELECT_SONG) {

            if (cursor.moveToPosition(position)) {

                int fileColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA);
                int mimeTypeColumn = cursor
                        .getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

                String audioFilePath = cursor.getString(fileColumn);
                String mimeType = cursor.getString(mimeTypeColumn);

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                File newFile = new File(audioFilePath);
                intent.setDataAndType(Uri.fromFile(newFile), mimeType);

                startActivity(intent);
            }
        }

    }
}
