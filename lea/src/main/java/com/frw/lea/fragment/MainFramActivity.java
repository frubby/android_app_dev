package com.frw.lea.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.frw.lea.R;

/**
 * Created by frw on 2016/1/20.
 */
public class MainFramActivity extends FragmentActivity implements View.OnClickListener {
    public  static  final  String TAG="MainFramActivity";

    private OnButtonClickedListener buttonClickedListener;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg==null)
                return;
            switch (msg.what){
                case 100:
                    break;

            }
        }
    };

    String fragmentLeft;
    String fragmentRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mfragment);
        fragmentRight=RightFragment.class.getName();
        fragmentLeft=LeftFragment.class.getName();
       replaceFragment(R.id.main_content, fragmentRight);

        init();
    }


    private void  init(){
        ((Button)this.findViewById(R.id.btn_one)).setOnClickListener(this);
        ((Button)this.findViewById(R.id.btn_two)).setOnClickListener(this);
    }
    private void replaceFragment(int viewResource, String fragmentName){
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        Fragment fragment = Fragment.instantiate(this, fragmentName);
        ft.replace(viewResource, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_one:
//                replaceFragment(R.id.main_content, fragmentRight);
                buttonClickedListener.onClick("test");
                break;
            case R.id.btn_two:
                replaceFragment(R.id.main_content, fragmentLeft);
                break;
        }
    }

    public interface OnButtonClickedListener {
        public  void onClick(String msg);
    }
    public void setButtonClickedListener(OnButtonClickedListener buttonClickedListener){
        this.buttonClickedListener=buttonClickedListener;
    }
}
