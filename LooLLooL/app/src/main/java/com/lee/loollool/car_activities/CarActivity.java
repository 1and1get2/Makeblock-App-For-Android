package com.lee.loollool.car_activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lee.loollool.main_activities.MainActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import activities.loollool.lee.com.loollool.R;

public class CarActivity extends Activity implements View.OnTouchListener, View.OnClickListener {


    private static final int CARAOTO = 0;
    private static final int CARMANUAL = 1;
    private static final int CARTRACK = 2;
    private static final int CARG = 3;
    private static final int CARSHAKE = 4;
    private static final int CARSIGNAL = 5;
    private static final int CARLIGHT = 6;
    private static final int CARSPEED = 7;
    private static final int CARBLOW = 8;
    private static final int WIFISTATEOPEN = 9;
    private static final int WIFISTATECLOSE = 10;

    private boolean modeSwitch1 = false;
    private boolean modeSwitch2 = false;
    private boolean wifistate = false;
    private boolean init_flag = true;


    private IntentFilter intentFilter;
    private WifiReceiver wifiReceiver;
    private Context mcontext;
    private SensorManager sensorManager;
    private Sensor sensor;
    private final String wifiname = "DoitWiFi";
    private final String IP = "192.168.4.1";
    private final int PORT = 9000;

    private ImageView car_img;
    private ImageView car_imageView5;

    int lastX, lastY, originX, originY;
    int lastMotorL, lastMotorR;
    int initTop, initLeft;
    int lastTop, lastLeft;
    long lastTime;
    long sen_lasttime;


    private ImageView car_img_aoto;
    private ImageView car_img_manual;
    private ImageView car_img_track;
    private ImageView car_img_g;
    private ImageView car_img_shake;

    private ImageView car_img_signal;
    private ImageView car_img_light;
    private ImageView car_img_speed;
    private ImageView car_img_blow;

    private TimerView timerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("小车");
        actionBar.setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        initcarview();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, intentFilter);

    }


    private void initcarview() {

        car_img = (ImageView) findViewById(R.id.car_joystick);
        car_imageView5 = (ImageView) findViewById(R.id.car_imageView5);

        car_img_aoto = (ImageView) findViewById(R.id.car_img_auto);
        car_img_manual = (ImageView) findViewById(R.id.car_img_manual);
        car_img_track = (ImageView) findViewById(R.id.car_img_track);
        car_img_g = (ImageView) findViewById(R.id.car_img_G);
        car_img_shake = (ImageView) findViewById(R.id.car_img_shake);

        car_img_signal = (ImageView) findViewById(R.id.car_img_signal);
        car_img_light = (ImageView) findViewById(R.id.car_img_light);
        car_img_speed = (ImageView) findViewById(R.id.car_img_speed);
        car_img_blow = (ImageView) findViewById(R.id.car_img_blow);

        car_img_aoto.setOnClickListener(this);
        car_img_manual.setOnClickListener(this);
        car_img_track.setOnClickListener(this);
        car_img_g.setOnClickListener(this);
        car_img_shake.setOnClickListener(this);
        car_img_signal.setOnClickListener(this);
        car_img_light.setOnClickListener(this);
        car_img_speed.setOnClickListener(this);
        car_img_blow.setOnClickListener(this);

        timerView = (TimerView) findViewById(R.id.timber_view);
        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerView.stop();
            }
        });
        timerView.bringToFront();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(mIntent, 0);
                finish();

        }
        return true;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CARAOTO:

                    break;
                case CARMANUAL:

                    break;
                case CARTRACK:

                    break;
                case CARG:

                    break;
                case CARSHAKE:

                    break;
                case CARSIGNAL:

                    break;
                case CARLIGHT:

                    break;
                case CARSPEED:

                    break;
                case CARBLOW:

                    break;
                case WIFISTATEOPEN:
                    Toast.makeText(mcontext, "WIFI已连接", Toast.LENGTH_SHORT).show();
                    break;
                case WIFISTATECLOSE:
                    Toast.makeText(mcontext, "WIFI未连接", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int xValue = (int) event.values[0];
            int yValue = (int) event.values[1];
            int zValue = (int) event.values[2];
            Log.d("xValue", "xValue=" + String.valueOf(xValue));
            Log.d("yValue", "yValue=" + String.valueOf(yValue));
            Log.d("zValue", "zValue=" + String.valueOf(zValue));
            int motorL1;
            int motorR1;
            byte[] cmdl;
            byte[] cmdr;
            if (modeSwitch1) {
                if (zValue < 2 && zValue > -2) zValue = 0;
                if (yValue < 2 && yValue > -2) yValue = 0;
                motorL1 = zValue * 30 - yValue * 10;
                motorR1 = zValue * 30 + yValue * 10;
                Log.d("motor", String.valueOf(motorL1));
                Log.d("motor", String.valueOf(motorR1));

                if (motorL1 > 255) motorL1 = 255;
                if (motorL1 < -255) motorL1 = -255;
                if (motorR1 > 255) motorR1 = 255;
                if (motorR1 < -255) motorR1 = -255;

                cmdl = buildWriteCar(9, motorL1);
                cmdr = buildWriteCar(10, motorR1);

                long time = System.currentTimeMillis();
                if (time - sen_lasttime > 150) {
                    TCPSend(cmdl);
                    TCPSend(cmdr);
                    sen_lasttime = time;
                }
            }
            if (modeSwitch2) {
                if (xValue > 15 || yValue > 15 || zValue > 15) {
                    motorL1 = 255;
                    motorR1 = 255;
                    cmdl = buildWriteCar(9, motorL1);
                    cmdr = buildWriteCar(10, motorR1);
                    TCPSend(cmdl);
                    TCPSend(cmdr);
                } else {
                    motorL1 = 0;
                    motorR1 = 0;
                    cmdl = buildWriteCar(9, motorL1);
                    cmdr = buildWriteCar(10, motorR1);
                    TCPSend(cmdl);
                    TCPSend(cmdr);
                }

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    // some new changes
    public void TCPSend(final byte[] cmd) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(IP, PORT));
                    OutputStream outputStream = socket.getOutputStream();
                    if (cmd == null) {
                        return;
                    }
                    byte[] data = cmd;
                    outputStream.write(data);
                    outputStream.close();
                    socket.close();
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void sendXY(int x, int y) {
        int dx = -x + 350;
        int dy = -y + 350;
        Log.d("dx", "dx=" + String.valueOf(dx));
        Log.d("dy", "dy=" + String.valueOf(dy));

        int motorL, motorR;

        if (dy > 0) {
            motorL = dy;
            motorR = dy;
            motorL += dx;
            motorR -= dx;
        } else {
            motorL = dy;
            motorR = dy;
            motorL -= dx;
            motorR += dx;
        }

        if (lastMotorL != motorL || lastMotorR != motorR) {
            byte[] cmdl = buildWriteCar(9, motorL);
            byte[] cmdr = buildWriteCar(10, motorR);
            TCPSend(cmdl);
            TCPSend(cmdr);
            lastMotorL = motorL;
            lastMotorR = motorR;
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (init_flag) {
            init_flag = false;
            initTop = car_img.getTop();
            initLeft = car_img.getLeft();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                originX = lastX;
                originY = lastY;
                lastTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                if (left < 0) left = 0;
                if (left > 700) left = 700;
                if (top < 0) top = 0;
                if (top > 700) top = 700;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
                params.gravity = Gravity.LEFT | Gravity.TOP;
                params.topMargin = top;
                params.leftMargin = left;

                Log.d("car", " top =" + String.valueOf(top));
                Log.d("car", "left =" + String.valueOf(left));

                if (top != lastTop || left != lastLeft) {
                    v.setLayoutParams(params);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    long time = System.currentTimeMillis();
                    if ((time - lastTime) > 100) {
                        sendXY(left, top);
                        lastTime = time;
                    }
                    lastTop = top;
                    lastLeft = left;
                }
                break;
            case MotionEvent.ACTION_UP:
                left = 350;//v.getLeft() + dx;
                top = 350;//v.getTop() + dy;
                params = (FrameLayout.LayoutParams) v.getLayoutParams();
                params.gravity = Gravity.LEFT | Gravity.TOP;
                params.topMargin = initTop;
                params.leftMargin = initLeft;
                sendXY(left, top);
                v.setLayoutParams(params);
                break;

        }
        return true;
    }

    public byte[] buildWriteCar(int port, int value) {
        byte[] cmd = new byte[13];
        cmd[0] = (byte) 0xff;
        cmd[1] = (byte) 0x55;
        cmd[2] = (byte) 9;
        cmd[3] = (byte) 0;
        cmd[4] = (byte) 2;
        cmd[5] = (byte) 10;
        cmd[6] = (byte) (port & 0xff);
        final ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort((short) value);
        buf.position(0);
        // Read back bytes
        final byte b1 = buf.get();
        final byte b2 = buf.get();
        cmd[8] = b1;
        cmd[7] = b2;
        cmd[12] = (byte) '\n';
        return cmd;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.car_img_signal) {
            if (wifistate || true) {

                Toast.makeText(this, "当前的wifi可用", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "请链接我们的WIfI网络", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
            return;
        }
        if (wifistate) {
            switch (v.getId()) {
                case R.id.car_img_auto:
                    actionClose();
                    handler.sendEmptyMessage(CARAOTO);
                    car_img_aoto.setImageResource(R.drawable.mbot_auto_press);
                    break;
                case R.id.car_img_manual:
                    actionClose();
                    handler.sendEmptyMessage(CARMANUAL);
                    car_img_manual.setImageResource(R.drawable.mbot_manual_press);
                    car_img.setOnTouchListener(this);

                    break;
                case R.id.car_img_track:
                    actionClose();
                    handler.sendEmptyMessage(CARTRACK);
                    car_img_track.setImageResource(R.drawable.mbot_track_press);

                    break;
                case R.id.car_img_G:
                    actionClose();
                    handler.sendEmptyMessage(CARG);
                    car_img_g.setImageResource(R.drawable.mbot_gsensor_press);
                    modeSwitch1 = true;
                    if (sensorManager == null || sensor == null) {
                        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    }
                    sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

                    break;
                case R.id.car_img_shake:
                    actionClose();
                    handler.sendEmptyMessage(CARSHAKE);
                    car_img_shake.setImageResource(R.drawable.mbot_shake_press);
                    modeSwitch2 = true;
                    if (sensorManager == null || sensor == null) {
                        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    }
                    sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    break;

                case R.id.car_img_light:
                    handler.sendEmptyMessage(CARLIGHT);
                    break;
                case R.id.car_img_speed:
                    handler.sendEmptyMessage(CARSPEED);
                    timerView.toggle();
                    //startActivity(new Intent(this, TimerTaskActivity.class));
                    break;
                case R.id.car_img_blow:
                    handler.sendEmptyMessage(CARBLOW);
                    break;
            }
        } else {

            Toast.makeText(this, "请先连接你的WIfi小车", Toast.LENGTH_SHORT).show();

        }

    }


    public void actionClose() {
        byte[] cmdl;
        byte[] cmdr;
        cmdl = buildWriteCar(9, 0);
        cmdr = buildWriteCar(10, 0);
        TCPSend(cmdl);
        TCPSend(cmdr);
        modeSwitch1 = false;
        modeSwitch2 = false;
        car_img_aoto.setImageResource(R.drawable.mbot_auto);
        car_img_manual.setImageResource(R.drawable.mbot_manual);
        car_img_track.setImageResource(R.drawable.mbot_track);
        car_img_g.setImageResource(R.drawable.mbot_gsensor);
        car_img_shake.setImageResource(R.drawable.mbot_shake);
        car_img.setOnTouchListener(null);

        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }


    }


    class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub
            if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
                //signal strength changed
            } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上与否

                Log.d("wifi", "网络状态改变");
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

                if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {

                    wifistate = false;
                    car_imageView5.setImageResource(R.drawable.mbot_offline);
                    handler.sendEmptyMessage(WIFISTATECLOSE);
                    Toast.makeText(mcontext, "wifi网络连接断开", Toast.LENGTH_SHORT).show();
                    Log.d("wifi", "wifi网络连接断开");

                } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String nowwifiname = wifiInfo.getSSID();

                    if (nowwifiname != null || nowwifiname != "") {

                        if (nowwifiname.contains(wifiname)) {
                            wifistate = true;
                            car_imageView5.setImageResource(R.drawable.mbot_online);
                            handler.sendEmptyMessage(WIFISTATEOPEN);
                        } else {
                            wifistate = false;
                            car_imageView5.setImageResource(R.drawable.mbot_offline);
                            handler.sendEmptyMessage(WIFISTATECLOSE);
                        }
                    } else {

                        Toast.makeText(mcontext, "请检测你的wifi网络", Toast.LENGTH_SHORT).show();

                    }
                    Log.d("wifi", "连接到网络 " + wifiInfo.getSSID());
                }

            } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {//wifi打开与否
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

                if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                    Log.d("wifi", "系统关闭wifi");

                } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                    Log.d("wifi", "系统开启wifi");

                }
            }
        }
    }
}





