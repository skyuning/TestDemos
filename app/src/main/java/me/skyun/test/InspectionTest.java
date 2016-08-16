package me.skyun.test;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * Created by linyun on 16/8/12.
 */

public class InspectionTest {

    private static String sTimeFormatStr = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sTimeFormat = new SimpleDateFormat(sTimeFormatStr);

    public static int a = 1;

//    The value returned from a stream read should be checked

    public static String getRawString(Context context, int resId) {
        String str = null;
        try {
            InputStream inputStream = context.getResources().openRawResource(resId);
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            str = new String(bytes);
            inputStream.close();
        } catch (IOException e) {
        }
        return str;
    }

    public static int getColor(Context context, String status) {
        boolean a = true || true;
        status += "abc";
        if ("服务中".equals(status) || "待评价".equals(status) || "待确认".equals(status) || "待预约".equals(status) || "未就诊".equals(status) || "未就诊".equals(status) || "就诊中".equals(status) || "待支付".equals(status)) {
            a = true;
        } else if ("已拒绝".equals(status) || "未接通".equals(status) || "已过期".equals(status) || "已回复".equals(status)) {
            a = false;
        } else {
            a = false;
        }
        int b = 1;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        b = 2;
        return 0;
    }
}
