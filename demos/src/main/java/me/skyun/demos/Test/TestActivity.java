package me.skyun.demos.Test;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.wasabeef.richeditor.RichEditor;
import me.chunyu.g7json.JSONableObject;
import me.chunyu.g7json.annotation.JSONDict;
import me.skyun.test.R;
import me.skyun.androidlib.utils.IntentEx;
import uk.co.senab.photoview.PhotoViewAttacher;

//import me.chunyu.g7anno.G7Anno;
//import me.chunyu.g7anno.annotation.BroadcastResponder;
//import me.chunyu.g7anno.processor.ActivityProcessor;

//import me.skyun.processor.PrintMe;

/**
 * Created by linyun on 15-5-26.
 */
public class TestActivity extends FragmentActivity {

    public static final String ACTION_TEST_1 = "action.test.1";
    private static TestActivity sInstance;
    private TestDialogFragment mDialog;
    private FragmentManager mFm;

    private String ABC = "xxxxxxxxxxxxxxxxxxxxxxx";

    protected ImageView mImageView;
    protected ImageView mImageView2;
    protected TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hello);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mImageView2 = (ImageView) findViewById(R.id.image_view_2);
        mTextView = (TextView) findViewById(R.id.hello);
        PhotoViewAttacher attacher = new PhotoViewAttacher(mImageView);
        attacher.setZoomable(true);
        attacher.setMaximumScale(30);
        PhotoViewAttacher attacher2 = new PhotoViewAttacher(mImageView2);
        attacher2.setZoomable(true);
        attacher2.setMaximumScale(30);

        testBitmapCompress(null);
    }

    private void testBitmapCompress(Uri uri) {
//        if (uri == null) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");
//            startActivityForResult(intent, 101);
//            return;
//        }

        String text = "";
        mImageView.setImageResource(R.drawable.fj);
        Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        text += bitmap.getByteCount();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.fj, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, boas);
        options.inSampleSize = 2;
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(boas.toByteArray(), 0, boas.size(), options);
        mImageView.setImageBitmap(bitmap2);
        text += " : " + bitmap2.getByteCount();
        mTextView.setText(text);
    }

    private RichEditor mEditor;

    private void testRichEditor() {
        mEditor = (RichEditor) findViewById(R.id.rich_editor);
        mEditor.setEditorWidth(getWindowManager().getDefaultDisplay().getWidth());
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        mEditor.setEditorBackgroundColor(Color.BLUE);
//        mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");
//        mEditor.insertImage("https://raw.githubusercontent.com/wasabeef/art/master/twitter.png", "twitter");

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                testMultiSelGallery();
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(mEditor.getHtml());

                List<String> imageUrl = new ArrayList<>();
                Pattern pattern = Pattern.compile("<img.*?src=\"(.*?)\".*?>");
                Matcher matcher = pattern.matcher(mEditor.getHtml());
                while (matcher.find()) {
                    imageUrl.add(matcher.group());
                }

                String modifiedHtml = matcher.replaceAll("<br>");
                mTextView.setText(Html.fromHtml(modifiedHtml).toString());

//                Toast.makeText(TestActivity.this, imageUrl.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String alt = "image\" style=\"max-width:100%;max-height:200px;";
        if (data == null) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if (data.getData() != null) {
            testBitmapCompress(data.getData());
//            mEditor.insertImage(data.getDataString(), alt);
        } else if (Build.VERSION.SDK_INT >= 16 && data.getClipData() != null) {
            ClipData clipData = data.getClipData();
            int count = clipData.getItemCount();
            for (int i = 0; i < count; i++) {
                mEditor.insertImage(clipData.getItemAt(i).getUri().toString(), alt);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void testMultiSelGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    private void testFragmentLifeCycle() {
        final Fragment fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction()
                .add(fragment, "aaaaa").commit();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .detach(fragment).commit();
            }
        });
    }

    public static class OuterJson extends JSONableObject {
        @JSONDict(key = "inner_json")
        public List<InnerJson> innerJson;
    }

    public static class InnerJson extends JSONableObject {
        @JSONDict(key = "var1")
        public String var1;

        @JSONDict(key = "var2")
        public int var2;

        @Override
        public Object fromJSONString(String jsonString) {
            return super.fromJSONString(jsonString);
        }

        @Override
        public Object fromJSONObject(JSONObject jsonObj) {
            return super.fromJSONObject(jsonObj);
        }
    }

    private void testJson() {
        String json = "{\"inner_json\": [" +
                "{\"var1\": \"hello\", \"var2\": 111111111}" +
                ",{\"var1\": \"world\", \"var2\": 222222222}" +
                "]}";
        OuterJson outerJson = null;
        outerJson = new OuterJson();
        outerJson.fromJSONString(json);
        System.out.println(outerJson);
    }

    private void someTest() {
        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "clickable", Toast.LENGTH_SHORT).show();
            }
        });

        final View contentView = findViewById(android.R.id.content);

        mDialog = new TestDialogFragment();
        mFm = getSupportFragmentManager();
        final FragmentTransaction ft = mFm.beginTransaction();
//        ft.add(R.id.container, mDialog, "").commit();

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

    private void testBroadcast() {
//        ActivityProcessor processor = (ActivityProcessor) G7Anno.adaptProcessor(getClass());
//        processor.generalProcess(this, getIntent().getExtras());
//        mBroadcastReceiver = processor.createBroadcastReceiver(this);

        final Fragment fragment = new TestFragment();

        findViewById(R.id.image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.root, fragment, TestFragment.class.getName()).commit();
            }
        });
        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentEx().setAction(Uri.class)
                        .setData(Uri.parse("http://www.chunyu.me"))
                        .putObjectExtras("演示新broadcast---------------------")
                        .localBroadcast(v.getContext());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    private BroadcastReceiver mBroadcastReceiver;

    public static class DateEvent implements Serializable {
        public Date date;
        public String msg;

        public DateEvent(Date date, String msg) {
            this.date = date;
            this.msg = msg;
        }
    }

    //    @BroadcastResponder(actionTypes = Uri.class)
    protected void onActionTest1(Uri uri, String msg) {
        Toast.makeText(TestActivity.this, uri.toString() + "_" + msg, Toast.LENGTH_SHORT).show();
    }
}

