package com.tnplanet.lastscreen_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tnplanet.lastscreen_sdk.LastscreenAD;

public class MainActivity extends AppCompatActivity {

    private LastscreenAD lastscreenAD;
    private TextView mainTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTv = findViewById(R.id.main_tv);

        lastscreenAD = new LastscreenAD(MainActivity.this);
        lastscreenAD.setInitCallBack(new LastscreenAD.LastscreenInitCallBack() {
            @Override
            public void initCallBack(boolean valid, String msg) {   //광고 준비
                Log.d("MainActivity", "LastscreenCallBack : "+valid+" / "+msg);
                mainTv.setText(msg);
            }
        });
        lastscreenAD.init("test_sdk_key");
    }

    @Override
    public void onBackPressed() {
        lastscreenAD.showAD();   //광고 호출(앱 종료 시점)
    }
}
