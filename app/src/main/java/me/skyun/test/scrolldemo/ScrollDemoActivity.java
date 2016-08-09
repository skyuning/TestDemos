package me.skyun.test.scrolldemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import me.skyun.test.R;

/**
 * Created by linyun on 16/7/7.
 */
public class ScrollDemoActivity extends Activity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_demo);

        final EditText positionView = (EditText) findViewById(R.id.edit_text_position);
        Button goBtn = (Button) findViewById(R.id.go_button);
        mListView = (ListView) findViewById(R.id.list_view);

        mListView.setAdapter(mAdapter);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(positionView.getText().toString());
                mListView.setSelection(position);
//                scrollToPosition(position);
            }
        });
    }

    private ListAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextColor(Color.WHITE);
            String n = getItem(position).toString() + "\n";
            textView.setText(n + n + n + n + n + n + n + n + n + n + n + n + n);
            return textView;
        }
    };

    private void scrollToPosition(final int position) {
        if (position > mListView.getLastVisiblePosition()) {
            mListView.smoothScrollBy(mListView.getHeight(), 0);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    scrollToPosition(position);
                }
            });
        }
    }
}

