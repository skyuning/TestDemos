package me.skyun.test.MVPDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simple.eventbus.EventBus;

import me.skyun.test.R;

/**
 * Created by linyun on 15-6-19.
 */
public class RegisterInputFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_input, null);
        view.findViewById(R.id.register_btn_get_captcha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ClickEvent(), "" + R.id.register_btn_get_captcha);
            }
        });
        return view;
    }
}
