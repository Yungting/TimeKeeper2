package com.example.user.myapplication.ai_manage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.user.myapplication.R;
import com.example.user.myapplication.ai_alarm;

import java.util.ArrayList;
import java.util.List;

public class ai_manage extends AppCompatActivity {
    private ViewPager mViewPager;
    private Switch switch1;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    String smartlist;
    List<String> slist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_alarm_manage);
        mViewPager = findViewById(R.id.mviewPager);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("若您所工作的縣市\n" +
                "發布颱風天停班停課\n" +
                "系統將自動幫您關閉當日鬧鐘\n" +
                "讓您享有一個美好的早晨 : )\n", R.drawable.typhoon));
//        switch1 = mCardAdapter.getCardViewAt(0).findViewById(R.id.switch1);
//        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    switch1.getThumbDrawable().setColorFilter(Color.rgb( 85, 145, 198), PorterDuff.Mode.MULTIPLY);
//                    switch1.getTrackDrawable().setColorFilter(Color.argb( 100,255, 255, 255), PorterDuff.Mode.MULTIPLY);
//                }else {
//                    switch1.getThumbDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//                    switch1.getTrackDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
//                }
//            }
//        });


        mCardAdapter.addCardItem(new CardItem("若該日為國定假日為\n" +
                "(係指勞基法第37條之規定)\n" +
                "系統將自動幫您關閉當日鬧鐘\n" +
                "讓您享有一個美好的早晨 : )\n", R.drawable.holiday));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);

        Intent intent1 = getIntent();
        slist = new ArrayList<>();
        if (intent1 != null){
            smartlist = intent1.getStringExtra("sbar");
            if (smartlist != null && !smartlist.equals("0  0")){
                String[] arrays = smartlist.trim().split("\\s+");
                for(String s : arrays){
                    slist.add(s);
                }
            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int[] swithb = mCardShadowTransformer.switchb;
            String sbar = "";
            for (int i = 0; i < 2 ; i++){
                sbar = sbar + swithb[i] + "  ";
            }
            Intent intent = new Intent(ai_manage.this, ai_alarm.class);
            intent.putExtra("switchb", sbar);
            setResult(4, intent);
            Log.d("ss",":"+sbar);
            finish();
        }
        return false;
    }

    public void setcheck(String a, String b){
        final Switch switch1 = mCardAdapter.getCardViewAt(0).findViewById(R.id.switch1);
        final Switch switch2 = mCardAdapter.getCardViewAt(1).findViewById(R.id.switch1);
        if (a.equals("1")){
            switch1.setChecked(true);
        }else { switch1.setChecked(false); }
        if (b.equals("1")){
            switch2.setChecked(true);
        }else { switch2.setChecked(false); }
    }
}
