package com.frw.douban.act;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.frw.douban.AppGlobal;
import com.frw.douban.R;
import com.frw.douban.bean.ActorBean;
import com.frw.douban.bean.MovieBean;
import com.frw.douban.comm.ConstValue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String TAG = "MovieDetailActivity";

    TextView tvTitle;
    TextView tvTitleOrg;
    TextView tvDirectors;
    TextView tvYear;
    TextView tvScore;
    TextView tvDetail;

    MovieBean mb;
    DisplayImageOptions options;

    AppGlobal app;

    CustomAdapter actAdapter;
    GridView gv;

    private List<ActorBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle para = this.getIntent().getExtras();
        mb = (MovieBean) para.getSerializable("movie");
        if (mb == null)
            return;
        Log.d(TAG, "id: " + mb.getId());


        app = AppGlobal.getInstance(this);
        init();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();                                   // 创建配置过得DisplayImageOption对象
        app.getRequestQueue().add(createReq());

    }


    private void init() {
        tvTitle = (TextView) findViewById(R.id.tx_mv_title);
        tvTitleOrg = (TextView) findViewById(R.id.tx_mv_orgtitle);
        tvDirectors = (TextView) findViewById(R.id.tx_mv_directors);
        tvYear = (TextView) findViewById(R.id.tx_mv_year);
        tvScore = (TextView) findViewById(R.id.tx_mv_score);
        tvDetail = (TextView) findViewById(R.id.tx_mv_detail);

        list=new ArrayList<ActorBean>(4);
        gv=(GridView)findViewById(R.id.gv_actors);
        actAdapter=new CustomAdapter(this);
        gv.setAdapter(actAdapter);
    }


    private StringRequest createReq() {
        StringRequest stringRequest = new StringRequest(ConstValue.DB_BASE + ConstValue.MOVIE_DETAIL + mb.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        Str2Movie(response, mb);
                        setContent();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        return stringRequest;


    }

    private void setContent() {
        tvTitle.setText(mb.getTitle());
        tvTitleOrg.setText(mb.getOriginal_title());
        tvYear.setText("" + mb.getYear());
        tvScore.setText("" + mb.getRating_average());
        tvDetail.setText(mb.getSummary());

        Log.d(TAG, mb.getSummary());


    }

    private int Str2Movie(String jsonStr, MovieBean mb) {


        JSONObject jobj = JSON.parseObject(jsonStr);
        mb.setSummary(jobj.getString("summary"));
        mb.setYear(jobj.getInteger("year"));
        mb.setRating_average(jobj.getJSONObject("rating").getFloat("average"));

        JSONArray castArr = jobj.getJSONArray("casts");
        int num = castArr.size();
        for (int i = 0; i < num; i++) {
            ActorBean act = new ActorBean();
            JSONObject actObj = castArr.getJSONObject(i);
            act.setId(actObj.getIntValue("id"));
            act.setName(actObj.getString("name"));
            act.setImgM(actObj.getJSONObject("avatars").getString("medium"));
            act.setImgS(actObj.getJSONObject("avatars").getString("small"));
            list.add(act);
        }

        actAdapter.notifyDataSetChanged();

        return 1;
    }


    class CustomAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public  CustomAdapter(Context context){
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
            if (view == null) {

                view = inflater.inflate(R.layout.lv_item_actors, null);
            }

            ActorBean actor = list.get(i);
            TextView txName = (TextView) view.findViewById(R.id.item_actor);
            txName.setText(actor.getName());

            ImageView img = (ImageView) view.findViewById(R.id.item_actor_img);
            Log.d(TAG, actor.getImgM());
            MovieDetailActivity.this.app.getImageLoader().displayImage(actor.getImgM(), img, options, null);

//            Button btn = (Button) view.findViewById(R.id.item_mv_btn_detail);
//            btn.setOnClickListener(new MyListener(i));


            return view;
        }
    }
}
