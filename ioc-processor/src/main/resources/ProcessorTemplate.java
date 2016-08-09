package $packageName;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class $processorName<T extends $className> implements Runnable {

    private T m$className;

    public $processorName(T obj) {
        m$className = obj;
    }

    @Override
    public void run() {
        if (m$className instanceof Activity) {
            registerBroadcastReceivers((Context) m$className);
        }
    }

    public void registerBroadcastReceivers(Context context) {
        IntentFilter intentFilter;
        BroadcastReceiver receiver;
        #foreach($info in $broadcastInfos)##{

            // receiver $velocityCount
            intentFilter = new IntentFilter();
            #foreach($action in $info.actions)
            intentFilter.addAction("$action");
            #end
            #foreach($category in $info.categories)
            intentFilter.addCategory("$category");
            #end

            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    m$className.$info.method;
                }
            };

            LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
            ##}
        #end
        ##

    }
}