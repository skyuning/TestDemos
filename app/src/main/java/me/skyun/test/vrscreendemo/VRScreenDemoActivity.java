package me.skyun.test.vrscreendemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.skyun.test.BufferUtil;
import me.skyun.test.R;

public class VRScreenDemoActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor gyroscopeSensor;

    private FrameLayout mDividerView;
    private ImageView mLeftScreen;
    private ImageView mRightScreen;
    private GLSurfaceView mLeftSurfaceView;
    private GLSurfaceView mRightSurfaceView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrscreen_demo);
        mLeftSurfaceView = (GLSurfaceView) findViewById(R.id.surface_left);
        mRightSurfaceView = (GLSurfaceView) findViewById(R.id.surface_right);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screen);

        test3();
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

    private void test3() {
        mLeftSurfaceView.setRenderer(new SimpleRenderer(this));
        mRightSurfaceView.setRenderer(new SimpleRenderer(this));
    }

    private class _Renderer implements GLSurfaceView.Renderer {

        private float w = mBitmap.getWidth() / 200.0f;
        private float h = mBitmap.getHeight() / 200.0f;
        private float[] mSquareVertexArray = {
                -w, -h, 0f,
                w, -h, 0f,
                -w, h, 0f,
                w, h, 0f,
        };
        private FloatBuffer mSquareVertexBuffer = BufferUtil.floatToBuffer(mSquareVertexArray);

        private short[] mIndicesArray = {0, 1, 2, 1, 2, 3};
        private ShortBuffer mIndicesBuffer = BufferUtil.shortToBuffer(mIndicesArray);

        private float[] mTexCoordArray = {0, 1, 1, 1, 0, 0, 1, 0};
        private FloatBuffer mTexCoordBuffer = BufferUtil.floatToBuffer(mTexCoordArray);

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//            gl.glShadeModel(GL10.GL_SMOOTH);
//            gl.glClearColor(0f, 0f, 0f, 0f);
//            gl.glClearDepthf(1.0f);
//            gl.glEnable(GL10.GL_DEPTH_TEST);
//            gl.glDepthFunc(GL10.GL_LEQUAL);
//            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);

//            float ratio = (float) width / height;
//            gl.glMatrixMode(GL10.GL_PROJECTION);
//            gl.glLoadIdentity();
//            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);
//            gl.glMatrixMode(GL10.GL_MODELVIEW);
//            gl.glLoadIdentity();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
//            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//            gl.glLoadIdentity();

            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(-160, 160, -240, 240, 1, -1);
//            gl.glTranslatef(0f, 0.0f, -50f);

            IntBuffer textureId = IntBuffer.allocate(1);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glGenTextures(1, textureId);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId.get());
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mSquareVertexBuffer);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexCoordBuffer);
            gl.glDrawElements(GL10.GL_TRIANGLES, mIndicesArray.length, GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
            gl.glFinish();
        }
    }

    private void test2() {
        mLeftSurfaceView.setRenderer(new GLSurfaceView.Renderer() {

            private float mVertices[] = {
                    -3.0f, -3.0f, 0.0f,  // 1, Bottom Left
                    3.0f, -3.0f, 0.0f,  // 2, Bottom Right
                    -3.0f, 3.0f, 0.0f,  // 0, Top Left
                    3.0f, 3.0f, 0.0f,  // 3, Top Right
            };
            private short[] mIndexes = {
                    0, 1, 2, 1, 2, 3
            };

            private FloatBuffer mVerticesBuffer;
            private FloatBuffer mTextureBuffer;
            private ShortBuffer mIndexBuffer;

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//                gl.glShadeModel(GL10.GL_SMOOTH);
//                gl.glClearColor(0, 0, 0, 0);
//                gl.glClearDepthf(1);
//                gl.glEnable(GL10.GL_DEPTH_TEST);
//                gl.glDepthFunc(GL10.GL_LEQUAL);
                gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//                gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
                mVerticesBuffer = BufferUtil.floatToBuffer(mVertices);
                mIndexBuffer = BufferUtil.shortToBuffer(mIndexes);

                float[] textureCoordinates = {0, 1, 1, 1, 0, 0, 1, 0};
                mTextureBuffer = BufferUtil.floatToBuffer(textureCoordinates);
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                gl.glViewport(0, 0, width, height);
                float ratio = (float) width / height;
                gl.glMatrixMode(GL10.GL_PROJECTION);
                gl.glLoadIdentity();
                gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                gl.glLoadIdentity();

                gl.glTranslatef(0f, 0.0f, -6.0f);
//                gl.glColor4f(1, 0, 0, 1);
//                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer); // OpenGL docs.
//                gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, mVertices.length / 3);
//                gl.glFinish();

                IntBuffer intBuffer = IntBuffer.allocate(1);
                gl.glGenTextures(1, intBuffer);
                int texture = intBuffer.get();

                gl.glEnable(GL10.GL_TEXTURE_2D);
                gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
                GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
                gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
                gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
                gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
                gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
            }
        });
    }

    private static final float NS2S = 1.0f / 1000000000.0f;
    private float[] angle = new float[3];
    private long timestamp;

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
                mDividerView.setPadding(padding, 0, padding, 0);
                mLeftScreen.setPadding(padding, padding, padding, padding);
                mRightScreen.setPadding(padding, padding, padding, padding);
            }

            //将当前时间赋值给timestamp
            timestamp = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
