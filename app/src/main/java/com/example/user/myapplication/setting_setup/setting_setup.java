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
import com.example.user.myapplication.setting_friend_search;
import com.example.user.myapplication.showPopupWindow;

import static com.example.user.myapplication.R.menu.mainpage_menu;
import static com.example.user.myapplication.mainpage.KEY;

public class setting_setup extends AppCompatActivity {

    View timekeeper_logo;

//    private Button mButton;
//    private ViewPager mViewPager;
//    private CardPagerAdapter mCardAdapter;
//    private ShadowTransformer mCardShadowTransformer;

    // hamburger
    Button menu;
    ImageButton menu_open;
    PopupWindow popupWindow;
    TextView set_up, friend, check;

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

}

