package com.example.wifilocation997;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg_main = findViewById(R.id.rg_main);

        //每一页的对象
        homeFragment = HomeFragment.newInstance("","");
        pathFragment = new PathFragment();
        friendFragment = new FriendFragment();
        thingsFragment = new ThingsFragment();
        settingFragment = new SettingFragment();

        //设置底部导航栏监听器
        rg_main.setOnCheckedChangeListener(this);
        rg_main.check(R.id.home);

        //为底部导航栏设置字体（图标）
        Typeface font1 = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        TextView rbtn_home = findViewById(R.id.home);
        TextView rbtn_path = findViewById(R.id.path);
        TextView rbtn_friend = findViewById(R.id.friend);
        TextView rbtn_things = findViewById(R.id.things);
        TextView rbtn_setting = findViewById(R.id.setting);
        rbtn_home.setTypeface(font1);
        rbtn_path.setTypeface(font1);
        rbtn_friend.setTypeface(font1);
        rbtn_things.setTypeface(font1);
        rbtn_setting.setTypeface(font1);


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Fragment fragment = null;
        switch (i) {
            case R.id.home:
                fragment = homeFragment;
                break;
            case R.id.path:
                fragment = pathFragment;
                break;
            case R.id.friend:
                fragment = friendFragment;
                break;
            case R.id.things:
                fragment = thingsFragment;
                break;
            case R.id.setting:
                fragment = settingFragment;
                break;
        }
        switchFragment(fragment);
    }


    private void switchFragment(Fragment fragment) {//切换Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }

}