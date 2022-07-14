package com.example.wifilocation997.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifilocation997.activity.DetailActivity;
import com.example.wifilocation997.R;
import com.example.wifilocation997.activity.MainActivity;
import com.example.wifilocation997.database.ExhibitDBHelper;
import com.example.wifilocation997.entity.Coordinate;
import com.example.wifilocation997.entity.Exhibit;
import com.example.wifilocation997.entity.User;
import com.example.wifilocation997.util.GetWifi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThingsFragment extends Fragment {

    // 声明一个展品数据库的帮助器对象
    private ExhibitDBHelper mDBHelper;
    private GridLayout gl_channel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String flag="";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private double x=11;
    private double y=7;
    private LinearLayout.LayoutParams params;

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
        // 展品条目是一个线性布局，设置布局的宽度为屏幕宽度
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        params = new LinearLayout.LayoutParams(screenWidth , LinearLayout.LayoutParams.WRAP_CONTENT);
        // 查询展品数据库中的所有展品记录
        List<Exhibit> list = mDBHelper.queryAllGoodsInfo();

        // 移除下面的所有子视图
        gl_channel.removeAllViews();

        //获取当前坐标,优先展示当前展厅展品
        getXY();
        Log.d("PAN", String.valueOf(x));
        Log.d("PAN", String.valueOf(y));
        if((x>0 || x==0) && x<3.5 && (y>0 || y==0) && y<4.5){//展厅三  X∈[0,3.5),Y∈[0,4.5)
            for (Exhibit info : list) {
                if(info.getPosition().equals("展厅三")){
                    addExhibitToView(info);
                    Log.d("PAN","展厅三");
                }
            }
            flag="展厅三";
        }else if((x>0 || x==0) && x<3.5 && (y>4.5 || y==4.5) && y<11){//展厅四 X∈[0,3.5),Y∈[4.5,11)
            for (Exhibit info : list) {
                if(info.getPosition().equals("展厅四")){
                    addExhibitToView(info);
                    Log.d("PAN","展厅四");
                }
            }
            flag="展厅四";
        }else if((x>3.5 || x==3.5) && x<8.5 && (y>1.75 || y==1.75) && y<11){//展厅一 X∈[3.5,8.5),Y∈[1.75,11)
            for (Exhibit info : list) {
                if(info.getPosition().equals("展厅一")){
                    addExhibitToView(info);
                    Log.d("PAN","展厅一");
                }
            }
            flag="展厅一";
        }else if((x>8.5 || x==8.5) && x<14 && (y>1.75 || y==1.75) && y<11){//展厅二 X∈[8.5,14),Y∈[1.75,11)
            for (Exhibit info : list) {
                if(info.getPosition().equals("展厅二")){
                    addExhibitToView(info);
                    Log.d("PAN","展厅二");
                }
            }
            flag="展厅二";
        }else{//其他情况，默认排序
            flag="";
            Log.d("PAN","其他展厅");
        }


        for (Exhibit info : list) {
            if(flag.equals(info.getPosition())){//如果当前展厅就是这个展品所在展厅，就跳过，因为已经优先展示过了
                continue;
            }
            addExhibitToView(info);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }

    private void getXY() {
        GetWifi getWifi = new GetWifi(getActivity());
        Coordinate coordinate = getWifi.scanWifi();
        if (coordinate == null) {
            //错误
            Log.e("error", "坐标对象为空");
            //在子线程中实现Toast
            Toast.makeText(getContext(), "无法获取位置信息", Toast.LENGTH_SHORT).show();
        } else {
            x = coordinate.getPositionX();
            y = coordinate.getPositionY();
            Toast.makeText(getContext(), "已优先展示最近的展厅", Toast.LENGTH_SHORT).show();
        }
    }

    public void addExhibitToView(Exhibit info){
        // 获取布局文件item_goods.xml的根视图
        View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_goods, null);
        ImageView iv_biankuang = view.findViewById(R.id.iv_biankuang);
        ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
        TextView tv_name = view.findViewById(R.id.tv_username);
        TextView tv_position = view.findViewById(R.id.tv_position);
        Button btn_add = view.findViewById(R.id.btn_add);
        iv_biankuang.setImageResource(R.drawable.biankuang);
        iv_thumb.setImageURI(Uri.parse(info.pic_path));
        tv_name.setText(info.exhibit_name);
        tv_position.setText(info.position);

        // 点击展品按钮，跳转到展品详情页面
        btn_add.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), DetailActivity.class);
            intent.putExtra("exhibit_number", info.exhibit_number);
            MainActivity mainActivity = (MainActivity) getActivity();
            User user = mainActivity.getUser();
            String user_name=null;
            if(user!=null){
                user_name=user.getUser_name();
            }
            intent.putExtra("user_name",user_name);
            startActivity(intent);
        });

        // 把展品视图添加到网格布局
        gl_channel.addView(view, params);
    }

}