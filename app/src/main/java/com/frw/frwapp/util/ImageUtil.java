package com.frw.frwapp.util;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URI;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;

/**
 * Created by frw on 2016/1/20.
 */
public class ImageUtil {
private  static  final  String TAG="ImageUtil";
    HttpClient httpClient;
    public  ImageUtil(){
        httpClient=HttpUtil.getInstace().getHttpClient();
    }
    public byte[] downloadImg(String url){
        byte[] rel=null;
        HttpGet httpGet=new HttpGet();
        httpGet.setURI(URI.create(url));
        HttpResponse hp;
        try {
            hp=httpClient.execute(httpGet);
            Log.d(TAG, "stats code " + hp.getStatusLine().getStatusCode());


            rel=ByteToInputStream.input2byte(hp.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  rel;

    }

    public static void dispImg(String url,ImageView img){
        Log.d(TAG,"disp img");
        HttpClient  httpClient=HttpUtil.getInstace().getHttpClient();
        byte[] rel=null;
        HttpGet httpGet=new HttpGet();
        httpGet.setURI(URI.create(url));
        HttpResponse hp;
        try {
            hp=httpClient.execute(httpGet);
            Log.d(TAG, "stats code " + hp.getStatusLine().getStatusCode());


//            rel=ByteToInputStream.input2byte(hp.getEntity().getContent());
            img.setImageBitmap(BitmapFactory.decodeStream(hp.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
