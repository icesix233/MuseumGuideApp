package com.example.wifilocation997;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wifilocation997.database.ExhibitDBHelper;
import com.example.wifilocation997.entity.Exhibit;
import com.example.wifilocation997.fragment.HomeFragment;
import com.example.wifilocation997.util.ToastUtil;
import java.util.List;


public class ExhibitlistActivity extends AppCompatActivity {

    // 声明一个商品数据库的帮助器对象
    private ExhibitDBHelper mDBHelper;
    private GridLayout gl_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitlist);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("展品列表");

        gl_channel = findViewById(R.id.gl_channel);

        ImageView iv_cart = findViewById(R.id.iv_cart);
        iv_cart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ExhibitlistActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDBHelper = ExhibitDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

        showGoods();
    }

    private void showGoods() {
// 商品条目是一个线性布局，设置布局的宽度为屏幕的一半
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth , LinearLayout.LayoutParams.WRAP_CONTENT);
        // 查询商品数据库中的所有商品记录
        List<Exhibit> list = mDBHelper.queryAllGoodsInfo();

        // 移除下面的所有子视图
        gl_channel.removeAllViews();

        for (Exhibit info : list) {
            // 获取布局文件item_goods.xml的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_biankuang = view.findViewById(R.id.iv_biankuang);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_position = view.findViewById(R.id.tv_position);
            Button btn_add = view.findViewById(R.id.btn_add);
            iv_biankuang.setImageResource(R.drawable.biankuang);
            iv_thumb.setImageURI(Uri.parse(info.pic_path));
            tv_name.setText(info.exhibit_name);
            tv_position.setText(info.position);

            // 点击展品按钮，跳转到商品详情页面
            btn_add.setOnClickListener(v -> {
                Intent intent = new Intent(ExhibitlistActivity.this, DetailActivity.class);
                intent.putExtra("exhibit_number", info.exhibit_number);
                startActivity(intent);
            });

            // 把商品视图添加到网格布局
            gl_channel.addView(view, params);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }
}