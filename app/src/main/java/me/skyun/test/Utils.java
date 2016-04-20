package me.skyun.test;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by linyun on 16/3/31.
 */
public class Utils {
    public static void choosePhoto(Activity activity, int token) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent, token);
    }
}
