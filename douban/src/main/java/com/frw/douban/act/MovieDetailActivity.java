package com.frw.douban.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.frw.douban.AppGlobal;
import com.frw.douban.R;
import com.frw.douban.bean.MovieBean;
import com.frw.douban.comm.ConstValue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle para = this.getIntent().getExtras();
        mb = (MovieBean) para.getSerializable("movie");
        if (mb == null)
            return;
        Log.d(TAG, "id: " + mb.getId());


        app=AppGlobal.getInstance(this);
        init();

        app.getRequestQueue().add(createReq());

    }


    private void init() {
        tvTitle = (TextView) findViewById(R.id.tx_mv_title);
        tvTitleOrg = (TextView) findViewById(R.id.tx_mv_orgtitle);
        tvDirectors = (TextView) findViewById(R.id.tx_mv_directors);
        tvYear = (TextView) findViewById(R.id.tx_mv_year);
        tvScore = (TextView) findViewById(R.id.tx_mv_score);
        tvDetail = (TextView) findViewById(R.id.tx_mv_detail);
    }


    private StringRequest createReq(){
        StringRequest stringRequest = new StringRequest(ConstValue.DB_BASE+ConstValue.MOVIE_DETAIL+mb.getId(),
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
        tvYear.setText(""+mb.getYear());
        tvScore.setText(""+mb.getRating_average());
        tvDetail.setText(mb.getSummary());



    }

    private int Str2Movie(String jsonStr,MovieBean mb){

        JSONObject jobj= JSON.parseObject(jsonStr);
        mb.setSummary(jobj.getString("summary"));


        return 1;
    }


}
