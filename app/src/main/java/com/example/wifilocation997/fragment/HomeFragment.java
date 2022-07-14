package com.example.wifilocation997.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wifilocation997.R;
import com.example.wifilocation997.view.MySurfaceView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MySurfaceView sv_map;
    private TextView tv_title;
    private Switch switch_location;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
         View view = inflater.inflate(R.layout.fragment_home, container, false);

         tv_title = view.findViewById(R.id.tv_title);
         sv_map = view.findViewById(R.id.sv_map);

         switch (getArguments().getString(ARG_PARAM1)){
             case "1":
                 Bitmap map1 = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.map1); //地图图片
                 sv_map.setBitmap(map1);
                 break;
             case "2":
                 Bitmap map2 = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.map2); //地图图片
                 sv_map.setBitmap(map2);
                 break;
             case "3":
                 Bitmap map3 = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.map3); //地图图片
                 sv_map.setBitmap(map3);
                 break;
             default:   //空串，即从MainActivity正常跳转，这时的地图为默认地图
                 Bitmap map = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.map); //地图图片
                 sv_map.setBitmap(map);
                 break;
         }

        return view;
    }
}