package com.example.user.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.user.myapplication.mainpage.KEY;

public class ai_alarm_group extends Activity {
    ListView ai_gruop;
    Connect_To_Server find_friends;
    JSONArray get_fresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ai_alarm_group);

        ai_gruop = findViewById(R.id.group_list);


        find_friends = new Connect_To_Server();
        ArrayList friend_list = new ArrayList();
        ArrayList friendID_list = new ArrayList();
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
        final String u_id = getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null);
        Thread search_friend = new Thread(new Runnable() {
            @Override
            public void run() {
                find_friends.connect("select_sql","SELECT user.u_name,user.user_id FROM `user` WHERE user.user_id = any(SELECT user_friends.friend_id FROM `user_friends` WHERE user_friends.user_id =  '"+u_id +"')");
            }
        });
        search_friend.start();
        try {
            search_friend.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            get_fresult = new JSONArray(find_friends.get_data);
            int lenght = get_fresult.length();
            for(int i = 0;i < lenght;i++) {
                JSONObject jsonObject = get_fresult.getJSONObject(i);
                friend_list.add(jsonObject.getString("u_name"));
                friendID_list.add(jsonObject.getString("user_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<=friendID_list.size();i++){
            final String f_id = friendID_list.get(i).toString();
            final Bitmap[] img = new Bitmap[1];
            Thread get_photo = new Thread(new Runnable() {
                @Override
                public void run() {
                    img[0] = get_u_sticker.get_sticker("http://140.127.218.207/uploads/"+f_id+".png");
                }
            });
            get_photo.start();
            try {
                get_photo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(img[0] !=null){
                bitmapArray.add(img[0]);
            }else {
                bitmapArray.add(null);
            }
        }




        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.ai_group_item, R.id.name, friend_list);
        

    }
}
