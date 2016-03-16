package com.teambition.snapperandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.teambition.snapper.Snapper;
import com.teambition.snapper.SnapperListener;

public class MainActivity extends AppCompatActivity {

    private Snapper snapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        snapper = Snapper.getInstance();

        snapper.init("your hostname", "your query");
        if (snapper.checkInit()) {
            snapper.setAutoRetry(true);
            snapper.setListener(new SnapperListener() {
                @Override
                public void onMessage(String msg) {
                    Log.d("Snapper", "message:" + msg);
                }
            });
            snapper.log(true);
            snapper.setRetryInterval(3 * 1000);
            snapper.setMaxRetryTimes(5);
            snapper.open();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        snapper.close();
    }
}
