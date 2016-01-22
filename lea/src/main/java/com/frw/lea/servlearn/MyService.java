package com.frw.lea.servlearn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("frw","create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("frw", "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int rel= super.onStartCommand(intent, flags, startId);
        Log.i("frw","onStartCommand");

        return rel;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

}
