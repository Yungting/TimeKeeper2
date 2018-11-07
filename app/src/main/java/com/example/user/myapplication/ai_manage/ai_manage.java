package com.example.user.myapplication.ai_manage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.myapplication.R;

public class ai_manage extends AppCompatActivity {
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_alarm_manage);
        mViewPager = findViewById(R.id.mviewPager);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("TYPHOONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN", R.drawable.typhoon));
        mCardAdapter.addCardItem(new CardItem("HOLIDAYYYYYYYYYYYYYYYYYYYYYYYYY", R.drawable.holiday));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);
    }
}
