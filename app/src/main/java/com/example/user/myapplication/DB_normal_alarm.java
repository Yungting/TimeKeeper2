package com.example.user.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.Date;

public class DB_normal_alarm extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "TimeKeeper.db";
    public static final int VERSION = 1;
    private static final String TABLE_NAME = "normal_alarm";
    //public static final String KEY_ID = "_id";

    public static final String REPEAT_TXT = "repeat_txt";
    public static final String MIME_TYPE = "mime_type";
    public static final String REQUESTCODE = "requestcode";
    public static final String IFREPEAT = "ifrepeat";
    public static final String NORMAL_EDIT_TITLE = "normal_edit_title";
    public static final String ALARMTIME = "alarmtime";
    public static final String TYPE = "type";
    public static final String STATE = "state";

    private SQLiteDatabase db;

    //private static SQLiteDatabase database;
    public DB_normal_alarm(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        REPEAT_TXT + " TEXT," +
                        MIME_TYPE + " TEXT , " +
                        REQUESTCODE + " INTEGER PRIMARY KEY, " +
                        IFREPEAT + " BOOLEAN , " +
                        NORMAL_EDIT_TITLE + " TEXT," +
                        ALARMTIME+" TEXT ," +
                        TYPE +" TEXT ," + STATE + " INTEGER);";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public Cursor select()
    {
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }
    public long insert(String repeat_txt , String mime_type, int request_code, boolean ifrepeat, String normal_edit_title ,
                       String alarmtime, String type, int state) {
        //SQLiteDatabase db = this.getWritableDatabase();
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(REPEAT_TXT,repeat_txt);
        cv.put(MIME_TYPE, mime_type);
        cv.put(REQUESTCODE, request_code);
        cv.put(IFREPEAT, ifrepeat);
        cv.put(NORMAL_EDIT_TITLE, normal_edit_title);
        cv.put(ALARMTIME, alarmtime);
        cv.put(TYPE, type);
        cv.put(STATE, state);

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    public void update(int request_code,String repeat_txt ,String mime_type, boolean ifrepeat, String normal_edit_title , String alarmtime, String type, int state){
        ContentValues values = new ContentValues();
        values.put(REPEAT_TXT,repeat_txt);
        values.put(MIME_TYPE, mime_type);
        values.put(IFREPEAT, ifrepeat);
        values.put(NORMAL_EDIT_TITLE, normal_edit_title);
        values.put(ALARMTIME, alarmtime);
        values.put(TYPE, type);
        values.put(STATE, state);
        db.update(TABLE_NAME,values,"requestcode="+request_code,null);
    }
    public void delete(int requestcode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "requestcode" + " = " + Integer.toString(requestcode) ;
        db.delete(TABLE_NAME, where, null);
    }

    //刪除Table全部資料
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME );
    }
}
