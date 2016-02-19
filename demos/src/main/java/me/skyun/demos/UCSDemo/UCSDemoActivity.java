package me.skyun.demos.UCSDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.reason.UcsReason;
import com.yzx.api.UCSCall;
import com.yzx.api.UCSService;
import com.yzx.listenerInterface.ConnectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import me.skyun.test.R;

/**
 * Created by linyun on 15-9-23.
 */
public class UCSDemoActivity extends AppCompatActivity implements ConnectionListener {

    private final String TOKEN = "b399390800e89932b4d0b606eb2eba63";
    private final String SID = "skyuning@163.com";
    private final String SPASSWORD = "Skyun870102";
    private final String CID = "78505031362219";
    private final String CPASSWORD = "66c81bab";

    private TextView mMsgView;
    private LinearLayout mRemoteLayout;
    private LinearLayout mLocalLayout;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucs_demo);

        mMsgView = (TextView) findViewById(R.id.ucs_demo_tv_msg);
        mRemoteLayout = (LinearLayout) findViewById(R.id.ucs_demo_layout_remote);
        mLocalLayout = (LinearLayout) findViewById(R.id.ucs_demo_layout_local);

        mQueue = Volley.newRequestQueue(this);

        UCSService.addConnectionListener(this);
        UCSCall.initCameraConfig(this, mRemoteLayout, mLocalLayout);
        UCSCall.setCameraPreViewStatu(this, true);

        findViewById(R.id.ucs_demo_btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UCSService.connect(SID, SPASSWORD, CID, CPASSWORD);
                    }
                }).run();
            }
        });
        findViewById(R.id.ucs_demo_btn_disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UCSService.uninit();
            }
        });

        findViewById(R.id.ucs_demo_btn_get_client_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getClientId();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onConnectionSuccessful() {
        Toast.makeText(this, "连接成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(UcsReason ucsReason) {
        Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
    }

    private void getClientId() throws JSONException {
        final String sid = "bc1c9190542704a8acad39e3fade1dfd";
        String token = "b399390800e89932b4d0b606eb2eba63";
        final long timestamp = System.currentTimeMillis();
        String sign = md5(sid + token + timestamp).toUpperCase();
        String url = "https://api.ucpaas.com/2014-06-30" +
            "/Accounts/bc1c9190542704a8acad39e3fade1dfd/Clients?" + sign;

        Response.Listener respListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                mMsgView.setText(response.toString());
            }
        };

        Response.ErrorListener errListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mMsgView.setText(error.getMessage());
                error.printStackTrace();
            }
        };

        JSONObject jsonBody = new JSONObject();
        JSONObject client = new JSONObject();
        client.put("appId", "46b3b2945af14f1aa9d06c25217c48b7");
        client.put("charge", "0");
        client.put("clientType", "0");
        client.put("friendlyName", "skyun");
        jsonBody.put("client", client);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody, respListener, errListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8;");
                String auth = sid + ":" + timestamp;
                auth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };

        mQueue.add(request);
    }

    private static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            String dstr = (new BigInteger(1, md.digest())).toString(16);
            return dstr;
        } catch (Exception var5) {
            var5.printStackTrace();
            return "";
        }
    }
}
