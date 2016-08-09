package me.skyun.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import me.chunyu.ioc.IOCProcessor;

//import me.chunyu.ioc.ProcessorUtils;

//import me.chunyu.iocprocessor.BroadcastRecvr;

//import me.chunyu.g7anno.G7Anno;
//import me.chunyu.g7anno.processor.ActivityProcessor;
//import me.chunyu.iocprocessor.BroadcastRecvr;


public class AppMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView lv = new ListView(this);
        setContentView(lv);

        Data data = new Data();

//        ActivityProcessor activityProcessor = (ActivityProcessor) G7Anno.adaptProcessor(getClass());
//        activityProcessor.createBroadcastReceiver(this);

        ActivityAdapter adapter = new ActivityAdapter(this);
        adapter.addAll(getActivities());
        lv.setAdapter(adapter);
        lv.setDividerHeight(5);

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("test"));
    }

    private class ActivityAdapter extends ArrayAdapter<ActivityInfo> {

        public ActivityAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }
            final Class activityClz = getActivityClass(getItem(position));
            TextView textView = (TextView) convertView;
            if (activityClz != null) {
                textView.setText(activityClz.getSimpleName());
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AppMainActivity.this, activityClz));
                }
            });
            return convertView;
        }
    }

    private Class getActivityClass(ActivityInfo activityInfo) {
        try {
            return Class.forName(activityInfo.name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private List<ActivityInfo> getActivities() {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            List<ActivityInfo> activityInfoList = new ArrayList<>(packageInfo.activities.length);
            for (ActivityInfo info : packageInfo.activities) {
                if (getActivityClass(info) != null) {
                    activityInfoList.add(info);
                }
            }
            Collections.reverse(activityInfoList);
            return activityInfoList;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    private class Temp_TT {
        public String action = "";
    }

    //    @BroadcastRecvr(actions = "test_aaa", actionTypes = {String.class, Integer.class})
    protected void onAAA(Context context, Intent intent) {
        Toast.makeText(context, "haha test succuess" + new Temp_TT().action, Toast.LENGTH_SHORT).show();
    }

    //    @BroadcastResponder(action = "test_bbb", actionTypes = {Integer.class, UsageEvents.Event.class})
//    @BroadcastRecvr(actions = "test_bbb", actionTypes = {Integer.class, UsageEvents.Event.class})
    private void onBBB(Context context, Intent intent, String abc) {
        Toast.makeText(context, "haha test succuess", Toast.LENGTH_SHORT).show();
    }
}

