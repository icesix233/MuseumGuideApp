package com.example.wifilocation997.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifilocation997.R;
import com.example.wifilocation997.activity.HtmlActivity;
import com.example.wifilocation997.activity.MainActivity;
import com.example.wifilocation997.entity.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static String ran = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));//游客模式下，游客的随机编号

    private View view;
    private MainActivity mainActivity;
    private User user;

    private Button btn_modify_username;
    private Button btn_modify_password;
    private Button btn_privacy_policy;
    private Button btn_sdk_list;
    private Button btn_update;
    private Button btn_about_us;
    private TextView tv_username;



    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        init();

        return view;
    }

    private void init() {
        btn_modify_username = view.findViewById(R.id.btn_modify_username);
        btn_modify_password = view.findViewById(R.id.btn_modify_password);
        btn_privacy_policy = view.findViewById(R.id.btn_privacy_policy);
        btn_sdk_list = view.findViewById(R.id.btn_sdk_list);
        btn_update = view.findViewById(R.id.btn_update);
        btn_about_us = view.findViewById(R.id.btn_about_us);
        tv_username = view.findViewById(R.id.tv_username);

        btn_modify_username.setOnClickListener(this);
        btn_modify_password.setOnClickListener(this);
        btn_privacy_policy.setOnClickListener(this);
        btn_sdk_list.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_about_us.setOnClickListener(this);

        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        if(user ==null){
            tv_username.setText("游客"+ran);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_modify_username:
                if(user==null){
                    Toast.makeText(getActivity(),"您当前处于游客模式，无法修改用户名",
                            Toast.LENGTH_SHORT).show();
                }else{
                    final EditText editText1 = new EditText(getActivity());
                    AlertDialog.Builder inputDialog1 =
                            new AlertDialog.Builder(getActivity());
                    inputDialog1.setTitle("请输入用户名").setView(editText1);
                    inputDialog1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            editText1.getText().toString(),
                                            Toast.LENGTH_SHORT).show();
                                    modifyUsername(editText1.getText().toString());
                                }
                            }).show();
                }
                break;
            case R.id.btn_modify_password:
                if(user==null){
                    Toast.makeText(getActivity(),"您当前处于游客模式，无法修改密码",
                            Toast.LENGTH_SHORT).show();
                }else{
                    final EditText editText2 = new EditText(getActivity());
                    editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    AlertDialog.Builder inputDialog2 =
                            new AlertDialog.Builder(getActivity());
                    inputDialog2.setTitle("请输入密码").setView(editText2);
                    inputDialog2.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(),
                                            editText2.getText().toString(),
                                            Toast.LENGTH_SHORT).show();
                                    modifyPassword(editText2.getText().toString());
                                }
                            }).show();
                }
                break;
            case R.id.btn_privacy_policy:
                Intent intent1 = new Intent(getActivity(), HtmlActivity.class);
                intent1.putExtra("type","privacy");
                startActivity(intent1);
                break;
            case R.id.btn_sdk_list:
                Intent intent2 = new Intent(getActivity(), HtmlActivity.class);
                intent2.putExtra("type","sdk");
                startActivity(intent2);
                break;
            case R.id.btn_update:
                Toast.makeText(getActivity(),"当前已是最新版本！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_about_us:
                Intent intent3 = new Intent(getActivity(), HtmlActivity.class);
                intent3.putExtra("type","about");
                startActivity(intent3);
                break;
            default:break;
        }
    }


    public void modifyUsername(String username){
        //okhttp发送给服务器
    }

    public void modifyPassword(String password){
        //okhttp发送给服务器
    }
}