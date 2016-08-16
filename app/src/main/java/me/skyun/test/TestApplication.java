package me.skyun.test;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import jp.wasabeef.takt.Takt;

//import com.facebook.stetho.Stetho;

//import me.skyun.androidlib.network.NetworkDispatcher;

/**
 * Created by linyun on 15-6-2.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Takt.stock(this).play();

        Log.d("debug", "test lint");

//        Stetho.initialize(Stetho.newInitializerBuilder(this)
//            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//            .build());

//        initNetworkDispather();

        test("abc");
        String a = System.out.toString();
        test(a);
    }

    private void test(@NonNull String a) {
    }

//    private NetworkDispatcher mNetworkDispatcher;

//    private void initNetworkDispather() {
//        mNetworkDispatcher = new NetworkDispatcher(this);
//        EventBus.getDefault().register(mNetworkDispatcher);
//    }
}

