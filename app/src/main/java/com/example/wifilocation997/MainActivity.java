package com.example.wifilocation997;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.wifilocation997.fragment.FriendFragment;
import com.example.wifilocation997.fragment.HomeFragment;
import com.example.wifilocation997.fragment.PathFragment;
import com.example.wifilocation997.fragment.SettingFragment;
import com.example.wifilocation997.fragment.ThingsFragment;

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

//        //为底部导航栏设置字体（图标）
//        Typeface font1 = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
//        TextView rbtn_home = findViewById(R.id.home);
//        TextView rbtn_path = findViewById(R.id.path);
//        TextView rbtn_friend = findViewById(R.id.friend);
//        TextView rbtn_things = findViewById(R.id.things);
//        TextView rbtn_setting = findViewById(R.id.setting);
//        rbtn_home.setTypeface(font1);
//        rbtn_path.setTypeface(font1);
//        rbtn_friend.setTypeface(font1);
//        rbtn_things.setTypeface(font1);
//        rbtn_setting.setTypeface(font1);


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

}