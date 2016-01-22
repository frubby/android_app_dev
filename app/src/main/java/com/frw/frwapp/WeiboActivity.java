package com.frw.frwapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.frw.frwapp.act.WBAuthActivity;
import com.frw.frwapp.fragm.NewsFragment;
import com.frw.frwapp.fragm.PersonFragment;
import com.frw.frwapp.util.ImageUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

public class WeiboActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "WeiboActivity";
    AppGlobal app;
    public  static  final  int MSG_USER_HEAD_IMG=1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_USER_HEAD_IMG:
                    Log.d(TAG,"set head");
                    Bundle bundle=msg.getData();
                    byte[] user_img=bundle.getByteArray("img");
                    ((ImageView)WeiboActivity.this.findViewById(R.id.nav_user_img)).setImageBitmap(BitmapFactory.decodeByteArray(user_img, 0, user_img.length));
                    break;

            }

        }
    };
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weibo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("frw", "setting");
            Intent it = new Intent();
            it.setClass(this, SettingsActivity.class);
            startActivity(it);
            return true;
        } else if (id == R.id.action_logout) {
            logout_weibo();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            NewsFragment firstFragment = new NewsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, firstFragment).commit();



        } else if (id == R.id.nav_friends) {
            PersonFragment firstFragment = new PersonFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, firstFragment).addToBackStack(null).commit();
            toolbar.setTitle("朋友");
            Spinner spinner=new Spinner(getSupportActionBar().getThemedContext());
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.friend_type_ary, R.layout.friend_type_spinner_item);
            adapter.setDropDownViewResource(R.layout.friend_type_spinner_item);
            spinner.setAdapter(adapter);
            toolbar.addView(spinner);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("frw","i ;" +i);
                    fragListener.OnFragMainChanged(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else if (id == R.id.nav_msgs) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void init() {

        app = AppGlobal.getInstance();
        app.setmAccessToken(AccessTokenKeeper.readAccessToken(this));

        int rel = app.isLogin();
        if (rel < 0) {
            login_weibo();
        } else {
            setUserInfo();
        }

    }


    private void setUserInfo() {
        Oauth2AccessToken mAccessToken = app.getmAccessToken();
        UsersAPI mUsersAPI = new UsersAPI(this, Constants.APP_KEY, mAccessToken);
        long uid = Long.parseLong(mAccessToken.getUid());
        Log.d("frw", "uid " + uid);
        mUsersAPI.show(uid, new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    Log.i(TAG, response);
                    // 调用 User#parse 将JSON串解析成User对象
                    final User user = User.parse(response);
                    if (user != null) {
                        Toast.makeText(WeiboActivity.this,
                                "获取User信息成功，用户昵称：" + user.screen_name,
                                Toast.LENGTH_LONG).show();
                        ((TextView) WeiboActivity.this.findViewById(R.id.nav_user_name)).setText(user.name);

                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                ImageUtil imgUtil=new ImageUtil();
                                byte[] user_img= imgUtil.downloadImg(user.profile_image_url);
                                Message msg=Message.obtain();
                                msg.what=MSG_USER_HEAD_IMG;
                                Bundle bundle=new Bundle();
                                bundle.putByteArray("img",user_img);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        }.start();

                    } else {
                        Toast.makeText(WeiboActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }

    private void login_weibo() {
        AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        SsoHandler mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorizeWeb(new AuthListener());
    }

    private void logout_weibo() {
        AccessTokenKeeper.clear(getApplicationContext());
        app.setmAccessToken(new Oauth2AccessToken());
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            AppGlobal app = AppGlobal.getInstance();

            // 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(WeiboActivity.this, mAccessToken);
                Toast.makeText(WeiboActivity.this,
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(WeiboActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(WeiboActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(WeiboActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }



    OnFragMainChanged fragListener;
    public interface  OnFragMainChanged{
        void OnFragMainChanged(int mark);
    }
    public void setOnFragMainChangedListener(OnFragMainChanged listener){
        this.fragListener=listener;
    }
}
