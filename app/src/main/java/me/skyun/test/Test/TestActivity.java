package me.skyun.test.Test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import me.skyun.test.R;

/**
 * Created by linyun on 15-5-26.
 */
public class TestActivity extends FragmentActivity {

    private TestDialogFragment mDialog;
    private FragmentManager mFm;

    private String ABC = "xxxxxxxxxxxxxxxxxxxxxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hello);
        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "clickable", Toast.LENGTH_SHORT).show();
            }
        });

//        final View contentView = findViewById(android.R.id.content);
//
//        mDialog = new TestDialogFragment();
//        mFm = getSupportFragmentManager();
//        final FragmentTransaction ft = mFm.beginTransaction();
//        ft.add(R.id.container, mDialog, "").commit();
//
//        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(TestActivity.this, ABC, Toast.LENGTH_SHORT).show();
//                FloatWindow floatWindow = new FloatWindow(TestActivity.this);
//                View vv = getLayoutInflater().inflate(R.layout.dialog_fragment_test, null);
//                floatWindow.setContentView(vv);
//                floatWindow.showAsDropDown(v);
//
//                View view = mDialog.getView();
//                if (view == null)
//                    return;
//
//                view.layout(0, 0, 100, 100);
//                contentView.postInvalidate();
//            }
//        });
    }
}

