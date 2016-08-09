package me.skyun.test.vrscreendemo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.skyun.test.R;
import me.skyun.test.Utils;


public class TexturedRectangleTest extends Activity {
    public GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉activity的标题，全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setRenderer(new SimpleRenderer());
        setContentView(glView);
    }

    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

    class SimpleRenderer implements Renderer {

        FloatBuffer vertices;
        FloatBuffer texture;
        ShortBuffer indices;
        int textureId;

        public SimpleRenderer() {

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertices = byteBuffer.asFloatBuffer();
//            vertices.put( new float[] {  -80f,   -120f,0,1f,
//                                         80f,  -120f, 1f,1f,
//                                         -80f, 120f, 0f,0f,
//                                         80f,120f,   1f,0f});
            vertices.put(new float[]{-80f, -120f,
                    80f, -120f,
                    -80f, 120f,
                    80f, 120f});

            ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(6 * 2);
            indicesBuffer.order(ByteOrder.nativeOrder());
            indices = indicesBuffer.asShortBuffer();
            indices.put(new short[]{0, 1, 2, 1, 2, 3});

            ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
            textureBuffer.order(ByteOrder.nativeOrder());
            texture = textureBuffer.asFloatBuffer();
            texture.put(new float[]{0, 1f,
                    1f, 1f,
                    0f, 0f,
                    1f, 0f});

            indices.position(0);
            vertices.position(0);
            texture.position(0);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d("GLSurfaceViewTest", "surface created");
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
                    + height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            textureId = Utils.loadTexture(getApplicationContext(), R.drawable.screen, gl);
            //定义显示在屏幕上的什么位置(opengl 自动转换)
            gl.glViewport(0, 0, glView.getWidth(), glView.getHeight());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(-160, 160, -240, 240, 1, -1);

            gl.glEnable(GL10.GL_TEXTURE_2D);
            //绑定纹理ID
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);

            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
            // gl.glRotatef(1, 0, 1, 0);
            gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6, GL10.GL_UNSIGNED_SHORT, indices);
//            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 6);
        }
    }
}
