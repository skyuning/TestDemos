package me.skyun.demos.ListFilterDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import me.skyun.test.R;

public class ListFilterDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filter_demo);

        ListView listView = (ListView) findViewById(R.id.list_view);
        final FilterAdapter<String> adapter = new FilterAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.addAll("aaa", "bbb", "ccc", "abc", "acb", "bac", "bca");
        listView.setAdapter(adapter);

        EditText editText = (EditText) findViewById(R.id.hospital_name);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
