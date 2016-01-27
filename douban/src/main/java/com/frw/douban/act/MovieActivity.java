package com.frw.douban.act;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.frw.douban.R;
import com.frw.douban.comm.ConstValue;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {
    BootstrapButton btn_mv_now;
    BootstrapButton btn_mv_soon;
    BootstrapButton btn_mv_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        init();

    }

    private void init() {
        btn_mv_now = (BootstrapButton) findViewById(R.id.btn_mv_now);
        btn_mv_soon = (BootstrapButton) findViewById(R.id.btn_mv_soon);
        btn_mv_us = (BootstrapButton) findViewById(R.id.btn_mv_us);
        btn_mv_now.setOnClickListener(this);
        btn_mv_soon.setOnClickListener(this);
        btn_mv_us.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_mv_now:
                Intent it=new Intent();
                Bundle para=new Bundle();
                para.putString("list_type", ConstValue.MOVIE_NOW);
                it.putExtras(para);
                it.setClass(this,MovieListActivity.class);
                startActivity(it);
                break;
        }
    }



}
