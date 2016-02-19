package me.skyun.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by linyun on 15/12/18.
 */
public class NumBlinkActivity extends AppCompatActivity {

    private ViewGroup mContentView;
    private TextView mBlinkNumView;
    private Button mDecView;
    private Button mIncView;
    private Button mNextBtn;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_blink);

        mContentView = (ViewGroup) findViewById(android.R.id.content);
        mBlinkNumView = (TextView) findViewById(R.id.blink_number);
        mDecView = (Button) findViewById(R.id.dec);
        mIncView = (Button) findViewById(R.id.inc);
        mNextBtn = (Button) findViewById(R.id.next_btn);
        mRandom = new Random(System.currentTimeMillis());

        mBlinkNumView.setTag(7);
        mDecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = (int) mBlinkNumView.getTag();
                mBlinkNumView.setTag(len - 1);
            }
        });

        mIncView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = (int) mBlinkNumView.getTag();
                mBlinkNumView.setTag(len + 1);
            }
        });

        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mBlinkNumView.setVisibility(View.VISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mBlinkNumView.setVisibility(View.INVISIBLE);
                        return true;
                    default:
                        return false;
                }
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String s = "å∫ç∂´ƒ©˙ˆ∆˚¬µ˜øπ®ß†¨√∑≈≈¥Ω";
                int number = mRandom.nextInt();
                int number2 = mRandom.nextInt();
                mContentView.setBackgroundColor(number);
                String str = Integer.toHexString(number).toUpperCase();
                str += Integer.toHexString(number2).toUpperCase();
                int len = (int) mBlinkNumView.getTag();
                mBlinkNumView.setText(str.substring(str.length() - len));
            }
        });
    }
}
