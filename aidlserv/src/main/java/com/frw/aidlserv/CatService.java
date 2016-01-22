package com.frw.aidlserv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class CatService extends Service {
   private  String colors[]={"red","yellow","white"};
    private double weights[]={1.1,2.2,3.3};
    private String color;
    private double weight;
    private CatBinder catbinder;
    boolean isEnd=false;
    public CatService() {
        color=colors[0];
    }

    public class CatBinder extends ICat.Stub{
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getColor() throws RemoteException {

            return color;
        }

        @Override
        public double getWeight() throws RemoteException {
            return weight;
        }
    }

    @Override
    public void onCreate() {
        Log.d("frw", "serv create");
        super.onCreate();
        catbinder=new CatBinder();

        new Thread(){
            @Override
            public void run() {
                int i=0;
                while(!isEnd){
                    color=colors[(i++)%3];
                    try {
                        this.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("frw", "serv onBind");
       return catbinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isEnd=true;
        return super.onUnbind(intent);
    }
}
