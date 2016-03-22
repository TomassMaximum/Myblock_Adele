package com.example.make201512.makeblock_adele;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by make201512 on 2016/3/21.
 */
public class ConnectDialog extends Dialog implements DialogInterface.OnClickListener{

    private static final String TAG = "ConnectDialog";

    //Log相对布局的宽高
    RelativeLayout relativeLayout;

    Context mainActivityContext;

    ImageView loginScanner;
    RotateAnimation scannerRotateAnimation;

    public ConnectDialog(Context context,int theme) {
        super(context,theme);
        this.mainActivityContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_connect);

        loginScanner = (ImageView) findViewById(R.id.login_scanner);
        scannerRotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(mainActivityContext, R.anim.radar_scanner_rotate);
        loginScanner.startAnimation(scannerRotateAnimation);

        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        int width = relativeLayout.getWidth();
        int height = relativeLayout.getHeight();

        Log.e(TAG,"Width:" + width + "::::::Height:" + height);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
