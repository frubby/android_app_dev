package com.frw.lea.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frw.lea.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {
    public  static  final  String TAG="LeftFragment";

    public LeftFragment() {
        Log.d(TAG, "LeftFragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

}
