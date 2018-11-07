package com.example.user.myapplication.setting_setup;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
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
import java.util.Calendar;

import static com.example.user.myapplication.R.menu.mainpage_menu;
import static com.example.user.myapplication.hideinput.hideSoftInput;
import static com.example.user.myapplication.hideinput.isShouldHideInput;
import static com.example.user.myapplication.mainpage.KEY;
import static com.example.user.myapplication.mainpage.logon;

public class setting_setup extends AppCompatActivity {

    View timekeeper_logo;

//    private Button mButton;
//    private ViewPager mViewPager;
//    private CardPagerAdapter mCardAdapter;
//    private ShadowTransformer mCardShadowTransformer;

    // hamburger
    Button menu, sign_out, edit_btn, save_btn, date_btn;
    ImageButton menu_open;
    PopupWindow popupWindow;
    TextView name, mail, pwd, gender, birth, job;
    EditText name_edit, pwd_edit, birthday_edit;
    LinearLayout show_layout, edit_layout, show_btn_layout, edit_btn_layout;
    View sticker;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_setup);

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        pwd = findViewById(R.id.pwd);
        gender = findViewById(R.id.gender);
        birth = findViewById(R.id.birthday);
        job = findViewById(R.id.career);
        edit_btn = findViewById(R.id.btn_edit);
        save_btn = findViewById(R.id.btn_save);
        name_edit = findViewById(R.id.name_edit);
        birthday_edit = findViewById(R.id.birthday_edit);
        date_btn = findViewById(R.id.date_btn);
        show_layout = findViewById(R.id.show_layout);
        edit_layout = findViewById(R.id.edit_layout);
        show_btn_layout = findViewById(R.id.show_btn_layout);
        edit_btn_layout = findViewById(R.id.edit_btn_latout);

        //下拉選單
        spinner = findViewById(R.id.career_edit);
        ArrayAdapter<CharSequence> careerList = ArrayAdapter.createFromResource(setting_setup.this, R.array.career,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(careerList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //點擊後的動作
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //生日的輸入框動作
        birthday_edit.setInputType(InputType.TYPE_NULL); //不显示系统输入键盘
        birthday_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //避免點擊2下才打開
        birthday_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) showDatePickerDialog();
            }
        });

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //切換edit或show模式
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_layout.setVisibility(View.GONE);
                edit_layout.setVisibility(View.VISIBLE);
                show_btn_layout.setVisibility(View.GONE);
                edit_btn_layout.setVisibility(View.VISIBLE);
                name.setVisibility(View.GONE);
                name_edit.setVisibility(View.VISIBLE);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_layout.setVisibility(View.VISIBLE);
                edit_layout.setVisibility(View.GONE);
                show_btn_layout.setVisibility(View.VISIBLE);
                edit_btn_layout.setVisibility(View.GONE);
                name.setVisibility(View.VISIBLE);
                name_edit.setVisibility(View.GONE);
            }
        });


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
                img[0] = get_u_sticker.get_sticker("http://140.127.218.207/uploads/" + u_id + ".png");
            }
        });
        get_photo.start();
        try {
            get_photo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (img[0] != null) {
            BitmapDrawable drawable_sticker = new BitmapDrawable(img[0]);
            sticker.setBackground(drawable_sticker);
        }
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 0);
            }
        });


        JSONArray get_result;
        final Connect_To_Server u_data = new Connect_To_Server();
        Thread search_account = new Thread(new Runnable() {
            @Override
            public void run() {
                u_data.connect("select_sql", "SELECT * FROM `user` WHERE user_id = '" + u_id + "'");
            }
        });
        search_account.start();
        try {
            search_account.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String u_pwd = null, u_name = null, u_gender = null, u_birth = null, u_job = null;
        try {
            get_result = new JSONArray(u_data.get_data);
            int lenght = get_result.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject jsonObject = get_result.getJSONObject(i);
                u_pwd = jsonObject.getString("u_password");
                u_name = jsonObject.getString("u_name");
                u_gender = jsonObject.getString("u_gender");
                u_birth = jsonObject.getString("u_birth");
                u_job = jsonObject.getString("u_job");
                int pwd_lenght = u_pwd.length();
                String not_pwd = "";
                for (int j = 0; j < pwd_lenght; j++) {
                    not_pwd = not_pwd + "*";
                }
                name.setText(u_name);
                mail.setText(u_id);
                pwd.setText(not_pwd);
                gender.setText(u_gender);
                birth.setText(u_birth);
                job.setText(u_job);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //登出
        sign_out = findViewById(R.id.btn_singout);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = v.getContext().getSharedPreferences(KEY, MODE_PRIVATE);
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
                if (popupWindow == null) {
                    showPopupWindow show = new showPopupWindow(setting_setup.this, getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null));
                    try {
                        show.showPopupWindow(menu);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //showPopupWindow();
                } else if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(menu, -200, -155);
                }
            }
        });

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        View view = LayoutInflater.from(this).inflate(R.layout.menu_window, null);//获取popupWindow子布局对象
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
                Thread upload_thread = new Thread(new Runnable() {
                    public void run() {
                        upload_img upload_sticker = new upload_img();
                        upload_sticker.uploadFile(user_id, getPath(uri));
                    }
                });
                upload_thread.start();
                upload_thread.join();
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        refresh();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refresh() {
        final String u_id = getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null);
        final Bitmap[] img = new Bitmap[1];
        sticker = findViewById(R.id.headshot);
        Thread get_photo = new Thread(new Runnable() {
            @Override
            public void run() {
                img[0] = get_u_sticker.get_sticker("http://140.127.218.207/uploads/" + u_id + ".png");
            }
        });
        get_photo.start();
        try {
            get_photo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (img[0] != null) {
            BitmapDrawable drawable_sticker = new BitmapDrawable(img[0]);
            sticker.setBackground(drawable_sticker);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    //跳出DatePicker選擇生日日期
    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(setting_setup.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                birthday_edit.setText(" " + year + " / " + (monthOfYear + 1) + " / " + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    //點擊空白處隱藏鍵盤
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken(),this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}


