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
                .init("Your uri")
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
