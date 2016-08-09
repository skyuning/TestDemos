package me.skyun.test.mapdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.skyun.test.R;
import me.skyun.test.scrolldemo.MapView;

public class MapDemoActivity extends AppCompatActivity {

    private MapView mFloorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);

        mFloorView = (MapView) findViewById(R.id.map_view_floor);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloorView.requestLayout();
            }
        });
    }
}
