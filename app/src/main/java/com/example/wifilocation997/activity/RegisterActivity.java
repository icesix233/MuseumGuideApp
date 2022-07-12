package com.example.wifilocation997.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wifilocation997.Constant.Constant;
import com.example.wifilocation997.R;
import com.example.wifilocation997.entity.User;
import com.example.wifilocation997.util.OKHttpUtil;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_reg_confirm;
    private EditText et_reg_username;
    private EditText et_reg_password;
    private EditText et_reg_confirmpwd;
    private String baseUrl=Constant.baseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_reg_confirm = findViewById(R.id.btn_reg_confirm);
        et_reg_username = findViewById(R.id.et_reg_username);
        et_reg_password = findViewById(R.id.et_reg_password);
        et_reg_confirmpwd = findViewById(R.id.et_reg_confirmpwd);

        btn_reg_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        //TODO：判断用户名和密码
        String username = et_reg_username.getText().toString();
        String password = et_reg_password.getText().toString();
        String confirm_pwd = et_reg_confirmpwd.getText().toString();
        if(!password.equals(confirm_pwd)){
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
        }else{
            User user=new User(username,password);
            Gson gson=new Gson();
            String json=gson.toJson(user);
            String args[]=new String[]{"user","register"};
            String res= OKHttpUtil.postSyncRequest(baseUrl,json,args);
            Log.d("同步:",res);
            if(res.equals("false")){
                //已有该用户
                Toast.makeText(this,"该用户已存在",Toast.LENGTH_SHORT).show();
            } else {
                //注册成功
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}