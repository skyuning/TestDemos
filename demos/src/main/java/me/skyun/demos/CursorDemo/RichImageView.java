package me.skyun.demos.CursorDemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

/**
 * Created by linyun on 16/4/2.
 */
public class RichImageView extends ImageView {

    private Paint mPaint = new Paint();

    public RichImageView(Context context) {
        super(context);
        init();
    }

    public RichImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    private Runnable mDrawCursor = new Runnable() {
        @Override
        public void run() {
            invalidate(getWidth() - 10, 0, getWidth(), getHeight());
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasFocus()) {
            mPaint.setColor(mPaint.getColor() ^ 0x00FFFFFF);
            canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
            removeCallbacks(mDrawCursor);
            postDelayed(mDrawCursor, 500);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() > getWidth() - 200) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
                requestFocus();
                invalidate(getWidth() - 10, 0, getWidth(), getHeight());
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            ViewGroup parent = (ViewGroup) getParent();
            parent.removeView(this);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}

