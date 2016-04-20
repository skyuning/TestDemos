package me.skyun.demos.MVPDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import me.skyun.test.R;

/**
 * Created by linyun on 15-6-19.
 */
public class MVPDemoActivity extends FragmentActivity {

    private FragmentManager mFm = getSupportFragmentManager();

    private String mPhoneNum;
    private String mPassword;
    private String mCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        setContentView(R.layout.activity_mvp_demo);

        EventBus.getDefault().register(this);

        showFragment(this, android.R.id.content, RegisterInputFragment.class);

        getSupportFragmentManager().executePendingTransactions();

        mCaptcha = "";

        Fragment fragment = new RegisterResetFragment();
        getSupportFragmentManager().beginTransaction()
            .add(fragment, fragment.getClass().getName())
            .commit();
    }

    // TODO 用broadcast代替
//    @Subscriber(tag = "" + R.id.register_btn_get_captcha)
    private void getCaptcha(ClickEvent event) {
        // TODO get captcha

        showFragment(this, android.R.id.content, RegisterVerifyFragment.class);
    }

    // TODO 用broadcast代替
//    @Subscriber(tag = "" + R.id.register_btn_register)
    private void register(ClickEvent event) {
        // TODO verify and register

        showFragment(this, android.R.id.content, RegisterResetFragment.class);
    }

    protected static void showFragment(FragmentActivity activity, int containerId, Class<? extends Fragment> fragClz) {
        FragmentManager fm = activity.getSupportFragmentManager();
        String fragName = fragClz.getName();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentByTag(fragName);
        if (fragment == null) {
            fragment = Fragment.instantiate(activity, fragName);
            ft.setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right
            );
            ft.replace(containerId, fragment, fragName);
            ft.addToBackStack(fragName);
        }
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        removeAllFragments(getSupportFragmentManager());
        super.onSaveInstanceState(outState);
    }

    public static void removeAllFragments(FragmentManager fm) {
        if (fm.getFragments().isEmpty())
            return;
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fm.getFragments()) {
            ft.remove(fragment);
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (mFm.getBackStackEntryCount() <= 1)
            finish();
        else
            super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
