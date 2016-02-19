package me.skyun.demos.EventBusDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import me.skyun.test.R;

/**
 * Created by linyun on 15-6-16.
 */
public class EventBusDemoActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_demo);

        final EventBus eventBus = EventBus.getDefault();

        eventBus.register(this);

        findViewById(R.id.eventbus_demo_btn_post_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.post(new _Event<String>("hello"), "hello_tag");
                eventBus.post(new _Event<Integer>(12345), "hello_tag");
            }
        });
    }

    @Subscriber(tag = "hello_tag", mode = ThreadMode.ASYNC)
    private void onHello2Event(_Event<Integer> event) {
        setTitle(event.data.toString());
    }

    @Subscriber(tag = "hello_tag", mode = ThreadMode.ASYNC)
    private void onHelloEvent(_Event<String> event) {
        setTitle(event.data.toString());
    }

    private static class _Event<T> {
        T data;

        private _Event(T data) {
            this.data = data;
        }
    }
}
