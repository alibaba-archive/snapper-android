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
                .init("https://push.teambition.com/websocket?token=OAuth2 s1SS7WhG9BcAGIY83GvfAKyikik=ZAJEsvnI032852c785df997b5e1dab722ac86ea71ff89f4c9d64cafc8755f054042e449e183e0b6a7338724f9e63867e0a077abf55b6ee989d80a7c9cb0a624ac8bc89d387352cc78840c4ea5c6880e4a923162c8d5ae2074a5e311e646e28fcfc67409d")
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
