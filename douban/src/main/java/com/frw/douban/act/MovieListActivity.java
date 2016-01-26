package com.frw.douban.act;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.frw.douban.AppGlobal;
import com.frw.douban.R;
import com.frw.douban.bean.MovieBean;
import com.frw.douban.comm.ConstValue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    public static final String TAG = "MovieListActivity";
    ListView lv;
    CustomAdapter adapter;
    List<MovieBean> list;
    DisplayImageOptions options;
    String type;

    AppGlobal app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Bundle bubdle = this.getIntent().getExtras();
        if (bubdle == null)
            return;
        type = bubdle.getString("list_type");
        init();
    }

    private void init() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();                                   // 创建配置过得DisplayImageOption对象

        lv = (ListView) this.findViewById(R.id.lv_mv);
        adapter = new CustomAdapter(this);
        app = AppGlobal.getInstance(this);
        list=new ArrayList<MovieBean>();
        lv.setAdapter(adapter);
        app.getRequestQueue().add(createReq());




    }



    private List<MovieBean> Str2Movies(String str){
        List<MovieBean> list=new ArrayList<MovieBean>();

        JSONObject jsonObject= JSON.parseObject(str);

        int  count=jsonObject.getInteger("count");
        int  start=jsonObject.getInteger("start");
        int  total=jsonObject.getInteger("total");


        JSONArray jarry=jsonObject.getJSONArray("subjects");


        for(int i=0;i<count;i++){
            MovieBean movieBean=new MovieBean();
            jsonObj2Movie(jarry.getJSONObject(i),movieBean);
            list.add(movieBean);
        }
        return list;
    }



    public  void jsonObj2Movie(JSONObject json,MovieBean movieBean){
        movieBean.setId(json.getInteger("id"));
        movieBean.setTitle(json.getString("title"));
        movieBean.setOriginal_title(json.getString("original_title"));
        JSONObject imgs=json.getJSONObject("images");
        movieBean.setImages_small(imgs.getString("small"));
//        movieBean.setGenres((String[])json.getJSONArray("genres").toArray());


    }

    private StringRequest createReq() {
        StringRequest stringRequest = new StringRequest(ConstValue.DB_BASE+type,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d(TAG, response);
                        list=Str2Movies(response);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        return stringRequest;
    }


    class CustomAdapter extends BaseAdapter {
        LayoutInflater inflater;
        public  CustomAdapter (Context context){
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyListener myListener;
            if(view==null) {
                view = inflater.inflate(R.layout.lv_item_mv, null);
            }

            MovieBean movie=list.get(i);
            TextView txTitle=(TextView)view.findViewById(R.id.item_mv_title_chn);
            txTitle.setText(movie.getTitle());
            TextView txTitleEng=(TextView)view.findViewById(R.id.item_mv_title_eng);
//            txTitleEng.setText(movie.getOriginal_title());
            TextView txInfo=(TextView)view.findViewById(R.id.item_mv_info);
            txInfo.setText(Arrays.toString(movie.getDirectors()));

            ImageView img=(ImageView)view.findViewById(R.id.item_mv_pic);
            MovieListActivity.this.app.getImageLoader().displayImage(movie.getImages_small(), img, options, null);

            Button btn=(Button)view.findViewById(R.id.item_mv_btn_detail);
            btn.setOnClickListener(new MyListener(i));

            return view;
        }
    }
    private class MyListener implements View.OnClickListener {
        int mPosition;
        public MyListener(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(MovieListActivity.this, "pos : "+mPosition, Toast.LENGTH_SHORT).show();
            Intent it=new Intent();
            it.setClass(MovieListActivity.this,MovieDetailActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("movie",list.get(mPosition));
            it.putExtras(bundle);
            startActivity(it);
        }

    }
}
