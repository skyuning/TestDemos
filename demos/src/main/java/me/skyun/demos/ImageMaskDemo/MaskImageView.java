package me.skyun.demos.ImageMaskDemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.ImageView;

public class MaskImageView extends ImageView {

    private static final int[] PRESSES_STATE = new int[]{android.R.attr.state_pressed};
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public MaskImageView(Context context) {
        this(context, null);
        init();
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaskImageView(Context context, AttributeSet attrs, int defStyle, Paint paint) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!(getDrawable() instanceof BitmapDrawable)) {
            super.onDraw(canvas);
            return;
        }

        int saveFlags = Canvas.MATRIX_SAVE_FLAG
            | Canvas.CLIP_SAVE_FLAG
            | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
            | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
            | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, saveFlags);

        getBackground().draw(canvas);
        BitmapDrawable image = (BitmapDrawable) getDrawable();
        image.getPaint().setXfermode(mXfermode);
        image.draw(canvas);

        canvas.restore();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        BitmapDrawable image = (BitmapDrawable) getDrawable();
        if (image == null || image.getPaint() == null) {
            return;
        }

        if (StateSet.stateSetMatches(PRESSES_STATE, getDrawableState())) {
            image.getPaint().setColorFilter(new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY));
            invalidate();
        } else {
            image.getPaint().setColorFilter(null);
            invalidate();
        }
    }
}
