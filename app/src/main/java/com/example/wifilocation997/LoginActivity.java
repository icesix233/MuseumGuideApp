package com.example.wifilocation997;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wifilocation997.entity.User;
import com.example.wifilocation997.util.OKHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


//    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
//
//    private static final int GET =1;
//    private static final int POST =2;


    private Button btn_login;
    private EditText et_username;
    private EditText et_password;
    private Button btn_reg;
    private String username;
    private String password;
    private String baseUrl="http://192.168.xxx.1:8081";

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case GET:
//                    //获取数据
//                    Log.d("PAN", String.valueOf(msg.obj));
//                    break;
//                case POST:
//                    //获取数据
//                    Log.d("PAN", String.valueOf(msg.obj));
//                    break;
//            }
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                //TODO：判断用户名和密码
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                User user=new User();
                Gson gson=new Gson();
                String json=gson.toJson(user);
                String args[]=new String[]{"user","login"};
                String res= OKHttpUtil.postSyncRequest(baseUrl,json,args);
                Log.d("同步:",res);


                //提示用户登陆成功
                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_reg:
                Intent intent2 = new Intent(this,RegisterActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
    //post同步请求（json对象）
    private void okhttpData(){
        Log.i("TAG","--ok-");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                //使用JSONObject封装参数
                MediaType mediaType  = MediaType.parse("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("user_name",username);
                    json.put("password",password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //创建RequestBody对象，将参数按照指定的MediaType封装
                RequestBody requestBody = RequestBody.create(mediaType,json.toString());

                Request request = new Request
                        .Builder()
                        .post(requestBody)//Post请求的参数传递
                        .url("https://h5556095v9.zicp.fun/user/login")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.d("PAN",result);
                    response.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


//
//    /**
//     * 使用get请求网络数据
//     */
//    private void getDataFromGet(){
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    String result = get("119.8.37.175:8888");
//                    Log.d("PAN",result);
//                    Message msg = Message.obtain();
//                    msg.what = GET;
//                    msg.obj = result;
//                    handler.sendMessage(msg);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    /**
//     * 使用post请求网络数据
//     */
//    private void getDataFromPost(){
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    String result = post("119.8.37.175:8888","");
//                    Log.d("PAN",result);
//                    Message msg = Message.obtain();
//                    msg.what = POST;
//                    msg.obj = result;
//                    handler.sendMessage(msg);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    /**
//     * get请求
//     * @param url
//     * @return
//     * @throws IOException
//     */
//    private String get(String url) throws IOException{
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Response response = okHttpClient.newCall(request).execute();
//        return  response.body().string();
//    }
//
//    /**
//     * post请求
//     * @param url
//     * @param json
//     * @return
//     * @throws IOException
//     */
//    private String post(String url,String json) throws IOException {
//        RequestBody body = RequestBody.create(JSON,json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        Response response = okHttpClient.newCall(request).execute();
//        return response.body().string();
//    }

}