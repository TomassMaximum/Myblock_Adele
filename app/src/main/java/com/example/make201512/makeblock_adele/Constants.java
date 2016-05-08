package com.example.make201512.makeblock_adele;

import java.util.UUID;

/**
 * Created by make201512 on 2016/3/22.
 */
public class Constants {

    //判断是否已连接设备
    public static boolean isConnected = false;

    //是否为第一次进入本Activity的标记。如果第一次进入当前Activity且未连接设备，则自动弹出蓝牙连接对话框。
    public static boolean isFirstEnterMainActivity;

    public static boolean isScreenChanged = true;

    public static int projectsNum = 16;

    public static boolean fragmentIsAdded = false;

    public static int getProjectsNum() {
        return projectsNum;
    }

    public static void setProjectsNum(int projectsNum) {
        Constants.projectsNum = projectsNum;
    }

    public static boolean isWidgetFragmentAdded = false;

    //获取设备时需要使用的UUID，蓝牙模块有唯一特殊的UUID，不能随机生成，一号坑。
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static boolean CONNECTSTATE = false;
}
