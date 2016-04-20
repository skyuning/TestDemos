//package me.skyun.demos.AnimatorDemo;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.Keyframe;
//import android.animation.LayoutTransition;
//import android.animation.ObjectAnimator;
//import android.animation.PropertyValuesHolder;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//
//import me.skyun.test.R;
//
///**
// * Created by linyun on 15-6-17.
// */
//public class AnimatorDemoActivity extends FragmentActivity {
//
//    AnimatorDemoFragment mFragment = new AnimatorDemoFragment();
//    LayoutTransition mTransitioner = new LayoutTransition();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_animator_demo);
//        findViewById(R.id.bottom_bar).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleFragment();
//            }
//        });
//
//        setTransition();
//
//        ViewGroup mainLayout = (ViewGroup) findViewById(R.id.main);
//        mainLayout.setLayoutTransition(mTransitioner);
//
//        ViewGroup container = (ViewGroup) findViewById(R.id.container);
//        container.setLayoutTransition(mTransitioner);
//    }
//
//    private void toggleFragment() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        if (mFragment.isAdded()) {
//            ft.remove(mFragment);
//        } else {
//            ft.add(R.id.container, mFragment, mFragment.getClass().getName());
//        }
//        ft.commit();
//    }
//
//    private void setTransition() {
//        /**
//         * view出现时 view自身的动画效果
//         */
//        ObjectAnimator animator1 = ObjectAnimator.ofInt(null, "alpha", 0, 1).
//            setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
//        mTransitioner.setAnimator(LayoutTransition.APPEARING, animator1);
//
//        /**
//         * view 消失时，view自身的动画效果
//         */
//        ObjectAnimator animator2 = ObjectAnimator.ofInt(null, "alpha", 1, 0).
//            setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
//        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animator2);
//
//        /**
//         * view 动画改变时，布局中的每个子view动画的时间间隔
//         */
//        mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
//        mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
//        mTransitioner.setStartDelay(LayoutTransition.APPEARING, 0);
//        mTransitioner.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);
//        mTransitioner.setStartDelay(LayoutTransition.DISAPPEARING, 0);
//        mTransitioner.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
//
//
//        /**
//         * 为什么这里要这么写？具体我也不清楚，ViewGroup源码里面是这么写的，我只是模仿而已
//         * 不这么写貌似就没有动画效果了，所以你懂的！
//         */
//        PropertyValuesHolder pvhLeft =
//            PropertyValuesHolder.ofInt("left", 0, 1);
//        PropertyValuesHolder pvhTop =
//            PropertyValuesHolder.ofInt("top", 0, 1);
//        PropertyValuesHolder pvhRight =
//            PropertyValuesHolder.ofInt("right", 0, 1);
//        PropertyValuesHolder pvhBottom =
//            PropertyValuesHolder.ofInt("bottom", 0, 1);
//
//
//        /**
//         * view出现时，导致整个布局改变的动画
//         */
//        PropertyValuesHolder animator3 = PropertyValuesHolder.ofFloat("scaleX", 1F, 2F, 1F);
//        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
//            this, pvhLeft, pvhTop, pvhRight, pvhBottom, animator3).
//            setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
//        changeIn.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                View view = (View) ((ObjectAnimator) animation).getTarget();
//                view.setScaleX(1.0f);
//            }
//        });
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
//
//
//        /**
//         * view消失，导致整个布局改变时的动画
//         */
////        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
////        Keyframe kf1 = Keyframe.ofFloat(.5f, 2f);
////        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
////        PropertyValuesHolder pvhRotation =
////            PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2);
////        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
////            this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
////            setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
////        changeOut.addListener(new AnimatorListenerAdapter() {
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                View view = (View) ((ObjectAnimator) animation).getTarget();
////                view.setScaleX(1.0f);
////            }
////        });
////        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
//    }
//}
