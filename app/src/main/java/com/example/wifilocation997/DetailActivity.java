package com.example.wifilocation997;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class DetailActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//    }
//}


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wifilocation997.database.ExhibitDBHelper;
import com.example.wifilocation997.entity.Exhibit;
import com.example.wifilocation997.util.ToastUtil;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_year;
    private TextView tv_goods_position;
    private TextView tv_goods_introduction;
    private ImageView iv_goods_pic;
    private ExhibitDBHelper mDBHelper;
    private int mGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tv_title = findViewById(R.id.tv_title);
        tv_goods_position = findViewById(R.id.tv_goods_position);
        tv_name = findViewById(R.id.tv_name);
        tv_goods_introduction = findViewById(R.id.tv_goods_introduction);
        tv_year = findViewById(R.id.tv_year);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        mDBHelper = ExhibitDBHelper.getInstance(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetail();
    }

    private void showDetail() {
        // 获取上一个页面传来的商品编号
        mGoodsId = getIntent().getIntExtra("exhibit_number", 0);
        if (mGoodsId > 0) {
            // 根据商品编号查询商品数据库中的商品记录
            Exhibit info = mDBHelper.queryGoodsInfoById(mGoodsId);
            tv_name.setText(info.exhibit_name);
            tv_title.setText(info.exhibit_name);
            tv_goods_introduction.setText(info.introduction);
            tv_goods_position.setText(info.position);
            tv_year.setText(info.year);
            iv_goods_pic.setImageURI(Uri.parse(info.pic_path));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_cart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }

}