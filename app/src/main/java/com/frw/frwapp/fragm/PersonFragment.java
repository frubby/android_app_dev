package com.frw.frwapp.fragm;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frw.frwapp.AppGlobal;
import com.frw.frwapp.Constants;
import com.frw.frwapp.R;
import com.frw.frwapp.WeiboActivity;
import com.frw.frwapp.bean.WeiboFriend;
import com.frw.frwapp.util.ImageUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.AbsOpenAPI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends Fragment implements WeiboActivity.OnFragMainChanged {
    public static final int SHOW_WATCHER = 0;
    public static final int SHOW_FANS = 1;
    private ListView mListView;
    private List<WeiboFriend> items;
    private CustomListAdapter cadp;
    ImageLoaderConfiguration config;
    DisplayImageOptions options;
    protected ImageLoader imageLoader;

    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeiboActivity act = (WeiboActivity) this.getActivity();
        act.setOnFragMainChangedListener(this);
        File cacheDir = StorageUtils.getCacheDirectory(this.getContext());
        config = new ImageLoaderConfiguration.Builder(this.getContext())
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs().
                       .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.user_default)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.user_default)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.user_default)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        items = new ArrayList<WeiboFriend>();
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_person);
        cadp = new CustomListAdapter(this.getContext());
        mListView.setAdapter(cadp);
        return view;


    }

    @Override
    public void OnFragMainChanged(int mark) {
        Log.d("frw", "OnFragMainChanged " + mark);
        switch (mark) {
            case SHOW_WATCHER:
                showFirends();
                break;
        }
    }

    private void showFirends() {

        AppGlobal app = AppGlobal.getInstance();
        Oauth2AccessToken mAccessToken = app.getmAccessToken();
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put("access_token", mAccessToken.getToken());
        params.put("uid", mAccessToken.getUid());
        params.put("trim_status", 0);
        params.put("count", 200);
        Log.d("frw", "map " + params.toString());
        Log.d("frw", "mAccessToken " + mAccessToken.getToken());
        String url = "https://api.weibo.com/2/friendships/friends.json";
        new AsyncWeiboRunner(this.getContext()).requestAsync(url, params, "GET", new RequestListener() {
            @Override
            public void onWeiboException(WeiboException e) {

            }

            @Override
            public void onComplete(String s) {
                Log.d("frw", s);
                JSONObject object = JSON.parseObject(s);
                Object jsonArray = object.get("users");
                List<WeiboFriend> list = JSON.parseArray(jsonArray + "", WeiboFriend.class);
                int i = 0;
                for (WeiboFriend tmp : list) {
                    Log.d("frw", (i++) + ": user_id :" + tmp.getId() + "  name : " + tmp.getName());
                    items.add(tmp);
                }
                cadp.notifyDataSetChanged();
            }
        });

    }

    class CustomListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext = null;

        public CustomListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ImageView img_head;
            final TextView tv_name;
            final TextView tv_info;
            if (view == null) {
                view = mInflater.inflate(R.layout.list_item_person, null);
            }
            WeiboFriend u = items.get(i);
            img_head = (ImageView) view.findViewById(R.id.lv_item_person_head);
            tv_name = (TextView) view.findViewById(R.id.lv_tv_name);
            tv_name.setText(u.getName());
            tv_name.setTextColor(Color.RED);

            tv_info = (TextView) view.findViewById(R.id.lv_tv_info);
            tv_info.setText(u.getDescription());

//            ImageUtil.dispImg(u.getProfile_image_url(),img_head);
//            img_head.setBackgroundResource(R.drawable.user_default);
            imageLoader.displayImage(u.getProfile_image_url(), img_head, options, null);
            return view;
        }
    }
}
