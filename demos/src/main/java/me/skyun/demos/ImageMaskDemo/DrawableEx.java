package me.skyun.demos.ImageMaskDemo;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;

/**
 * Created by linyun on 15-9-9.
 */
public class DrawableEx extends StateListDrawable {

    /**
     * 用DrawableEx Wrap一个View的background，处理点击变暗的效果
     *
     * @Deprecated use {@link ViewStateUtils#makeStates(View)}
     */
    public static void wrapBackground(View view) {
        view.setBackgroundDrawable(new DrawableEx(view.getBackground()));
    }

    private boolean mUseDisable = false;
    private int mRawColor;

    public DrawableEx(Drawable drawable, boolean useDisable) {
        this(drawable);
        mUseDisable = useDisable;
    }

    public DrawableEx(Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            mRawColor = ((ColorDrawable) drawable).getColor();
        }
        addState(GraphicsConst.STATE_PRESSED, drawable);
        addState(GraphicsConst.STATE_ENABLE, drawable);
        addState(StateSet.WILD_CARD, drawable);
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        // TODO 这里判断pressed的方法有待严格确认
        if (StateSet.stateSetMatches(GraphicsConst.STATE_PRESSED, stateSet)) {
            if (getCurrent() instanceof ColorDrawable) {
                ((ColorDrawable) getCurrent()).setColor(ColorUtils.multiple(mRawColor, 0.5f));
            } else {
                setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            }
        }
        // enabled
        else if (StateSet.stateSetMatches(GraphicsConst.STATE_ENABLE, stateSet)) {
            if (getCurrent() instanceof ColorDrawable) {
                ((ColorDrawable) getCurrent()).setColor(mRawColor);
            } else {
                setColorFilter(null);
            }
        }
        // disabled
        else if (mUseDisable) {
            if (getCurrent() instanceof ColorDrawable) {
                ((ColorDrawable) getCurrent()).setColor(Color.LTGRAY);
            } else {
                //创建颜色变换矩阵
                ColorMatrix mColorMatrix = new ColorMatrix();
                //设置灰度影响范围
                mColorMatrix.setSaturation(0);
                //创建颜色过滤矩阵
                ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
                //设置画笔的颜色过滤矩阵
                setColorFilter(mColorFilter);
//                setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);
            }
        }
        // wildcard
        else {
            if (getCurrent() instanceof ColorDrawable) {
                ((ColorDrawable) getCurrent()).setColor(mRawColor);
            } else {
                setColorFilter(null);
            }
        }
        return super.onStateChange(stateSet);
    }
}

