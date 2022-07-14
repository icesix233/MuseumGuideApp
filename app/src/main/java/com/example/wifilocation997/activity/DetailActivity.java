package com.example.wifilocation997.activity;
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


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wifilocation997.Constant.Constant;
import com.example.wifilocation997.R;
import com.example.wifilocation997.database.ExhibitDBHelper;
import com.example.wifilocation997.entity.Exhibit;
import com.example.wifilocation997.entity.Message;
import com.example.wifilocation997.util.OKHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_year;
    private TextView tv_goods_position;
    private TextView tv_goods_introduction;
    private ImageView iv_goods_pic;
    private TextView tv_title_board;
    private GridLayout message_board;
    private Button btn_remark;

    private ExhibitDBHelper mDBHelper;
    private int mGoodsId;
    private String user_name = null;
    private LinearLayout.LayoutParams params;
    private Button btn_audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tv_title = findViewById(R.id.tv_title);
        tv_goods_position = findViewById(R.id.tv_goods_position);
        tv_name = findViewById(R.id.tv_username);
        tv_goods_introduction = findViewById(R.id.tv_goods_introduction);
        tv_year = findViewById(R.id.tv_year);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        btn_audio = findViewById(R.id.btn_audio);

        tv_title_board = findViewById(R.id.tv_title_board);
        message_board = findViewById(R.id.message_board);
        btn_remark = findViewById(R.id.btn_remark);

        btn_audio.setOnClickListener(this);
        btn_remark.setOnClickListener(this);

        mDBHelper = ExhibitDBHelper.getInstance(this);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");

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
            tv_title.setText("展品信息");
            tv_goods_introduction.setText(info.introduction);
            tv_goods_position.setText(info.position);
            tv_year.setText(info.year);
            iv_goods_pic.setImageURI(Uri.parse(info.pic_path));
            tv_title_board.setText("留言板");


            // 展示评论功能
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            params = new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            // 向服务器查询所有评论
            List<Message> list = null;
            Gson gson = new Gson();
            String json = gson.toJson(mGoodsId);   //发送给服务器展品编号
            String args[] = new String[]{"message", "show"};
            String res = null;//服务器传回的json字符串
            res = OKHttpUtil.postSyncRequest(Constant.baseURL_ypy, json, args);
            if(res==null){
                Toast.makeText(this, "服务器响应超时", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("同步:", res);
                list = gson.fromJson(res, new TypeToken<List<Message>>() {
                }.getType());
                showList(list);
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_remark:
                if (user_name == null) {
                    Toast.makeText(this, "您当前处于游客模式，无法发表评论",
                            Toast.LENGTH_SHORT).show();
                } else {
                    final EditText editText1 = new EditText(this);
                    AlertDialog.Builder inputDialog1 =
                            new AlertDialog.Builder(this);
                    inputDialog1.setTitle("请输入评论").setView(editText1);
                    inputDialog1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(DetailActivity.this, editText1.getText().toString(), Toast.LENGTH_SHORT).show();
                                    remark(editText1.getText().toString());
                                }
                            }).show();
                }
                break;
            case R.id.btn_audio:
                Intent intent1 = new Intent(this, MusicActivity.class);
                intent1.putExtra("exhibit_number", mGoodsId);
                intent1.putExtra("position", mGoodsId);
                startActivity(intent1);
                finish();
        }
    }

    private void remark(String content) {

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
        String createDate = formatter.format(curDate);   //格式转换

        Message message = new Message(null, mGoodsId, user_name, createDate, content);
        //okhttp发送给服务器
        Gson gson = new Gson();
        String json = gson.toJson(message);
        String args[] = new String[]{"message", "set"};
        String res = null;//服务器传回的json字符串
        res = OKHttpUtil.postSyncRequest(Constant.baseURL_ypy, json, args);
        if (res == null) {
            Toast.makeText(this, "服务器响应超时", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("同步:", res);
            List<Message> list = null;
            list = gson.fromJson(res, new TypeToken<List<Message>>() {
            }.getType());
            showList(list);
        }
    }

    public void showList(List<Message> list) {
        //循环展示评论列表
        message_board.removeAllViews(); // 移除下面的所有子视图
        if (list != null) {
            for (Message message_info : list) {
                // 获取布局文件item_goods.xml的根视图
                View view = LayoutInflater.from(this).inflate(R.layout.item_messages, null);
                ImageView iv_biankuang2 = view.findViewById(R.id.iv_biankuang2);
                TextView mm_message = view.findViewById(R.id.mm_message);
                TextView mm_user = view.findViewById(R.id.mm_user);
                TextView mm_date = view.findViewById(R.id.mm_date);

                iv_biankuang2.setImageResource(R.drawable.biankuang);
                mm_message.setText(message_info.content);
                mm_user.setText(message_info.user_name+"：");
                mm_date.setText(message_info.date);
                // 点击用户名，跳转到聊天页面
                mm_user.setOnClickListener(v -> {
                    if(user_name == null){
                        Toast.makeText(this, "您当前处于游客模式，无法发起聊天",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message_info.user_name, EMConversation.EMConversationType.Chat, true);
                        Log.d("PAN", "CONVERSATION: " + conversation.conversationId());
                        EMClient.getInstance().chatManager().getAllConversations();
                        EMClient.getInstance().groupManager().getAllGroups();
                        ChatActivity.actionStart(getApplicationContext(), conversation.conversationId(), 1);
                    }
                });


                // 把商品视图添加到网格布局
                message_board.addView(view, params);
            }
        }
    }
}