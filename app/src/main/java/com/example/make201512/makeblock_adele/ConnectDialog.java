package com.example.make201512.makeblock_adele;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by make201512 on 2016/3/22.
 */
public class ConnectDialog extends Dialog implements View.OnClickListener {

    //雷达Scanner
    ImageView loginScanner;

    //Scanner的动画
    RotateAnimation scannerRotateAnimation;

    //MainActivity的上下文
    Context mainActivity;

    //Scanner位图
    Bitmap scanner;

    //压缩后的Scanner位图
    Bitmap compressedScanner;

    //蓝牙连接标识logo
    ImageView iconLink;

    Typer typer;

    Bitmap blurBackground;

    //构造方法，接收MainActivity传过来的压缩过的屏幕截图和上下文
    public ConnectDialog(Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.mainActivity = context;
//        this.blurBackground = blurBackground;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局文件
        setContentView(R.layout.dialog_connect);

        //找到连接icon的ImageView
        iconLink = (ImageView) findViewById(R.id.radar_icon_link);

        //给连接logo设置监听，点击蓝牙连接logo时关闭当前对话框
        iconLink.setOnClickListener(this);

        //将对话框背景图片设为毛玻璃效果的屏幕截图
        //setBlurBackground(this, compressedScreenShot);

        //获取到压缩过的Scanner
        getScannerAndCompress();

        //设置Scanner位图到ImageView
        loginScanner = (ImageView) findViewById(R.id.login_scanner);
        loginScanner.setImageBitmap(compressedScanner);

        //设置Scanner旋转动画
        scannerRotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(mainActivity, R.anim.radar_scanner_rotate);
        loginScanner.startAnimation(scannerRotateAnimation);

        typer = (Typer) findViewById(R.id.typer);
        typer.setCharacterDelay(50);
        typer.animateText("Disconnected.         \n               \nTap an icon  to\n connect a device.           \n");
    }

    //获取到Scanner位图并压缩的方法
    public void getScannerAndCompress(){

        //从资源文件获取到Scanner的位图
//        scanner = BitmapFactory.decodeResource(mainActivity.getResources(),R.drawable.login_scanner);

        //将位图压缩至原大小的一般。使用原大小位图会造成内存溢出，crash
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//        compressedScanner = BitmapFactory.decodeResource(mainActivity.getResources(),R.drawable.login_scanner,options);


        InputStream in = mainActivity.getResources().openRawResource(+ R.drawable.login_scanner);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;
        compressedScanner = BitmapFactory.decodeStream(in,null,options);

    }

    @Override
    public void onClick(View v) {
        //点击蓝牙连接icon时关闭当前对话框
        this.dismiss();
        if (Variable.isScreenChanged){
            blurBackground.recycle();
        }
    }
}
