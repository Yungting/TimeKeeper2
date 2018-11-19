package com.example.user.myapplication.ai_manage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Response;

public class Holiday {
    Boolean isbreak = false;

    public boolean isholiday(){
        HttpUtil.sendRequestWithOkhttp("http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000077-002", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String jobject = "";
                        try {
                            jobject = response.body().string();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(jobject);
                            JSONObject result = jsonObject.getJSONObject("result");
                            JSONArray array = result.getJSONArray("records");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                String today = year +"/"+month+"/"+day;
                                Log.d("today",":"+today);
                                if (obj.getString("date").equals(today) && !obj.getString("holidayCategory").equals("星期六、星期日")){
                                    if (obj.getString("isHoliday").equals("是")){
                                        isbreak = true;
                                        break;
                                    }
                                }
                            }
                            Log.d("break",":"+isbreak);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        return isbreak;
    }
}
