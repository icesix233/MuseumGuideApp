package com.example.wifilocation997.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wifilocation997.DetailActivity;
import com.example.wifilocation997.R;
import com.example.wifilocation997.database.ExhibitDBHelper;
import com.example.wifilocation997.entity.Exhibit;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThingsFragment extends Fragment {

    // 声明一个商品数据库的帮助器对象
    private ExhibitDBHelper mDBHelper;
    private GridLayout gl_channel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThingsFragment newInstance(String param1, String param2) {
        ThingsFragment fragment = new ThingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_things, container, false);
        gl_channel = view.findViewById(R.id.gl_channel);


        mDBHelper = ExhibitDBHelper.getInstance(this.getActivity());
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

        showGoods();
        return view;
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
            View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_goods, null);
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
                Intent intent = new Intent(this.getActivity(), DetailActivity.class);
                intent.putExtra("exhibit_number", info.exhibit_number);
                startActivity(intent);
            });

            // 把商品视图添加到网格布局
            gl_channel.addView(view, params);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }
}