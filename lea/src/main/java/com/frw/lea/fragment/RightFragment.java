package com.frw.lea.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frw.lea.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment implements MainFramActivity.OnButtonClickedListener {
    public  static  final  String TAG="RightFragment";

    TextView tx_right;
    public RightFragment() {
        Log.d(TAG, "RightFragment");
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        MainFramActivity activity=(MainFramActivity)this.getActivity();
        activity.setButtonClickedListener(this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        View view= inflater.inflate(R.layout.fragment_right, container, false);
        tx_right=(TextView)view.findViewById(R.id.tx_right);
        return view;
    }


    @Override
    public void onClick(String msg) {
        tx_right.setText(msg);

    }
}
