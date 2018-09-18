package com.example.zunnorain.telcard_khokha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zunnorain.telcard_khokha.OptionsActivity.OptionsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent i = new Intent(this,OptionsActivity.class);
        Thread t = new Thread() {
            public void run(){
                try{
                    sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        t.start();

    }



    @Override
    public void onStart() {
        super.onStart();
    }

}
