package me.skyun.demos.ImageSpanDemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.skyun.test.R;
import me.skyun.test.Utils;

public class ImageSpanDemoActivity extends FragmentActivity {

    private CYRichEditText mEditText;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_span_demo);

        mEditText = (CYRichEditText) findViewById(R.id.edit_text);
        mEditText.setMovementMethod(LinkMovementMethod.getInstance());
        mEditText.setShowSoftInputOnFocus(false);
        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setAutoLinkMask(Linkify.WEB_URLS);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String url = "http://oimagea5.ydstatic.com/image?id=1439989378077022073&product=adpublish&w=250&h=250";
                String url2 = "https://s1.2mdn.net/viewad/4157372/2-stock-roi-10free-vector-v1-728x90-na_R1.jpg";
                String url3 = "http://www.jcodecraeer.com/uploads/20141106/63881415275800.png";
                String html = "<a href=\"http://www.baidu.com\">他妈的跳转去百度</a><br>" +
                        "fasfsdfasdfdsa<br>";
                for (int i = 0; i < 100; i++) {
//                    html += String.format("<img src='%s'>", url3);
                    html += "" + i + "<br>";
                }
                mEditText.setHtml(html);
                mTextView.setText("http://www.baidu.com");
            }
        }, 100);
        ImageView addImageView = (ImageView) findViewById(R.id.add_image);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.choosePhoto(ImageSpanDemoActivity.this, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            mEditText.insertImage(data.getData());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
