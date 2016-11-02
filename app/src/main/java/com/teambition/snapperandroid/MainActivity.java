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
            .init("http://192.168.0.48:7701/websocket?token=eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NzgxMzg5ODQsInVzZXJJZCI6IjU1YzgzZGZjODBhZjc2MzIyZGY0MjRhNCIsInNvdXJjZSI6ImFuZHJvaWQifQ.o11oNz04X1ytYJmu0VidbcAV3ka-hzLGZwSWlSIKGAA")
//            .init("http://snapper.project.bi/websocket/?token=eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NzgxMzg5ODQsInVzZXJJZCI6IjU1YzgzZGZjODBhZjc2MzIyZGY0MjRhNCIsInNvdXJjZSI6ImFuZHJvaWQifQ.o11oNz04X1ytYJmu0VidbcAV3ka-hzLGZwSWlSIKGAA")
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Snapper.getInstance().close();
    }
}
