package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private boolean isConnected;
    ConnectDialog connectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //从SharedPreference中获取连接信息
        SharedPreferences sharedPreferences = getSharedPreferences("Makeblock_Adele",MODE_PRIVATE);
        isConnected = sharedPreferences.getBoolean("isConnected",false);

        //判断连接信息。为true则表示已连接，直接显示主界面；为false则表示未连接，自动弹出连接对话框
        if (isConnected){
            //开启主界面（MakeblockActivity）
        }else {
            //开启连接界面（ConnectActivity）
            connectDialog = new ConnectDialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            connectDialog.show();
        }

    }
}
