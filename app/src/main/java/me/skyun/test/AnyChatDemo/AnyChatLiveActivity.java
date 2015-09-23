package me.skyun.test.AnyChatDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;

import me.skyun.test.R;

public class AnyChatLiveActivity extends Activity implements AnyChatBaseEvent {

    int userID;
    private SurfaceView mRemoteSurface;
    private SurfaceView mLocalSurface;
    public AnyChatCoreSDK mAnyChatSDK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_any_chat_live);
        mLocalSurface = (SurfaceView) findViewById(R.id.anychat_surface_local);
        mRemoteSurface = (SurfaceView) findViewById(R.id.anychat_surface_remote);

        Intent intent = getIntent();
        userID = Integer.parseInt(intent.getStringExtra("UserID"));

        mAnyChatSDK = new AnyChatCoreSDK();
        mAnyChatSDK.SetBaseEvent(this);

        mAnyChatSDK.mSensorHelper.InitSensor(this);
        AnyChatCoreSDK.mCameraHelper.SetContext(this);
        mLocalSurface.getHolder().addCallback(AnyChatCoreSDK.mCameraHelper);
        mLocalSurface.setZOrderOnTop(true);

        mAnyChatSDK.UserCameraControl(-1, 1);// -1表示对本地视频进行控制，打开本地视频
        mAnyChatSDK.UserSpeakControl(-1, 1);// -1表示对本地音频进行控制，打开本地音频

        int index = mAnyChatSDK.mVideoHelper.bindVideo(mRemoteSurface.getHolder());
        mAnyChatSDK.mVideoHelper.SetVideoUser(index, userID);
        mAnyChatSDK.UserCameraControl(userID, 1);
        mAnyChatSDK.UserSpeakControl(userID, 1);
    }

    @Override
    public void OnAnyChatConnectMessage(boolean bSuccess) {
    }

    @Override
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
    }

    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
    }

    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
    }

    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
    }

    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
    }
}
