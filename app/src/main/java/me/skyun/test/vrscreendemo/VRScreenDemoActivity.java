package me.skyun.test.vrscreendemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import me.skyun.test.R;

public class VRScreenDemoActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor gyroscopeSensor;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float[] angle = new float[3];
    private long timestamp;

    private FrameLayout mDividerView;
    private GLSurfaceView mLeftSurfaceView;
    private GLSurfaceView mRightSurfaceView;
    private MyGLRender mLeftRender;
    private MyGLRender mRightRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrscreen_demo);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        mLeftSurfaceView = (GLSurfaceView) findViewById(R.id.surface_left);
        mRightSurfaceView = (GLSurfaceView) findViewById(R.id.surface_right);

        test();

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftRender.resetLookAt();
                mRightRender.resetLookAt();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLeftSurfaceView.onResume();
        mRightSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLeftSurfaceView.onPause();
        mRightSurfaceView.onPause();
    }

    private void test() {
        mLeftRender = new MyGLRender(this);
        mRightRender = new MyGLRender(this);
        mLeftSurfaceView.setRenderer(mLeftRender);
        mRightSurfaceView.setRenderer(mRightRender);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            //从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
            if (timestamp != 0) {

                // 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
                final float dT = (event.timestamp - timestamp) * NS2S;

                // 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
                angle[0] += event.values[0] * dT;
                angle[1] += event.values[1] * dT;
                angle[2] += event.values[2] * dT;

                // 将弧度转化为角度
                float anglex = (float) Math.toDegrees(angle[0]);
                float angley = (float) Math.toDegrees(angle[1]);
                float anglez = (float) Math.toDegrees(angle[2]);

                System.out.println("anglex------------>" + anglex);
                System.out.println("angley------------>" + angley);
                System.out.println("anglez------------>" + anglez);
                System.out.println("gyroscopeSensor.getMinDelay()----------->" + gyroscopeSensor.getMinDelay());

                int padding = (int) angley * 5;
//                mDividerView.setPadding(padding, 0, padding, 0);
                mLeftSurfaceView.setPadding(padding, padding, padding, padding);
                mRightSurfaceView.setPadding(padding, padding, padding, padding);


                anglex = (float) Math.toDegrees(event.values[0] * dT);
                angley = (float) Math.toDegrees(event.values[1] * dT);
                anglez = (float) Math.toDegrees(event.values[2] * dT);
                mLeftRender.rotate(new float[]{anglex, angley, anglez});
                mRightRender.rotate(new float[]{anglex, angley, anglez});
            }

            //将当前时间赋值给timestamp
            timestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
