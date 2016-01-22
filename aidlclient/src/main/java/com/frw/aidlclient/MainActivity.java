package com.frw.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.frw.aidlserv.ICat;

public class MainActivity extends AppCompatActivity {

    ICat remoteCat;
    ServiceConnection servConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            remoteCat= ICat.Stub.asInterface(iBinder);
            Log.d("frw","client connected");
            try {
                Log.d("frw",remoteCat.getColor());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ((Button)this.findViewById(R.id.btn_serv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setAction("com.frw.serv.icat");
                it.setPackage("com.frw.aidlserv");
                bindService(it, servConn, BIND_AUTO_CREATE);
            }
        });


        ((Button)this.findViewById(R.id.btn_unserv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(servConn);
            }
        });
        ((Button)this.findViewById(R.id.btn_query)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("frw", "query :" + remoteCat.getColor());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
