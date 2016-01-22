package com.frw.frwapp.util;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Created by frw on 2016/1/20.
 */
public class HttpUtil {

    private static  class HttpUtilHandle {
        private static HttpUtil httputil=new HttpUtil();
    }

    private CloseableHttpClient httpClient;

    private HttpUtil(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        httpClient = HttpClients.custom().setConnectionManager(cm) .build();
    }

    public  static HttpUtil getInstace(){
        return HttpUtilHandle.httputil;
    }

    public CloseableHttpClient getHttpClient(){
        return httpClient;
    }

}
