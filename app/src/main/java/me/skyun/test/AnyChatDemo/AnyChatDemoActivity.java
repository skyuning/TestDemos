package me.skyun.test.AnyChatDemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;

import me.skyun.test.ImageMaskDemo.ViewStateUtils;
import me.skyun.test.R;

/**
 * Created by linyun on 15-9-21.
 */
public class AnyChatDemoActivity extends FragmentActivity implements AnyChatBaseEvent {

    private String mServerIP = "demo.anychat.cn";
    private int mPort = 8906;

    private EditText mUsernameView;
    private EditText mRoomIdView;
    private Button mLoginOrOutBtn;
    private AnyChatRoomFragment mRoomFragment;

    private AnyChatCoreSDK mAnyChatSDK;
    private int mDwUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_chat_demo);
        setTitle("登录");
        mAnyChatSDK = AnyChatCoreSDK.getInstance(this);
        mAnyChatSDK.InitSDK(Build.VERSION.SDK_INT, 0);
        mAnyChatSDK.SetBaseEvent(this);

        mUsernameView = (EditText) findViewById(R.id.anychat_et_username);
        mRoomIdView = (EditText) findViewById(R.id.anychat_et_room_id);
        mLoginOrOutBtn = (Button) findViewById(R.id.anychat_btn_login);
        mRoomFragment = (AnyChatRoomFragment) getSupportFragmentManager().findFragmentById(R.id.anychat_fragment_room);
        ViewStateUtils.makeStates(mLoginOrOutBtn);
        mLoginOrOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLogin = (Boolean) v.getTag();
                if (isLogin == null || !isLogin) {
                    mAnyChatSDK.Connect(mServerIP, mPort);
                    mAnyChatSDK.Login(mUsernameView.getText().toString(), "");
                } else {
                    mAnyChatSDK.LeaveRoom(-1);
                    mAnyChatSDK.Logout();
                    mLoginOrOutBtn.setText("登录");
                    mLoginOrOutBtn.setTag(false);
                    mRoomFragment.showList(null, mAnyChatSDK);
                }
            }
        });
    }

    @Override
    public void OnAnyChatConnectMessage(boolean bSuccess) {
        Toast.makeText(AnyChatDemoActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
        if (dwErrorCode == 0) {
            Toast.makeText(AnyChatDemoActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            int roomID = Integer.valueOf(mRoomIdView.getText().toString());
            mAnyChatSDK.EnterRoom(roomID, "");
            mLoginOrOutBtn.setText("注销");
            mLoginOrOutBtn.setTag(true);
            mDwUserId = dwUserId;
        } else {
            Toast.makeText(AnyChatDemoActivity.this, "登录失败，errorCode：" + dwErrorCode, Toast.LENGTH_SHORT).show();
            mLoginOrOutBtn.setTag(false);
        }
    }

    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
        if (dwErrorCode == 0) {
            Toast.makeText(AnyChatDemoActivity.this, "进入房间：" + dwRoomId, Toast.LENGTH_SHORT).show();
            mRoomFragment.showList(mDwUserId, mAnyChatSDK);
        } else {
            Toast.makeText(AnyChatDemoActivity.this, "进入房间失败：" + dwErrorCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
    }

    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
    }

    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
        Toast.makeText(AnyChatDemoActivity.this, "连接关闭，errorCode：" + dwErrorCode, Toast.LENGTH_SHORT).show();
    }

}
