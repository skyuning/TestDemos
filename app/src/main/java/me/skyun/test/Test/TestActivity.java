package me.skyun.test.Test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import me.skyun.test.R;
import me.skyun.widget.FloatWindow;

/**
 * Created by linyun on 15-5-26.
 */
public class TestActivity extends FragmentActivity {

    private TestDialogFragment mDialog;
    private FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final View contentView = findViewById(android.R.id.content);

        mDialog = new TestDialogFragment();
        mFm = getSupportFragmentManager();
        final FragmentTransaction ft = mFm.beginTransaction();
        ft.add(R.id.container, mDialog, "").commit();

        findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindow floatWindow = new FloatWindow(TestActivity.this);
                View vv = getLayoutInflater().inflate(R.layout.dialog_fragment_test, null);
                floatWindow.setContentView(vv);
                floatWindow.showAsDropDown(v);

                View view = mDialog.getView();
                if (view == null)
                    return;

                view.layout(0, 0, 100, 100);
                contentView.postInvalidate();
            }
        });
    }
}

