package me.skyun.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by linyun on 15-9-8.
 */
public class FloatWindow {

    private WindowManager mWindowManager;
    private ContainerView mContainerView;
    private View mContentView;

    public FloatWindow(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mContainerView = new ContainerView(context);
        mContainerView.setLayoutTransition(new LayoutTransition());
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    public void showAsDropDown(View anchor) {
        WindowManager.LayoutParams params = createParams(anchor.getWindowToken());
        int anchorLocation[] = new int[2];
        anchor.getLocationInWindow(anchorLocation);
        params.x = anchorLocation[0];
        params.y = anchorLocation[1] + anchor.getHeight();
        mContainerView.addView(mContentView);
        mWindowManager.addView(mContainerView, params);
    }

    private WindowManager.LayoutParams createParams(IBinder token) {
        // generates the layout parameters for the drop down
        // we want a fixed size view located at the bottom left of the anchor
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        // these gravity settings put the view at the top left corner of the
        // screen. The view is then positioned to the appropriate location
        // by setting the x and y offsets to match the anchor's bottom
        // left corner
        p.gravity = Gravity.START | Gravity.TOP;
        p.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        p.token = token;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.setTitle("PopupWindow:" + Integer.toHexString(hashCode()));

        return p;
    }

    private void dismiss() {
        mWindowManager.removeView(mContainerView);
    }

    private class ContainerView extends FrameLayout {
        public ContainerView(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                dismiss();
                return true;
            } else {
                return super.dispatchKeyEvent(event);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if ((event.getAction() == MotionEvent.ACTION_DOWN)
                && ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight()))) {
                dismiss();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                dismiss();
                return true;
            } else {
                return super.onTouchEvent(event);
            }
        }
    }
}
