package me.skyun.androidlib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by linyun on 14-11-27.
 */
public class NumberPicker extends View {

    // ui
    private Paint mPaint;
    private int mTextColor = 0xff777c8a;
    private int mDividerHeight = 1;
    private int mDividerColor = 0xffe2e2e2;
    private float mDividerMarginRate = 0.0f;

    // indices
    private int mIndicesCount = 3; // 3个骰子轮流滚动
    private int mIndices[];
    private int mMinValue = 0;
    private int mMaxValue = 1000;
    private int mCurValue = (mMinValue + mMaxValue) / 2;

    // scroll
    private float mCurOffsetY = 0;
    private ScrollListener mScrollListener;

    // fling and adjust
    private int mLastOffsetY = 0;
    private Scroller mFlingScroller;
    private Scroller mAdjustScroller;

    public interface ScrollListener {
        public void onScrollFinished();
    }

    public NumberPicker(Context context) {
        super(context);
        init();
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        initPaint();
        initIndices();
        mFlingScroller = new Scroller(getContext());
        mAdjustScroller = new Scroller(getContext());
    }

    private void initIndices() {
        mIndices = new int[mIndicesCount];
        for (int i = 0; i < mIndices.length; i++) {
            int indices = i - (mIndices.length / 2) + mCurValue;
            indices = getCycleIndics(indices);
            mIndices[i] = indices;
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
        if (mCurValue > mMaxValue)
            mCurValue = mMaxValue;
        initIndices();
        invalidate();
    }

    public void setMinValue(int minValue) {
        mMinValue = minValue;
        if (mCurValue < mMinValue)
            mCurValue = mMinValue;
        initIndices();
        invalidate();
    }

    public void setCurValue(int curValue) {
        mCurValue = curValue;
        scrollIndicesTo(curValue);
    }

    private int getCycleIndics(int indices) {
        if (indices > mMaxValue) {
            return mMinValue + (indices - mMaxValue) % (mMaxValue - mMinValue) - 1;
        } else if (indices < mMinValue) {
            return mMaxValue - (mMinValue - indices) % (mMaxValue - mMinValue) + 1;
        }
        return indices;
    }

    public int getCurValue() {
        return mCurValue;
    }

    private float getItemHeight() {
        return getHeight() / mIndicesCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float itemHeight = getItemHeight();
        int midIdx = (mIndices.length - 1) / 2;

        // draw text
        mPaint.setTextSize(itemHeight * 0.4f);
        float fontOffset = mPaint.getTextSize() * 0.75f / 2;
        float x = getWidth() / 2;
        float y = mCurOffsetY + itemHeight / 2 + fontOffset;
        for (int i = 0; i < mIndices.length; i++) {
//            float rate = (y - fontOffset) / getHeight();
//            float radius = (float) ((rate - 0.5f) * Math.PI);
//            mPaint.setAlpha((int) (255 * Math.cos(radius)));
            if (i == midIdx)
                mPaint.setColor(mTextColor);
            else
                mPaint.setColor(0xffd0d0d3);
            canvas.drawText("" + mIndices[i], x, y, mPaint);
            y += itemHeight;
        }

        // draw divider
        mPaint.setColor(mDividerColor);
        float width = getWidth();
        canvas.drawRect(mDividerMarginRate * width, midIdx * itemHeight - mDividerHeight,
            (1 - mDividerMarginRate) * width, midIdx * itemHeight + mDividerHeight, mPaint);
        midIdx++;
        canvas.drawRect(mDividerMarginRate * width, midIdx * itemHeight - mDividerHeight,
            (1 - mDividerMarginRate) * width, midIdx * itemHeight + mDividerHeight, mPaint);
    }

    private float mLastMoveEventY;
    private VelocityTracker mVelocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveEventY = event.getY();
                if (!mFlingScroller.isFinished() || !mAdjustScroller.isFinished()) {
                    mFlingScroller.forceFinished(true);
                    mAdjustScroller.forceFinished(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                float currentMoveY = event.getY();
                scrollBy(0, (int) (currentMoveY - mLastMoveEventY));
                mLastMoveEventY = currentMoveY;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, 50000);
                fling((int) mVelocityTracker.getYVelocity());
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                return true;
            default:
                return false;
        }

    }

    private void fling(int velocity) {
        mLastOffsetY = 0;
        if (velocity > 0)
            mFlingScroller.fling(0, 0, 0, velocity, 0, 0, 0, Integer.MAX_VALUE);
        else
            mFlingScroller.fling(0, Integer.MAX_VALUE, 0, velocity, 0, 0, 0, Integer.MAX_VALUE);
        invalidate();
    }

    private void adjust() {
        int deltaY = (int) (-mCurOffsetY);
        if (deltaY == 0)
            return;
        mLastOffsetY = 0;
        float itemHeight = getItemHeight();
        if (Math.abs(deltaY) > itemHeight / 2) {
            deltaY += (deltaY > 0) ? -itemHeight : itemHeight;
        }
        mAdjustScroller.startScroll(0, 0, 0, deltaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        Scroller scroller = mFlingScroller;
        if (scroller.isFinished()) {
            scroller = mAdjustScroller;
            if (scroller.isFinished())
                return;
        }

        scroller.computeScrollOffset();
        if (mLastOffsetY == 0)
            mLastOffsetY = scroller.getStartY();
        int curScrollY = scroller.getCurrY();
        scrollBy(0, curScrollY - mLastOffsetY);
        mLastOffsetY = curScrollY;
        if (!scroller.isFinished())
            invalidate();

        if (scroller.isFinished()) {
            if (scroller == mFlingScroller) {
                adjust();
                onScrollerFinished();
            }
        } else {
            invalidate();
        }
    }

    public void onScrollerFinished() {
        if (mScrollListener != null)
            mScrollListener.onScrollFinished();
    }

    @Override
    public void scrollBy(int x, int y) {
        mCurOffsetY += y;
        float itemHeight = getItemHeight();
        while (mCurOffsetY > itemHeight / 2) {
            mCurOffsetY -= itemHeight;
            scrollIndicesBy(-1);
            mCurValue = mIndices[getMidIndex()];
        }
        while (mCurOffsetY < -itemHeight / 2) {
            mCurOffsetY += itemHeight;
            scrollIndicesBy(1);
            mCurValue = mIndices[getMidIndex()];
        }
    }

    private int getMidIndex() {
        return (mIndicesCount - 1) / 2;
    }

    private void scrollIndicesBy(int offset) {
        playSoundEffect(SoundEffectConstants.CLICK);
        for (int i = 0; i < mIndices.length; i++)
            mIndices[i] = getCycleIndics(mIndices[i] + offset);
    }

    private void scrollIndicesTo(int curValue) {
        int midIdx = getMidIndex();
        for (int i = 0; i < mIndices.length; i++) {
            mIndices[i] = mCurValue - midIdx + i;
        }
    }
}

