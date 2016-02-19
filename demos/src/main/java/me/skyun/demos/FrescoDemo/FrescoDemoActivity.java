package me.skyun.demos.FrescoDemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import me.skyun.test.R;

/**
 * Created by linyun on 15-6-2.
 */
public class FrescoDemoActivity extends FragmentActivity {

    private SimpleDraweeView mSimpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_demo);
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.simple_drawee_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showPic();
    }

    private void showPic() {
        Uri uri = Uri.parse("https://raw.githubusercontent.com/liaohuqiu/fresco-docs-cn/docs/static/fresco-logo.png");
        mSimpleDraweeView.setImageURI(uri);
    }

    private void showGif() {
        String url = "http://img0.bdstatic.com/img/image/imglogo-r.png";
//        String url = "http://img4.imgtn.bdimg.com/it/u=4274835636,2877046532&fm=21&gp=0.jpg"
        Uri uri = Uri.parse(url);
        mSimpleDraweeView.setImageURI(uri);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setAutoPlayAnimations(true)
            .build();
        mSimpleDraweeView.setController(controller);
    }
}
