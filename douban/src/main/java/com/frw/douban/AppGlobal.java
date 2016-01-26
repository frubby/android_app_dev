package com.frw.douban;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by frw on 2016/1/19.
 */
public class AppGlobal {
    private static AppGlobal app;
    RequestQueue mQueue ;


    ImageLoaderConfiguration config;
    private ImageLoader imageLoader;

    public  ImageLoader getImageLoader(){
        return imageLoader;
    }
    public RequestQueue getRequestQueue(){
        return mQueue;
    }
    private AppGlobal(Context context){
        mQueue = Volley.newRequestQueue(context);
        File cacheDir = StorageUtils.getCacheDirectory(context);
        config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
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
    }
    public static AppGlobal getInstance(Context context){
        if(app==null){
            app=new AppGlobal(context);
        }
        return app;
    }

}
