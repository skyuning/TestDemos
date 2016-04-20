package me.skyun.androidlib.network;

/**
 * Created by linyun on 15-7-31.
 */
public class BasicNetworkRequest implements INetworkRequest {

    private String mUrl;

    @Override
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String[] getParams() {
        return new String[0];
    }
}
