package com.teambition.snapperandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.teambition.snapper.Snapper;
import com.teambition.snapper.SnapperListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Snapper.getInstance()
                .init("https://push.teambition.com/websocket?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1M2FkNGE3NTU2M2QzMTNjNWFiNjgwNTgiLCJleHAiOjE0NjM2NDkzNjh9.njd9jd7NokPG6r2qtAjglQNUtgMfpsqgxDygWEGATuU")
                .setAutoRetry(true)
                .setListener(new SnapperListener() {
                    @Override
                    public void onMessage(String msg) {
                        Log.d("Snapper", "message:" + msg);
                    }
                })
                .log(true)
                .setRetryInterval(3 * 1000)
                .setMaxRetryTimes(5)
                .open();

        Snapper.getInstance().send("message");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Snapper.getInstance().close();
    }
}
