package com.example.user.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.user.myapplication.setting_setup.setting_setup;

import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

public class showPopupWindow extends Activity{
    Context context;
    ImageButton menu_open;
    ImageView photo_sticker;
    PopupWindow popupWindow;
    FrameLayout menu_window;
    TextView set_up, friend, check;
    Bitmap img;
    public showPopupWindow(Context context){
        this.context = context;
    }
    public showPopupWindow(Context context,Bitmap img){
        this.context = context;
        this.img = img;
    }

    public void showPopupWindow(Button menu) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_window,null);//获取popupWindow子布局对象
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,false);//初始化
        popupWindow.showAsDropDown(menu,-300,-155);//在ImageView控件下方弹出

        photo_sticker = view.findViewById(R.id.photo_sticker);
        if(img != null){
            photo_sticker.setImageBitmap(img);
        }

        photo_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                ((Activity) context).startActivityForResult(intent,0);
            }
        });



        menu_open = view.findViewById(R.id.menu_btn_open);
        menu_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        set_up = view.findViewById(R.id.set_up);
        friend = view.findViewById(R.id.friend);
        check = view.findViewById(R.id.check);

        set_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context, setting_setup.class);
                context.startActivity(intent2);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(context, setting_friend.class);
                context.startActivity(intent3);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context, check.class);
                context.startActivity(intent2);
            }
        });

    }
}
