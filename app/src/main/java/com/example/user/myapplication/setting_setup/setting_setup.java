package com.example.user.myapplication.setting_setup;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


import com.example.user.myapplication.R;
import com.example.user.myapplication.check;
import com.example.user.myapplication.mainpage;
import com.example.user.myapplication.setting_friend;

import static com.example.user.myapplication.R.menu.mainpage_menu;

public class setting_setup extends AppCompatActivity {

    View timekeeper_logo;

    private Button mButton;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    // hamburger
    Button menu;
    ImageButton menu_open;
    PopupWindow popupWindow;
    TextView set_up, friend, check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_setup);

        mViewPager = findViewById(R.id.viewPager);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("hi@gmail.com","info"));
        mCardAdapter.addCardItem(new CardItem("Location Setup", "location"));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);

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
                    showPopupWindow();
                }else if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                else{
                    popupWindow.showAsDropDown(menu,-200,-155);
                }
            }
        });

    }

    //跳出選單
    //跳出選單
    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.menu_window,null);//获取popupWindow子布局对象
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,false);//初始化
        popupWindow.showAsDropDown(menu,-300,-155);//在ImageView控件下方弹出

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
                Intent intent2 = new Intent(setting_setup.this, setting_setup.class);
                startActivity(intent2);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(setting_setup.this, setting_friend.class);
                startActivity(intent3);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(setting_setup.this, com.example.user.myapplication.check.class);
                startActivity(intent2);
            }
        });

//        popupWindow.setAnimationStyle(R.style.popupAnim);//设置动画
    }

}

