package me.skyun.test;

import android.app.Application;

//import com.facebook.stetho.Stetho;

//import me.skyun.androidlib.network.NetworkDispatcher;

/**
 * Created by linyun on 15-6-2.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Stetho.initialize(Stetho.newInitializerBuilder(this)
//            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//            .build());

//        initNetworkDispather();

    }

//    private NetworkDispatcher mNetworkDispatcher;

//    private void initNetworkDispather() {
//        mNetworkDispatcher = new NetworkDispatcher(this);
//        EventBus.getDefault().register(mNetworkDispatcher);
//    }
}
