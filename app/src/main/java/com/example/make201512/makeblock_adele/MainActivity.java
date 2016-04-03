package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.io.InputStream;

import at.markushi.ui.ActionView;
import at.markushi.ui.action.BackAction;
import at.markushi.ui.action.DrawerAction;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    //连接对话框
    ConnectDialog connectDialog;

    //本Activity的根视图
    View rootView;

    //连接icon的ImageView
    ImageView iconLink;

    Handler handler;

    Bitmap blurScreenShot;

    ActionView actionView;

    MenuFragment menuFragment;

    AddFloatingActionButton addProjectFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProjectFab = (AddFloatingActionButton) findViewById(R.id.fab_add_project);
        addProjectFab.setOnClickListener(this);

        iconLink = (ImageView) findViewById(R.id.icon_link);
        iconLink.setOnClickListener(this);

        //设置是否第一次进入Activity标记为真，用于判断是否打开连接对话框
        Variable.isFirstEnterMainActivity = true;

        //创建Handler的对象，用于更新UI
        handler = new MyHandler(this);

        //找到本Activity的根View，用于获取屏幕截图
        rootView = findViewById(android.R.id.content);

        MainFragment mainFragment = new MainFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mainFragment);
        fragmentTransaction.commit();

        menuFragment = new MenuFragment();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

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

        int id = v.getId();
        switch (id){
            case R.id.icon_link:{

                new GetCompressedScreenShot(this).start();

                iconLink.setClickable(false);
                break;
            }
            case R.id.fab_add_project:{
                Intent intent = new Intent(this,ProjectActivity.class);
                intent.putExtra("enter_mode","add_project");
                startActivity(intent);
            }
        }

    }

    public void showMenu(){
        actionView.setAction(new BackAction(),ActionView.ROTATE_CLOCKWISE);

        //在这里打开菜单碎片
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.menu_fragment_in, R.anim.menu_fragment_out);

        if (menuFragment.isAdded()){
            fragmentTransaction.show(menuFragment).commit();
        }else {
            fragmentTransaction.add(R.id.menu_fragment_container,menuFragment).commit();
        }
    }

    public void hideMenu(){
        actionView.setAction(new DrawerAction(), ActionView.ROTATE_CLOCKWISE);
        //在这里关闭菜单碎片
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.menu_fragment_in,R.anim.menu_fragment_out);
        fragmentTransaction.hide(menuFragment).commit();
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
            //如果列表发生滑动屏幕改变，则重新截图并压缩加毛玻璃。如果未改变，则省去此步骤复用之前的图片以节约内存。
            if (Variable.isScreenChanged || blurScreenShot == null){

                if (blurScreenShot != null){
                    blurScreenShot.recycle();
                    Log.e(TAG,"回收完成");
                }

                getScreenShotAndCompress(rootView);
                Variable.isScreenChanged = false;
                Log.e(TAG,"截图操作");
            }

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
            connectDialog = new ConnectDialog(mainActivity);
            connectDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(blurScreenShot));
            connectDialog.show();

            //将第一次进入Activity标记设为false
            Variable.isFirstEnterMainActivity = false;
        }
    }

    //用于获取当前屏幕截图的方法
    public void getScreenShotAndCompress(final View view){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建一个空的Bitmap并画到canvas上。
                blurScreenShot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                final Canvas mCanvas = new Canvas(blurScreenShot);
                view.draw(mCanvas);

                //压缩截图并加毛玻璃效果。第二个参数是压缩的倍数，第三个参数是毛玻璃效果的半径。
                blurScreenShot = fastblur(blurScreenShot,1/20f,5);
            }
        });

    }

    //一个很牛逼的加毛玻璃的方法，效果优于Android自带的RenderScript，且执行速度也更快。
    public Bitmap fastblur(Bitmap sentBitmap, float scale, int radius) {

        int width = Math.round(sentBitmap.getWidth() * scale);
        int height = Math.round(sentBitmap.getHeight() * scale);
        sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        //Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }
}
