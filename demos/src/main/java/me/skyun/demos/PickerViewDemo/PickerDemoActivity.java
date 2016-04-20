package me.skyun.demos.PickerViewDemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import me.skyun.test.R;

public class PickerDemoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_demo);

        final ArrayPicker picker1 = (ArrayPicker) findViewById(R.id.array_picker);
        final ArrayPicker picker2 = (ArrayPicker) findViewById(R.id.array_picker_2);
        final String[] data = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        picker1.setData(data);
        picker1.setScrollListener(new ArrayPicker.ScrollListener() {
            @Override
            public void onScrollFinished() {
                String text1 = (String) picker1.getCurValue();
                String[] data2 = new String[10];
                for (int i = 0; i < data2.length; i++) {
                    data2[i] = text1 + "123";
                }
                picker2.setData(data2);
            }
        });
    }
}
