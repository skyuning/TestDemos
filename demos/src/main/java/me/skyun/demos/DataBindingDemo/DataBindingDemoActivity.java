package me.skyun.demos.DataBindingDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import me.skyun.test.R;

/**
 * Created by linyun on 15-6-21.
 */
public class DataBindingDemoActivity extends FragmentActivity {

    private TextView mTextView = (TextView) findViewById(R.id.text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bindin_demo);
        mTextView.setText("bbb");
    }
}
