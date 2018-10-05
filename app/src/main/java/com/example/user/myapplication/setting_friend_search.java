package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.setting_setup.setting_setup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.myapplication.mainpage.KEY;

public class setting_friend_search extends AppCompatActivity {

    ImageButton search_btn;
    LinearLayout friend_show;
    View timekeeper_logo;
    EditText search_friend;
    Connect_To_Server find_friend;

    // list
    RecyclerView req_list;
    MyAdapter mAdapter;


    // hamburger
    Button menu, friend_add;
    ImageButton menu_open;
    PopupWindow popupWindow;
    FrameLayout menu_window;

    TextView set_up, friend, check,friend_name;
    JSONArray get_result,get_fresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_friend_search);
        friend_show = findViewById(R.id.friend_show);
        friend_add = findViewById(R.id.friend_add_btn);
        search_friend = (EditText) findViewById(R.id.search_friend);
        search_btn = findViewById(R.id.search_btn);
        friend_name = findViewById(R.id.friend_name);
        find_friend = new Connect_To_Server();
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread search_account = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        find_friend.connect("select_sql", "SELECT user_id,u_password,u_name FROM `user` WHERE user_id = '" + search_friend.getText() + "'");
                    }
                });
                search_account.start();
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String f_id = null,f_pwd = null,f_name =null;
                try{
                    get_result = new JSONArray(find_friend.get_data);
                    int lenght = get_result.length();
                    for(int i = 0;i < lenght;i++){
                        JSONObject jsonObject = get_result.getJSONObject(i);
                        f_id = jsonObject.getString("user_id");
                        f_name = jsonObject.getString("u_name");
                    }
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }

                //final String[] token = find_friend.get_data.split("/");
                if(f_id == null){
                //if(token.length != 3){

                    new AlertDialog.Builder(setting_friend_search.this).setTitle("在試試看一次~").setMessage("沒有這位使用者喔~")
                            .setNegativeButton("OK", null)
                            .show();
                }else {
                    friend_name.setText(f_name);
                    //friend_name.setText(token[2]);
                    if(friend_show.getVisibility() != View.VISIBLE){
                        friend_show.setVisibility(View.VISIBLE);
                    }
                    final String finalF_id = f_id;
                    friend_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String u_id = getSharedPreferences(KEY, MODE_PRIVATE).getString("u_id", null);
                            String friend_id = finalF_id;
                            //查詢是否已送過交友邀請
                            Thread search_friend_invitation = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    find_friend.connect("select_sql","SELECT friend_id FROM `user_friends_invitation` WHERE user_id = '"+u_id+"'");
                                }
                            });
                            search_friend_invitation.start();
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            int exist = 0;
                            String flist_u_id = null;
                            try{
                                get_fresult = new JSONArray(find_friend.get_data);
                                int lenght = get_fresult.length();
                                for(int i = 0;i < lenght;i++){
                                    JSONObject jsonObject = get_fresult.getJSONObject(i);
                                    flist_u_id = jsonObject.getString("friend_id");
                                    if(flist_u_id.equals(friend_id)){
                                        new AlertDialog.Builder(setting_friend_search.this).setTitle("等待對方回覆邀請中").setMessage("已送過交友邀請")
                                                .setNegativeButton("OK",null)
                                                .show();
                                        exist = 1;
                                        break;
                                    }
                                }
                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                            }

                            if(exist == 0){
                                int status = 0;
                                find_friend.connect("insert_sql","INSERT INTO `user_friends_invitation` (`user_id`, `friend_id`, `status`) VALUES('" + u_id + "', '" + friend_id + "', '" + status + "')");
                                new AlertDialog.Builder(setting_friend_search.this).setTitle("好友邀請已送出").setMessage("對方接受邀請後你們就是朋友囉")
                                        .setNegativeButton("OK",null)
                                        .show();
                            }
                        }
                    });
                    ;
                }
            }
        });

        //回首頁
        timekeeper_logo = findViewById(R.id.timekeeper_logo);
        timekeeper_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(setting_friend_search.this, mainpage.class);
                startActivity(intent2);
            }
        });


        // 選單彈跳
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    showPopupWindow();
                } else if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(menu, -200, -155);
                }
            }
        });

        // recyclerview
        req_list = findViewById(R.id.req_list);
        ArrayList<String> myDataset = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            myDataset.add("HIIIII");
        }
        mAdapter = new MyAdapter(myDataset);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        req_list.setLayoutManager(layoutManager);
        req_list.setAdapter(mAdapter);

    }


    //跳出選單
    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.menu_window, null);//获取popupWindow子布局对象
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);//初始化
        popupWindow.showAsDropDown(menu, -300, -155);//在ImageView控件下方弹出

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
                Intent intent2 = new Intent(setting_friend_search.this, setting_setup.class);
                startActivity(intent2);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(setting_friend_search.this, setting_friend.class);
                startActivity(intent3);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(setting_friend_search.this, check.class);
                startActivity(intent2);
            }
        });

//        popupWindow.setAnimationStyle(R.style.popupAnim);//设置动画
    }

    //點擊空白處隱藏鍵盤
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    // RecyclerView
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = v.findViewById(R.id.friend_name);
            }
        }

        public MyAdapter(List<String> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friend_request_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(mData.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(setting_friend_search.this, "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(setting_friend_search.this, "Item " + position + " is long clicked.", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

}
