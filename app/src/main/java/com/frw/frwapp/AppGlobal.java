package com.frw.frwapp;

import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.text.SimpleDateFormat;

/**
 * Created by frw on 2016/1/19.
 */
public class AppGlobal {
    private static AppGlobal app;

    public Oauth2AccessToken getmAccessToken() {
        return mAccessToken;
    }

    public void setmAccessToken(Oauth2AccessToken mAccessToken) {
        this.mAccessToken = mAccessToken;
    }

    private Oauth2AccessToken mAccessToken;
    private AppGlobal(){

    }
    public static AppGlobal getInstance(){
        if(app==null){
            app=new AppGlobal();
        }
        return app;
    }

    public int isLogin(){

        int rel=0;
        boolean hasExisted=false;

        if(mAccessToken==null)
            return -1;
        if (mAccessToken.isSessionValid()) {
            hasExisted=true;
        }else
        {
            return -1;
        }
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        Log.d("frw", date);

        return rel;
    }
}
