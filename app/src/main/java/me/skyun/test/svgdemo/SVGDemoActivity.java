package me.skyun.test.svgdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import me.skyun.test.R;

public class SVGDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svgdemo);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(R.drawable.svg_f);
//        VectorDrawable drawable = (VectorDrawable) VectorDrawable.createFromPath("fdas");
//        drawable.setCallback(null);
    }
}
