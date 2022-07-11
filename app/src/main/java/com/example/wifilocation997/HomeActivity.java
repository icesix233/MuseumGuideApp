package com.example.wifilocation997;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wifilocation997.LoginActivity;
import com.example.wifilocation997.R;
import com.example.wifilocation997.entity.User;
import com.example.wifilocation997.fragment.FriendFragment;
import com.example.wifilocation997.fragment.PathFragment;
import com.example.wifilocation997.fragment.SettingFragment;
import com.example.wifilocation997.fragment.ThingsFragment;
import com.example.wifilocation997.util.OKHttpUtil;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity{
    private RadioButton button_home;
    private RadioButton button_path;
    private RadioButton button_friend;
    private RadioButton button_things;
    private RadioButton button_setting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        button_home = findViewById(R.id.home);
        button_path = findViewById(R.id.path);
        button_friend = findViewById(R.id.friend);
        button_things = findViewById(R.id.things);
        button_setting = findViewById(R.id.setting);

        button_home.setOnClickListener((View.OnClickListener) this);
        button_path.setOnClickListener((View.OnClickListener) this);
        button_friend.setOnClickListener((View.OnClickListener) this);
        button_things.setOnClickListener((View.OnClickListener) this);
        button_home.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            //首页按钮
            case R.id.home:
                Intent intent1 = new Intent(this, HomeActivity.class);
                startActivity(intent1);
                //路径按钮
            case R.id.path:
                Intent intent2 = new Intent(this, PathFragment.class);
                startActivity(intent2);
                //好友按钮
            case R.id.friend:
                Intent intent3 = new Intent(this, FriendFragment.class);
                startActivity(intent3);
                //展品按钮
            case R.id.things:
                Intent intent4 = new Intent(this, ThingsFragment.class);
                startActivity(intent4);
                //设置按钮
            case R.id.setting:
                Intent intent5 = new Intent(this, SettingFragment.class);
                startActivity(intent5);
        }
    }

}
