package me.skyun.demos.ImageSpanDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import me.skyun.test.R;

/**
 * Created by linyun on 16/3/31.
 */
public class CYImageSpan extends ImageSpan implements Target {

    private Context mContext;
    private boolean mIsRemote = true;
    private Uri mLocalUri;
    private String mRemoteUrl = "";
    private WeakReference<Drawable> mDrawableRef;
    private CYRichEditText mEditText;

    private int mMaxHeight = 1024;
    private int mMaxWidth;
    private int mHorizonMargin = 10;

    public CYImageSpan(CYRichEditText editText, String remoteUrl) {
        this(editText, (Drawable) null);
        mRemoteUrl = remoteUrl;
        mIsRemote = true;
    }

    public CYImageSpan(CYRichEditText editText, Uri localUri) {
        this(editText, (Drawable) null);
        mLocalUri = localUri;
        mIsRemote = false;
    }

    private CYImageSpan(CYRichEditText editText, Drawable drawable) {
        super(drawable);
        mContext = editText.getContext().getApplicationContext();
        mEditText = editText;
        mDrawableRef = new WeakReference<>(drawable);
        mMaxWidth = mEditText.getWidth() - mEditText.getPaddingLeft() - mEditText.getPaddingRight()
            - 2 * mHorizonMargin;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();

        // 缩放
        if (rect.bottom > mMaxHeight) {
            rect.right = rect.right * mMaxHeight / rect.bottom;
            rect.bottom = mMaxHeight;
            d.setBounds(rect);
        }
        if (rect.right > mMaxWidth) {
            rect.right = mMaxWidth;
            rect.bottom = rect.right * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            d.setBounds(rect);
        }

        // baseline的坐标为0，往上依次是ascent，top，向下依次是，descent，bottom
        if (fm != null) {
            fm.ascent = -rect.bottom;
            fm.top = fm.ascent - 20;
            fm.descent = 0;
            fm.bottom = fm.descent + 20;
        }
        return mMaxWidth + 2 * mHorizonMargin;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        Rect bounds = b.getBounds();
        canvas.save();

        int transX = mHorizonMargin + (mMaxWidth - bounds.right) / 2;

        int transY = bottom - bounds.bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= paint.getFontMetricsInt().descent;
        }

        canvas.translate(transX, transY);
        b.draw(canvas);
        if (!mIsRemote && TextUtils.isEmpty(mRemoteUrl)) {
            paint.setColor(0xaa222222);
            canvas.drawRect(bounds, paint);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            String msg = "正在上传";
            canvas.drawText(msg, bounds.centerX(), bounds.centerY(), paint);
        }
        canvas.restore();
    }

    @Override
    public Drawable getDrawable() {
        Drawable d = null;
        if (mDrawableRef != null) {
            d = mDrawableRef.get();
            if (d != null) {
                return d;
            }
        }

        // 远程图片
        if (mIsRemote && mRemoteUrl != null && mRemoteUrl.startsWith("http")) {
            d = mContext.getResources().getDrawable(R.drawable.default_image);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mDrawableRef = new WeakReference<>(d);
            Picasso.with(mContext).load(Uri.parse(mRemoteUrl)).into(this);
        }
        // 本地图片
        else if (!mIsRemote && mLocalUri != null && mLocalUri.toString().startsWith("content")) {
            d = getLocalDrawable();
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mDrawableRef = new WeakReference<>(d);
            if (mRemoteUrl == null) { // 还未上传
//                UploadCallback callback = new UploadCallback() {
//                    @Override
//                    public void onUploadReturn(int i, int i1, Uri localUri, String remoteUrl, Exception e) {
//                        mRemoteUrl = remoteUrl;
//                    }
//                };
//                CYFileUploader.upload(mContext, CYFileUploader.TYPE_IMAGE, callback, mLocalUri);
            }
        } else {
            throw new IllegalArgumentException("no illegal image source");
        }
        return d;
    }

    public Drawable getLocalDrawable() {
        try {

            // decode bounds
            InputStream is = mContext.getContentResolver().openInputStream(mLocalUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            is.close();

            // calc sample size
            int horizonSampleSize = (int) Math.ceil((double) options.outWidth / mMaxWidth);
            int verticalSampleSize = (int) Math.ceil((double) options.outHeight / mMaxHeight);
            options.inSampleSize = Math.max(horizonSampleSize, verticalSampleSize);

            options.inJustDecodeBounds = false;
            is = mContext.getContentResolver().openInputStream(mLocalUri);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            Drawable d = new BitmapDrawable(mContext.getResources(), bitmap);
            return d;
        } catch (Exception e) {
            Log.e("sms", "Failed to loaded content " + mLocalUri, e);
            return mContext.getResources().getDrawable(R.drawable.default_error_image);
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Drawable d = new BitmapDrawable(mContext.getResources(), bitmap);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        mDrawableRef = new WeakReference<>(d);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mEditText.replaceSpan(mEditText.getText(), CYImageSpan.this, CYImageSpan.this);
            }
        });
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    public String getSource() {
        return mRemoteUrl;
    }
}
