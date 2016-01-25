package com.frw.douban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.frw.douban.act.MovieActivity;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    Button btn_movie;
    Button btn_book;
    Button btn_music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(  R.layout.activity_main);

        init();
    }

    private void init(){
        btn_book=(Button)findViewById(R.id.btn_book);
        btn_book.setOnClickListener(this);
        btn_movie=(Button)findViewById(R.id.btn_movie);
        btn_movie.setOnClickListener(this);
        btn_music=(Button)findViewById(R.id.btn_music);
        btn_music.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_movie:
                Intent it=new Intent();
                it.setClass(this,MovieActivity.class);
                startActivity(it);
                break;
        }
    }
}
