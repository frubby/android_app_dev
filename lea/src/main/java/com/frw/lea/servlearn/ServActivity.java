package com.frw.lea.servlearn;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.frw.lea.R;

public class ServActivity extends AppCompatActivity implements View.OnClickListener {

    BootstrapButton btn_start;
    BootstrapButton btn_stop;
    BootstrapButton btn_bind;
    BootstrapButton btn_unbind;

    BindService.Mybind mybind;
    ServiceConnection servConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Log.d("frw","onServiceConnected");
            Log.d("frw"," "+((BindService.Mybind)iBinder).i);
            mybind=((BindService.Mybind)iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("frw","onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serv);
        init();

    }

    private  void init(){
        btn_start=(BootstrapButton)this.findViewById(R.id.btn_start);
        btn_stop=(BootstrapButton)this.findViewById(R.id.btn_stop);
        btn_bind=(BootstrapButton)this.findViewById(R.id.btn_bind);
        btn_unbind=(BootstrapButton)this.findViewById(R.id.btn_unbind);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent it;
        switch (view.getId()){
            case R.id.btn_start:
                it=new Intent();
                it.setClass(this,MyService.class);
                startService(it);
                break;
            case R.id.btn_stop:
                it=new Intent();
                it.setClass(this, MyService.class);
                stopService(it);
                break;
            case R.id.btn_bind:
                it=new Intent();
                it.setClass(this, BindService.class);
                bindService(it,servConn,BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                mybind.stop();
                unbindService(servConn);
                break;
        }
    }
}
