package com.frw.frwapp.fragm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frw.frwapp.AppGlobal;
import com.frw.frwapp.Constants;
import com.frw.frwapp.R;
import com.frw.frwapp.WeiboActivity;
import com.frw.frwapp.bean.WeiboFriend;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.AbsOpenAPI;

import java.util.List;

public class PersonFragment extends Fragment  implements WeiboActivity.OnFragMainChanged {
    public  static  final  int SHOW_WATCHER=0;
    public  static  final  int SHOW_FANS=1;
    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeiboActivity act=(WeiboActivity)this.getActivity();
        act.setOnFragMainChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);



    }

    @Override
    public void OnFragMainChanged(int mark){
        Log.d("frw","OnFragMainChanged "+mark);
        switch (mark){
            case SHOW_WATCHER:
                showFirends();
                break;
        }
    }

    private  void  showFirends(){

        AppGlobal app=AppGlobal.getInstance();
        Oauth2AccessToken mAccessToken=app.getmAccessToken();
        WeiboParameters params=new WeiboParameters(Constants.APP_KEY);
        params.put("access_token", mAccessToken.getToken());
        params.put("uid",mAccessToken.getUid());
        params.put("trim_status",0);
        params.put("count", 20);
        Log.d("frw", "map " + params.toString());
        Log.d("frw","mAccessToken "+mAccessToken.getToken());
        String url="https://api.weibo.com/2/friendships/friends.json";
        new AsyncWeiboRunner(this.getContext()).requestAsync(url, params, "GET", new RequestListener(){
            @Override
            public void onWeiboException(WeiboException e) {

            }

            @Override
            public void onComplete(String s) {
                Log.d("frw", s);
                JSONObject object = JSON.parseObject(s);
                Object jsonArray = object.get("users");
                List<WeiboFriend> list = JSON.parseArray(jsonArray+"", WeiboFriend.class);
                for (WeiboFriend tmp: list) {
                        Log.d("frw","user_id :"+tmp.getId()+"  name : "+tmp.getName());
                }
            }
        });

    }
}
