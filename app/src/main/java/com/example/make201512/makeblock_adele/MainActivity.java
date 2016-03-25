package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    //被压缩后的屏幕截图，传给连接对话框用作对话框的背景图片。
    private Bitmap compressedScreenShot;

    //连接对话框
    ConnectDialog connectDialog;

    //本Activity的根视图
    View rootView;

    //连接icon的ImageView
    ImageView iconLink;

    RecyclerView projectsRecyclerView;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconLink = (ImageView) findViewById(R.id.icon_link);
        iconLink.setOnClickListener(this);

        //设置是否第一次进入Activity标记为真，用于判断是否打开连接对话框
        Variable.isFirstEnterMainActivity = true;

        //创建Handler的对象，用于更新UI
        handler = new MyHandler(this);

        //找到本Activity的根View，用于获取屏幕截图
        rootView = findViewById(android.R.id.content);

        projectsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_projects);

        projectsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        projectsRecyclerView.setAdapter(new ProjectListAdapter(this));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, "onWindowFocusChanged be called");

        if (hasFocus){
            iconLink.setClickable(true);
        }

        //如果这是;第一次进入当前Activity且未连接设备，则开启一个线程进行截图操作，避免该动作阻塞主线程造成UI卡顿
        if (Variable.isFirstEnterMainActivity && !Variable.isConnected){
            new GetCompressedScreenShot(this).start();
        }
    }

    @Override
    public void onClick(View v) {
        if (iconLink.isClickable()){
            new GetCompressedScreenShot(this).start();
        }
        iconLink.setClickable(false);
    }

    //用于截图、压缩截图、唤醒Handler的子线程内部类
    public class GetCompressedScreenShot extends Thread{

        MainActivity mainActivity;

        //构造方法
        GetCompressedScreenShot(MainActivity mainActivity){
            this.mainActivity = mainActivity;
        }

        @Override
        public void run() {
            //调用getScreenShotAndCompress方法获取屏幕截图并进行压缩以节省内存
            getScreenShotAndCompress(rootView);

            //唤醒Handler来打开连接对话框进行更新UI的操作
            mainActivity.handler.sendEmptyMessage(0);

            iconLink.setClickable(true);
        }
    }

    //用于更新UI的Handler内部类
    private class MyHandler extends Handler{

        MainActivity mainActivity;

        MyHandler(MainActivity mainActivity){
            this.mainActivity = mainActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            //开启连接对话框（ConnectDialog）
            connectDialog = new ConnectDialog(mainActivity,compressedScreenShot);
            connectDialog.show();

            //将第一次进入Activity标记设为false
            Variable.isFirstEnterMainActivity = false;
        }
    }

    //用于获取当前屏幕截图的方法
    public void getScreenShotAndCompress(View view){

        //获取当前屏幕截图
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        //对拿到的屏幕截图进行压缩，节省内存
        BitmapFactory.Options options = new BitmapFactory.Options();

        //压缩比设为16倍
        options.inSampleSize = 32;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //压缩图片
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        compressedScreenShot = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()),null,options);
    }
}
