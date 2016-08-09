package me.skyun.test.gyrodemo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by linyun on 16/8/1.
 */
public class View3D extends View {

    private Matrix mMatrix = new Matrix();
    private Drawable mDrawable;
    Camera mCamera = new Camera();

    @Override
    public Matrix getMatrix() {
        return mMatrix;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
    }

    public View3D(Context context) {
        super(context);
    }

    public View3D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public View3D(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable == null) {
            return;
        }
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth() * 2, mDrawable.getIntrinsicHeight() * 2);

        mCamera.save();
        mCamera.translate(-mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight(), 8 * 72);
        mCamera.rotateX(-30);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        canvas.save();
        mMatrix.postTranslate(mDrawable.getIntrinsicWidth() / 2, mDrawable.getIntrinsicHeight() / 2);
        canvas.concat(mMatrix);
        mDrawable.draw(canvas);
        canvas.restore();
    }
}
