package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class ConnectActivity extends Activity {

    ImageView loginScanner;
    RotateAnimation scannerRotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_connect);

        loginScanner = (ImageView) findViewById(R.id.login_scanner);
        scannerRotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.radar_scanner_rotate);
        loginScanner.startAnimation(scannerRotateAnimation);
    }
}
