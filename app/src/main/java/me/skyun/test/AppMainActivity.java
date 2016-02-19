package me.skyun.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = new ListView(this);
        setContentView(lv);

        ActivityAdapter adapter = new ActivityAdapter(this);
        adapter.addAll(getActivites());
        lv.setAdapter(adapter);
        lv.setDividerHeight(5);
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
            final Class activityClz = getActivityClass(position);
            TextView textView = (TextView) convertView;
            if (activityClz != null)
                textView.setText(activityClz.getSimpleName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AppMainActivity.this, activityClz));
                }
            });
            return convertView;
        }

        private Class getActivityClass(int position) {
            ActivityInfo activityInfo = getItem(position);
            try {
                return Class.forName(activityInfo.name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private ActivityInfo[] getActivites() {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return new ActivityInfo[0];
        }
    }
}

