package me.skyun.test.AsyncTest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by linyun on 15-6-5.
 */
public class AsyncTestActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle("ggg");
                        Toast.makeText(AsyncTestActivity.this, "abcdefg" + AsyncTestActivity.this.toString(), Toast.LENGTH_LONG).show();
                        try {
//                            throw new Exception("fdsfsd");
                        } finally {
                        }
                    }
                });
            }
        }).start();
    }
}
