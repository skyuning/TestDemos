package me.skyun.test;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.justalk.cloud.lemon.MtcApi;
import com.justalk.cloud.zmf.ZmfAudio;
import com.justalk.cloud.zmf.ZmfVideo;

//import me.skyun.network.NetworkDispatcher;

/**
 * Created by linyun on 15-6-2.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());

//        initNetworkDispather();

        ZmfAudio.initialize(this);
        ZmfVideo.initialize(this);
        MtcApi.init(this, "301ad850be069e9694944097");
    }

//    private NetworkDispatcher mNetworkDispatcher;

//    private void initNetworkDispather() {
//        mNetworkDispatcher = new NetworkDispatcher(this);
//        EventBus.getDefault().register(mNetworkDispatcher);
//    }
}
