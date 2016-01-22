package com.frw.lea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.frw.lea.fragment.MainFramActivity;
import com.frw.lea.servlearn.ServActivity;

public class MainActivity extends AppCompatActivity {

   private BootstrapButton btnServ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();


    }

    private  void init(){
        btnServ=(BootstrapButton)this.findViewById(R.id.btn_serv);
        btnServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setClass(MainActivity.this,ServActivity.class);
                startActivity(it);
            }
        });
        ((BootstrapButton)this.findViewById(R.id.btn_frag)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setClass(MainActivity.this,MainFramActivity.class);
                startActivity(it);
            }
        });

    }
}
