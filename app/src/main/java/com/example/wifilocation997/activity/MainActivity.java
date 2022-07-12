package com.example.wifilocation997.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wifilocation997.R;
import com.example.wifilocation997.entity.User;
import com.example.wifilocation997.fragment.FriendFragment;
import com.example.wifilocation997.fragment.HomeFragment;
import com.example.wifilocation997.fragment.PathFragment;
import com.example.wifilocation997.fragment.SettingFragment;
import com.example.wifilocation997.fragment.ThingsFragment;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private Fragment homeFragment;
    private PathFragment pathFragment;
    private FriendFragment friendFragment;
    private ThingsFragment thingsFragment;
    private SettingFragment settingFragment;
    private Button btn_home;
    private Button btn_path;
    private Button btn_friend;
    private Button btn_things;
    private Button btn_setting;
    private User user=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg_main = findViewById(R.id.rg_main);

        //每一页的对象
        homeFragment = HomeFragment.newInstance("","");
        pathFragment = PathFragment.newInstance("","");
        friendFragment = FriendFragment.newInstance("","");
        thingsFragment = ThingsFragment.newInstance("","");
        settingFragment = SettingFragment.newInstance("","");

        //每个按钮对象
        btn_home = findViewById(R.id.btn_home);
        btn_path = findViewById(R.id.btn_path);
        btn_friend = findViewById(R.id.btn_friend);
        btn_things = findViewById(R.id.btn_things);
        btn_setting = findViewById(R.id.btn_setting);

        //设置底部导航栏监听器
        rg_main.setOnCheckedChangeListener(this);
        rg_main.check(R.id.btn_home);

        //取得user对象
        Intent intent = getIntent();
        String user_json = intent.getStringExtra("user_json");
        if(user_json.equals("null")){
            user=null;
            Toast.makeText(this,"游客模式登录",Toast.LENGTH_SHORT).show();//测试
        }else{
            user = new Gson().fromJson(user_json, User.class);
            Toast.makeText(this, user.getUser_name()+" "+ user.getUser_password(),Toast.LENGTH_SHORT).show();//测试
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Fragment fragment = null;
        switch (i) {
            case R.id.btn_home:
                fragment = homeFragment;
                init_button();
                btn_home.setBackgroundResource(R.mipmap.guide1);
                break;
            case R.id.btn_path:
                fragment = pathFragment;
                init_button();
                btn_path.setBackgroundResource(R.mipmap.path1);
                break;
            case R.id.btn_friend:
                fragment = friendFragment;
                init_button();
                btn_friend.setBackgroundResource(R.mipmap.friend1);
                break;
            case R.id.btn_things:
                fragment = thingsFragment;
                init_button();
                btn_things.setBackgroundResource(R.mipmap.exhibition1);
                break;
            case R.id.btn_setting:
                fragment = settingFragment;
                init_button();
                btn_setting.setBackgroundResource(R.mipmap.setting1);
                break;
        }
        switchFragment(fragment);
    }

    public void init_button(){
        btn_home.setBackgroundResource(R.mipmap.guide2);
        btn_path.setBackgroundResource(R.mipmap.path2);
        btn_friend.setBackgroundResource(R.mipmap.friend2);
        btn_things.setBackgroundResource(R.mipmap.exhibition2);
        btn_setting.setBackgroundResource(R.mipmap.setting2);
    }


    private void switchFragment(Fragment fragment) {//切换Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }

    public User getUser() {
        return user;
    }

}