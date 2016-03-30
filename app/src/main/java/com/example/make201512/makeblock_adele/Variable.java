package com.example.make201512.makeblock_adele;

/**
 * Created by make201512 on 2016/3/22.
 */
public class Variable {

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
        Variable.projectsNum = projectsNum;
    }
}
