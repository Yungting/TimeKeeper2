package com.example.user.myapplication.ai_alarm_manage;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.R;

public class ai_alarm_manage extends AppCompatActivity{

    private android.support.design.widget.TabLayout mTabs;
    private ViewPager mViewPager;
    private ai_manage_traffic traffic = new ai_manage_traffic();
    private ai_manage_holiday holiday = new ai_manage_holiday();
    Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_alarm_manage);

        //返回鍵
        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //fragment換頁
        mTabs = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.viewpager);

        mTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return traffic;
                    case 1:
                        return holiday;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }

        });

    }
}
