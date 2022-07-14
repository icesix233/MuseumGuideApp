package com.example.wifilocation997.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wifilocation997.R;
import com.example.wifilocation997.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PathFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PathFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_path1;
    private Button btn_path2;
    private Button btn_path3;
    private MainActivity mainActivity;

    public PathFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PathFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PathFragment newInstance(String param1, String param2) {
        PathFragment fragment = new PathFragment();
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
        View view = inflater.inflate(R.layout.fragment_path, container, false);
        btn_path1 = view.findViewById(R.id.btn_path1);
        btn_path2 = view.findViewById(R.id.btn_path2);
        btn_path3 = view.findViewById(R.id.btn_path3);
        btn_path1.setOnClickListener(this);
        btn_path2.setOnClickListener(this);
        btn_path3.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_path1:
                mainActivity.switchMapPhoto(1);
                break;
            case R.id.btn_path2:
                mainActivity.switchMapPhoto(2);
                break;
            case R.id.btn_path3:
                mainActivity.switchMapPhoto(3);
                break;
            default:break;
        }
    }
}