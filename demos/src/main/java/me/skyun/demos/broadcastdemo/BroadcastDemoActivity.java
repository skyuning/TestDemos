//package me.skyun.demos.broadcastdemo;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.content.LocalBroadcastManager;
//import android.view.View;
//import android.widget.TextView;
//
//import me.chunyu.ioc.BroadcastRecvr;
//import me.chunyu.ioc.IoCProcessor;
//import me.skyun.test.R;
//
//public class BroadcastDemoActivity extends FragmentActivity {
//
//    private TextView mBroadcastView;
//    private TextView mInvokeView;
//    private TextView mMessageView;
//
//    private int mCount = 0;
//    private int mLoopCount = 1000;
//    private long mStartMS;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_broadcast_demo);
//
//        IoCProcessor.process(this);
//
//        mBroadcastView = (TextView) findViewById(R.id.broadcast);
//        mInvokeView = (TextView) findViewById(R.id.invoke);
//        mMessageView = (TextView) findViewById(R.id.message);
//
//        mBroadcastView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mStartMS = System.currentTimeMillis();
//                broadcast();
//            }
//        });
//
//        mInvokeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mStartMS = System.currentTimeMillis();
//                broadcast();
//            }
//        });
//    }
//
//    private void broadcast() {
//        Intent intent = new Intent(String.class.getName());
//        intent.addCategory(getClass().getName());
//        intent.addCategory(Fragment.class.getName());
//        intent.putExtra(String.class.getName(), "duration: ");
//        LocalBroadcastManager.getInstance(BroadcastDemoActivity.this).sendBroadcast(intent);
//    }
//
//    @BroadcastRecvr(actions = "send", actionTypes = String.class,
//            categoryTypes = {BroadcastDemoActivity.class, Fragment.class})
//    protected void onReceive(String message) {
//        mCount++;
//        try {
//            Class.forName(BroadcastDemoActivity.class.getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (mCount % mLoopCount != 0) {
//            broadcast();
//        } else {
//            long duration = System.currentTimeMillis() - mStartMS;
//            mMessageView.setText(message + duration / 1000.0);
//        }
//    }
//}
//
