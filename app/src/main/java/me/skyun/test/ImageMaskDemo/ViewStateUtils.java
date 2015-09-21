package me.skyun.test.ImageMaskDemo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.StateSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by linyun on 15-9-10.
 */
public class ViewStateUtils {

    public static void makeStates(View view) {
        if (view.getBackground() != null)
            view.setBackgroundDrawable(new DrawableEx(view.getBackground()));

        if (view instanceof TextView) {
            TextView tv = (TextView) view;

            // make text states
            int normalColor = tv.getCurrentTextColor();
            int[][] states = new int[][]{GraphicsConst.STATE_PRESSED, GraphicsConst.STATE_ENABLE, StateSet.WILD_CARD};
            int[] colors = new int[]{ColorUtils.multiple(normalColor, 0.5f), normalColor, Color.LTGRAY};
            ColorStateList colorStateList = new ColorStateList(states, colors);
            tv.setTextColor(colorStateList);

            // make compound drawables states
            Drawable[] drawables = tv.getCompoundDrawables();
            for (int i = 0; i < drawables.length; i++) {
                if (drawables[i] != null) {
                    drawables[i] = new DrawableEx(drawables[i], true);
                }
            }
            tv.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }
}
