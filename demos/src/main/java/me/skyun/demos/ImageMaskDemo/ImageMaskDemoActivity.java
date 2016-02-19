package me.skyun.demos.ImageMaskDemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import me.skyun.test.R;

/**
 * Created by linyun on 15-9-9.
 */
public class ImageMaskDemoActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_mask_demo);
        test2();
    }

    private void test2() {
        View root = findViewById(R.id.root);
        ImageView image = (ImageView) findViewById(R.id.image);
        View leftBubble = findViewById(R.id.left_bubble_layout);
        View rigntBubble = findViewById(R.id.right_bubble_layout);
        final View text = findViewById(R.id.text);

        root.setClickable(true);
        image.setClickable(true);
        leftBubble.setClickable(true);
        rigntBubble.setClickable(true);
        text.setClickable(true);

        DrawableEx.wrapBackground(root);
        DrawableEx.wrapBackground(image);
        DrawableEx.wrapBackground(leftBubble);
        DrawableEx.wrapBackground(rigntBubble);

        ViewStateUtils.makeStates(text);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setEnabled(!text.isEnabled());
            }
        });
    }
}


