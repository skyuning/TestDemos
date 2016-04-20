package me.skyun.demos.ImageSpanDemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by linyun on 16/4/1.
 */
public class CYRichEditText extends EditText {

    private Scroller mFlingScroller;
    private int mFlingSoundOffset;
    private Scroller mOverscroller;
    private VelocityTracker mVelocityTracker;
    private float mLastMoveMentY;

    public CYRichEditText(Context context) {
        super(context);
        init();
    }

    public CYRichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CYRichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPadding(20, 0, 20, 0);
        mFlingScroller = new Scroller(getContext());
        mOverscroller = new Scroller(getContext());
    }

    public void setHtml(String html) {
        setText(Html.fromHtml(html));
        initImageSpans();
    }

    private void initImageSpans() {
        Editable editable = getText();
        ImageSpan[] imageSpens = editable.getSpans(0, editable.length(), ImageSpan.class);
        for (ImageSpan oldSpan : imageSpens) {
            CYImageSpan newSpan = new CYImageSpan(this, oldSpan.getSource());
            replaceSpan(editable, oldSpan, newSpan);
        }
    }

    public void replaceSpan(Editable editable, ImageSpan oldSpan, ImageSpan newSpan) {
        int spanStart = editable.getSpanStart(oldSpan);
        int spanEnd = editable.getSpanEnd(oldSpan);
        editable.removeSpan(oldSpan);
        editable.setSpan(newSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(widget.getContext(), "a clickable span", Toast.LENGTH_SHORT).show();
            }
        };
        editable.setSpan(clickableSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void insertImage(Uri uri) {
        Editable editable = getText();
        int cursorPos = getSelectionStart();

        String imageTag = "\uFFFC";
        editable.insert(cursorPos, imageTag);

        CYImageSpan imageSpan = new CYImageSpan(this, uri);
        editable.setSpan(imageSpan, cursorPos, cursorPos + imageTag.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        int curScrollY = getScrollY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveMentY = event.getY();
                if (!mFlingScroller.isFinished()) {
                    mFlingScroller.forceFinished(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float curY;
                int pointerCnt = event.getPointerCount();
                if (pointerCnt > 1) {
                    curY = event.getY(pointerCnt - 1);
                } else {
                    curY = event.getY();
                    float deltaY = mLastMoveMentY - curY;
                    if (curScrollY < 0) {
                        scrollBy(0, (int) deltaY / 3);
                    } else {
                        scrollBy(0, (int) deltaY);
                    }
                    invalidate();
                }
                mLastMoveMentY = curY;
                break;
            case MotionEvent.ACTION_UP:
                mFlingScroller.setFriction(ViewConfiguration.getScrollFriction());
                if (curScrollY > 0) {
                    mVelocityTracker.computeCurrentVelocity(1000, 30000);
                    int yVelocity = (int) mVelocityTracker.getYVelocity();
                    if (yVelocity != 0) {
                        mFlingScroller.fling(
                                0, getScrollY(), 0, -yVelocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                        mFlingSoundOffset = 0;
                        Log.d("fling", "start: " + getScrollY());
                    }
                } else {
                    mFlingScroller.fling(
                            0, curScrollY, 0, -curScrollY * 10 + 500, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                    invalidate();
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (!mFlingScroller.isFinished()) {
            mFlingScroller.computeScrollOffset();
            int scrollToY = mFlingScroller.getCurrY();
            float velocity = mFlingScroller.getCurrVelocity();
            // 从上面的overscrolll回滚时
            if (getScrollY() < 0 && scrollToY > 0) {
                scrollTo(0, 0);
                mFlingScroller.forceFinished(true);
                return;
            }
            // fling出上面overscroll区域时
            else if (getScrollY() > 0 && scrollToY < 0) {
                scrollTo(0, 0);
                mFlingScroller.forceFinished(true);
                mFlingScroller.setFriction(ViewConfiguration.getScrollFriction() * 50);
                mFlingScroller.fling(0, 0, 0, (int) -velocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                invalidate();
                return;
            }
            scrollTo(0, scrollToY);
            invalidate();
            Log.d("fling", "flinging: " + scrollToY + "  " + (scrollToY - getScrollY()));
            mFlingSoundOffset += Math.abs(scrollToY - getScrollY());
            while (mFlingSoundOffset > 200) {
                mFlingSoundOffset -= 200;
                playSoundEffect(SoundEffectConstants.CLICK);
            }
        } else if (getScrollY() < 0) {
            mFlingScroller.fling(
                    0, getScrollY(), 0, -getScrollY()* 10 + 500, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            invalidate();
        }
    }

//    @Override
//    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
//            int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//        This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
//
//        int newDeltaY = deltaY;
//        int delta = (int) (deltaY * 0.5);
//        if (delta != 0) {
//            newDeltaY = delta;
//        }
//        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
//                scrollRangeX, scrollRangeY, maxOverScrollX, 500, isTouchEvent);
//    }

    @Override
    public int getOffsetForPosition(float x, float y) {
        int pos = super.getOffsetForPosition(x, y);
        ImageSpan[] spans = getText().getSpans(pos, pos, ImageSpan.class);
        if (spans.length > 0) {
            Drawable drawable = spans[0].getDrawable();
        }
        return pos;
    }

    /**
     * Input Connection
     *
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new _InputConnection(super.onCreateInputConnection(outAttrs), false);
    }

    private class _InputConnection extends InputConnectionWrapper {

        public _InputConnection(android.view.inputmethod.InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            int sel = getSelectionStart();
            ImageSpan[] imageSpens = getText().getSpans(sel, sel, ImageSpan.class);
            if (imageSpens.length > 0) {
                return false;
            }
            return super.commitText(text, newCursorPosition);
        }

//        @Override
//        public boolean sendKeyEvent(KeyEvent event) {
//            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                int sel = getSelectionStart();
//                ImageSpan[] imageSpens = getText().getSpans(sel, sel, ImageSpan.class);
//                if (imageSpens.length > 0) {
//                    if (sel == getText().getSpanStart(imageSpens[0])) {
//                        getText().insert(sel, "\n");
//                        CYRichEditText.this.setSelection(sel);
//                        return false;
//                    }
//                }
//            }
//            return super.sendKeyEvent(event);
//        }
    }
}

