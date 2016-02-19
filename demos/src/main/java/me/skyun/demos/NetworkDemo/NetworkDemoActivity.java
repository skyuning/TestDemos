package me.skyun.demos.NetworkDemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import me.skyun.network.BasicNetworkRequest;
import me.skyun.test.R;

/**
 * Created by linyun on 15-7-31.
 */
public class NetworkDemoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_demo);

        EventBus.getDefault().register(this);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend();
            }
        });
//        com.android.ide.comon.process.ProcessException: org.gradle.process.internal.ExceException: finished with non-zero exit value 2
    }

    private void onSend() {
        EditText urlView = (EditText) findViewById(R.id.url);
        String url = urlView.getText().toString();
        if (TextUtils.isEmpty(url))
            url = urlView.getHint().toString();
        if (TextUtils.isEmpty(url))
            return;

        BasicNetworkRequest request = new BasicNetworkRequest();
        request.setUrl(url);
        EventBus.getDefault().post(request);
    }

    @Subscriber
    private void onResponse(JSONObject object) {
        TextView responseView = (TextView) findViewById(R.id.response);
        responseView.setText(object.toString());
    }
}
