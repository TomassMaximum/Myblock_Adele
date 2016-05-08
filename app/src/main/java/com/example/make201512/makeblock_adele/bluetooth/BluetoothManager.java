package com.example.make201512.makeblock_adele.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.make201512.makeblock_adele.Constants;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Tom on 2016/4/26.
 * 蓝牙连接管理类，用于连接蓝牙设备，管理连接，收发数据等操作
 */
public class BluetoothManager {

    private static final String TAG = "BluetoothManager";

    Context mContext;

    BluetoothManager mBluetoothManager;

    private BluetoothAdapter mBluetoothAdapter;

    ConnectThread connectThread;

    //单例设计模式，饿汉式
    private BluetoothManager(Context context){
        mContext = context;
    }

    public BluetoothManager getBluetoothManager(Context context){
        if (mBluetoothManager == null){
            mBluetoothManager = new BluetoothManager(context);
        }

        return mBluetoothManager;
    }

    public void startSearching(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }

        mBluetoothAdapter.startDiscovery();
    }

    public void connectDevice(BluetoothDevice bluetoothDevice){

    }

    /**
     * 连接到蓝牙设备的线程。
     * */
    private class ConnectThread extends Thread{

        private final BluetoothDevice mDevice;
        private final BluetoothSocket mSocket;
        private String deviceName;

        //构造方法，接受希望连接到的蓝牙设备
        public ConnectThread(BluetoothDevice bluetoothDevice){
            //接收构造收到的蓝牙设备
            mDevice = bluetoothDevice;

            //获取该蓝牙设备的名称
            deviceName = mDevice.getName();

            //考虑设备蓝牙名称为空的情况，避免空指针
            if (deviceName == null){
                deviceName = "未知设备";
            }

            //连接到Makeblock的蓝牙模块需要设置PIN码，三号坑。
            setBluetoothPairingPin(mDevice);

            //因为当前Socket为final，所以创建临时Socket存储
            BluetoothSocket tmpSocket = null;

            try {
                //使用UUID从服务端蓝牙设备获取到Socket
                tmpSocket = mDevice.createRfcommSocketToServiceRecord(Constants.MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,"不能获取到BluetoothSocket");
            }

            //赋值给本地的Socket
            mSocket = tmpSocket;
        }

        @Override
        public void run() {
            //判断设备是否已配对（绑定），如果已配对，则直接进行连接
            if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                Log.e(TAG,"Socket开始连接");
                try {
                    mSocket.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"Socket连接异常");
                }

                Log.e(TAG,"Socket已连接");
                Constants.CONNECTSTATE = true;

            }else {
                //如果当前蓝牙设备未配对，要手动进行配对工作。
                //SDK低于19的Android版本要利用反射获取到配对方法。四号坑。
                Log.e(TAG,"设备未配对，进行配对操作");
                if (Build.VERSION.SDK_INT >= 19){
                    mDevice.createBond();
                }else {
                    Method method;
                    try {
                        method = mDevice.getClass().getMethod("createBond", (Class[]) null);
                        method.invoke(mDevice, (Object[]) null);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"绑定异常");
                    }
                }
            }
        }

        //设置PIN码的方法，来自小明在mbot中写的代码
        public void setBluetoothPairingPin(BluetoothDevice device){
            byte[] pinBytes = ("0000").getBytes();
            if (Build.VERSION.SDK_INT >= 19){
                device.setPin(pinBytes);
                device.setPairingConfirmation(true);
            }else {
                try {
                    Log.e(TAG, "Try to set the PIN");
                    Method m = device.getClass().getMethod("setPin", byte[].class);

                    m.invoke(device, pinBytes);
                    Log.e(TAG, "Success to add the PIN.");
                    try {
                        device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                        Log.e(TAG, "Success to setPairingConfirmation.");
                    } catch (Exception e) {
                        Log.e(TAG,"设置配对信息失败");
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"获取setPin方法错误");
                }
            }
        }
    }

}
