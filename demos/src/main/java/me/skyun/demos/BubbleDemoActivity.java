package me.skyun.demos;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import me.skyun.test.R;


public class BubbleDemoActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_demo);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.bubble_layout);
        relativeLayout.setBackgroundDrawable(new Drawable() {

            private static final int OFFSET = 50;

            Paint whitePaint = new android.graphics.Paint();

            @Override
            public void setColorFilter(ColorFilter cf) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setAlpha(int alpha) {
                // TODO Auto-generated method stub

            }

            @Override
            public int getOpacity() {
                // TODO Auto-generated method stub
                return android.graphics.PixelFormat.OPAQUE;
            }

            @Override
            public void draw(Canvas canvas) {
                Rect r = getBounds();
                RectF rect = new RectF(r);
                rect.inset(OFFSET, OFFSET);

                float anchorX = 50;
                float anchorY = 0;
                float anchorSize = 20;

                //Build a path
                Path anchorPath = new Path();
                anchorPath.moveTo(rect.left + anchorX - anchorSize, rect.top + anchorSize);
                anchorPath.lineTo(rect.left + anchorX, rect.top + anchorY);
                anchorPath.lineTo(rect.left + anchorX + anchorSize, rect.top + anchorSize);
                anchorPath.close();

                RectF roundRectPath = new RectF(rect.left, rect.top + anchorSize, rect.right, rect.bottom);
                anchorPath.addRoundRect(roundRectPath, 20, 20, Path.Direction.CCW);

                whitePaint.setColor(Color.WHITE);
                whitePaint.setStyle(Style.STROKE);
                whitePaint.setStrokeWidth(10);
                canvas.drawPath(anchorPath, whitePaint);

                whitePaint.setStyle(Style.FILL);
                whitePaint.setColor(Color.TRANSPARENT);
                canvas.drawPath(anchorPath, whitePaint);
            }
        });
    }
}