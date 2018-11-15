package com.example.user.myapplication.ai_manage;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.user.myapplication.R;
import com.example.user.myapplication.setting_setup;

public class ai_manage extends AppCompatActivity {
    private ViewPager mViewPager;
    private Switch switch1;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    Spinner spinner_home, spinner_work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_alarm_manage);
        mViewPager = findViewById(R.id.mviewPager);


        //下拉選單
        spinner_home = findViewById(R.id.home_address);
        ArrayAdapter<CharSequence> careerList1 = ArrayAdapter.createFromResource(ai_manage.this, R.array.address,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_home.setAdapter(careerList1);

        spinner_work = findViewById(R.id.work_address);
        ArrayAdapter<CharSequence> careerList2 = ArrayAdapter.createFromResource(ai_manage.this, R.array.address,
                android.R.layout.simple_spinner_dropdown_item);
        spinner_work.setAdapter(careerList2);


        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("若您所工作的縣市\n" +
                "發布颱風天停班停課\n" +
                "系統將自動幫您關閉當日鬧鐘\n" +
                "讓您享有一個美好的早晨 : )\n", R.drawable.typhoon));

        mCardAdapter.addCardItem(new CardItem("若該日為國定假日為\n" +
                "(係指勞基法第37條之規定)\n" +
                "系統將自動幫您關閉當日鬧鐘\n" +
                "讓您享有一個美好的早晨 : )\n", R.drawable.holiday));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);
    }
}
