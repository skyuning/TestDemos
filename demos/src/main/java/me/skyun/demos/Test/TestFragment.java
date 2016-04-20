package me.skyun.demos.Test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//import me.chunyu.g7anno.G7Anno;
//import me.chunyu.g7anno.annotation.BroadcastResponder;
//import me.chunyu.g7anno.processor.V4FragmentProcessor;

/**
 * Created by linyun on 15-5-20.
 */
public class TestFragment extends Fragment {

//    @BroadcastResponder(actionTypes = TestActivity.DateEvent.class)
    protected void onActionTest1(Context context, TestActivity.DateEvent event) {
        Toast.makeText(context, "receive action test 1 in fragment\n" + event.toString(),
            Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        V4FragmentProcessor processor = (V4FragmentProcessor) G7Anno.adaptProcessor(getClass());
//        mBroadcastReceiver = processor.createBroadcastReceiver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(inflater.getContext());
        textView.setHeight(50);
        textView.setText(this.toString());
        textView.setBackgroundColor(Color.CYAN);
        return textView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
