package me.skyun.test.vrscreendemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.skyun.test.BufferUtil;
import me.skyun.test.R;

public class MyGLRender implements Renderer {

    private Context mContext;
    private Bitmap mBitmap;

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

    public MyGLRender(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
//        gl.glClearColor(0, 0, 0, 0);
//        gl.glClearDepthf(1);
//        gl.glEnable(GL10.GL_DEPTH_TEST);
//        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        mVerticesBuffer = BufferUtil.floatToBuffer(mVertices);
        mIndexBuffer = BufferUtil.shortToBuffer(mIndexes);

        float[] textureCoordinates = {0, 1, 1, 1, 0, 0, 1, 0};
        mTextureBuffer = BufferUtil.floatToBuffer(textureCoordinates);

        initTexture(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        // TODO Auto-generated method stub
        gl.glViewport(0, 0, w, h);

        float ratio = (float) w / h;
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
//        gl.glColor4f(1, 0, 0, 1);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer); // OpenGL docs.
//        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, mVertices.length / 3);
//        gl.glFinish();

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, mIndexBuffer);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
    }

    private void initTexture(GL10 gl) {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGenTextures(1, intBuffer);
        int texture = intBuffer.get();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.fj);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
    }
}