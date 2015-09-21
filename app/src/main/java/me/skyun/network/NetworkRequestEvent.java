package me.skyun.network;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by linyun on 15-6-15.
 */
public class NetworkRequestEvent {

    private INetworkRequest mRequest;

    private Context mContext;
    private String mMessage;
    private Dialog mDialog;
    private boolean mShowProgressDialog;

    public NetworkRequestEvent(Context context, INetworkRequest request) {
        this.mRequest = request;
        mContext = context;
        mMessage = "sending request";
    }

    public INetworkRequest getRequest() {
        return mRequest;
    }

    public void setShowProgressDialog(boolean showProgressDialog) {
        this.mShowProgressDialog = showProgressDialog;
    }

    public void onPreRequest() {
        if (!mShowProgressDialog)
            return;

        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle(mMessage);
        mDialog.show();
    }

    public void onResponse() {
        if (!mShowProgressDialog)
            return;

        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
