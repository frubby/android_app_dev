package com.frw.lea.servlearn;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {
    boolean isStop = false;

    class Mybind extends Binder {
        int i = 1;

        public void stop() {
            isStop = true;
        }
    }

    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("frw", "onCreate");
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (!isStop) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("frw", "thread : " + (i++));
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("frw", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("frw", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("frw", "onBind");
        return new Mybind();
    }
}
