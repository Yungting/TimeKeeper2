package com.example.user.myapplication.setting_setup;


import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.user.myapplication.Connect_To_Server;
import com.example.user.myapplication.R;
import com.example.user.myapplication.check;
import com.example.user.myapplication.get_u_sticker;
import com.example.user.myapplication.mainpage;
import com.example.user.myapplication.setting_friend;
import com.example.user.myapplication.setting_friend_search;
import com.example.user.myapplication.showPopupWindow;
import com.example.user.myapplication.upload_img;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import static com.example.user.myapplication.R.menu.mainpage_menu;
import static com.example.user.myapplication.mainpage.KEY;
import static com.example.user.myapplication.mainpage.logon;

public class setting_setup extends AppCompatActivity {

    View timekeeper_logo;

//    private Button mButton;
//    private ViewPager mViewPager;
//    private CardPagerAdapter mCardAdapter;
//    private ShadowTransformer mCardShadowTransformer;

    // hamburger
    Button menu,sign_out;
    ImageButton menu_open;
    PopupWindow popupWindow;
    TextView name, mail, pwd;
    View sticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_setup);

//        mViewPager = findViewById(R.id.viewPager);
//
//        mCardAdapter = new CardPagerAdapter();
//        mCardAdapter.addCardItem(new CardItem("hi@gmail.com","info"));
//        mCardAdapter.addCardItem(new CardItem("Location Setup", "location"));
//
//        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
//        mViewPager.setAdapter(mCardAdapter);
//        mViewPager.setOffscreenPageLimit(3);
//        mCardShadowTransformer.enableScaling(true);
        final String u_id = getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null);
        final Bitmap[] img = new Bitmap[1];
        sticker = findViewById(R.id.headshot);
        Thread get_photo = new Thread(new Runnable() {
            @Override
            public void run() {
                img[0] = get_u_sticker.get_sticker("http://140.127.218.207/uploads/"+u_id+".png");
            }
        });
        get_photo.start();
        try {
            get_photo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(img[0] !=null){
            BitmapDrawable drawable_sticker =new BitmapDrawable(img[0]);
            sticker.setBackground(drawable_sticker);
        }
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,0);
            }
        });

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        pwd = findViewById(R.id.pwd);
        JSONArray get_result;
        final Connect_To_Server u_data = new Connect_To_Server();
        Thread search_account = new Thread(new Runnable() {
            @Override
            public void run() {
                u_data.connect("select_sql", "SELECT user_id,u_password,u_name FROM `user` WHERE user_id = '" + u_id + "'");
            }
        });
        search_account.start();
        try {
            search_account.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String u_pwd = null,u_name =null;
        try {
            get_result = new JSONArray(u_data.get_data);
            int lenght = get_result.length();
            for(int i = 0;i < lenght;i++){
                JSONObject jsonObject = get_result.getJSONObject(i);
                u_pwd = jsonObject.getString("u_password");
                u_name = jsonObject.getString("u_name");
                int pwd_lenght = u_pwd.length();
                String not_pwd ="";
                for(int j = 0;j<pwd_lenght;j++){
                    not_pwd = not_pwd+"*";
                }
                name.setText(u_name);
                mail.setText(u_id);
                pwd.setText(not_pwd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        sign_out = findViewById(R.id.btn_singout);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref  = v.getContext().getSharedPreferences(KEY,MODE_PRIVATE);
                pref.edit().clear().commit();
                logon = false;
                Intent intent = new Intent(v.getContext(), mainpage.class);
                v.getContext().startActivity(intent);
            }
        });

        //回首頁
        timekeeper_logo = findViewById(R.id.timekeeper_logo);
        timekeeper_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(setting_setup.this, mainpage.class);
                startActivity(intent2);
            }
        });

        // 選單彈跳
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow==null){
                    showPopupWindow show = new showPopupWindow(setting_setup.this,getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null));
                    try {
                        show.showPopupWindow(menu);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //showPopupWindow();
                }else if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                else{
                    popupWindow.showAsDropDown(menu,-200,-155);
                }
            }
        });

    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        View view = LayoutInflater.from(this).inflate(R.layout.menu_window,null);//获取popupWindow子布局对象
        //當使用者按下確定後
        if (resultCode == RESULT_OK) {
            //取得圖檔的路徑位置
            final Uri uri = data.getData();
            //寫log
            Log.e("uri", uri.toString());
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                final String user_id = getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null);
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                Log.e("uri", bitmap.toString());
                Thread upload_thread =  new Thread(new Runnable() {
                    public void run() {
                        upload_img upload_sticker = new upload_img();
                        upload_sticker.uploadFile(user_id,getPath(uri));
                    }
                });
                upload_thread.start();
                upload_thread.join();
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        refresh();
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void refresh(){
        final String u_id = getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null);
        final Bitmap[] img = new Bitmap[1];
        sticker = findViewById(R.id.headshot);
        Thread get_photo = new Thread(new Runnable() {
            @Override
            public void run() {
                img[0] = get_u_sticker.get_sticker("http://140.127.218.207/uploads/"+u_id+".png");
            }
        });
        get_photo.start();
        try {
            get_photo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(img[0] !=null){
            BitmapDrawable drawable_sticker =new BitmapDrawable(img[0]);
            sticker.setBackground(drawable_sticker);
        }
    }
    @Override
    protected void onResume() {

        super.onResume();

    }

}

