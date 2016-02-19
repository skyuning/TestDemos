package me.skyun.demos.ImageMaskDemo;

import android.graphics.Color;

/**
 * Created by linyun on 15-9-10.
 */
public class ColorUtils {
    public static int multiple(int color, float rate) {
        int newRed = (int) (Color.red(color) * rate);
        int newGreen = (int) (Color.green(color) * rate);
        int newBlue = (int) (Color.blue(color) * rate);
        int newColor = Color.rgb(newRed, newGreen, newBlue);
        return newColor;
    }
}
