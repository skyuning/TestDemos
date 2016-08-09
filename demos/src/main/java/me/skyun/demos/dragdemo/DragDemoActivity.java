package me.skyun.demos.dragdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import me.skyun.test.R;

public class DragDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_demo);


        final ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnTouchListener(new View.OnTouchListener() {

            private int mLastX;
            private int mLastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = (int) event.getRawX();
                        mLastY = (int) event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - mLastX;
                        int dy = (int) event.getRawY() - mLastY;
                        imageView.layout(
                                imageView.getLeft() + dx,
                                imageView.getTop() + dy,
                                imageView.getRight() + dx,
                                imageView.getBottom() + dy);
                        mLastX += dx;
                        mLastY += dy;
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.requestLayout();
            }
        });
    }


}
