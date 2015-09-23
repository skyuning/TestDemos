package me.skyun.test.AnyChatDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bairuitech.anychat.AnyChatCoreSDK;
import com.example.helloanychat.RoleInfo;

import java.util.ArrayList;
import java.util.List;

import me.skyun.test.R;

/**
 * Created by linyun on 15-9-21.
 */
public class AnyChatRoomFragment extends ListFragment {

    private AnyChatCoreSDK mAnyChatSDK;
    private _Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new _Adapter(getActivity());
        setListAdapter(mAdapter);
    }

    public void showList(Integer myId, AnyChatCoreSDK anyChatSDK) {
        if (myId == null) {
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            return;
        }

        mAnyChatSDK = anyChatSDK;
        List<RoleInfo> userInfos = new ArrayList<RoleInfo>();

        RoleInfo me = new RoleInfo();
        me.setName(mAnyChatSDK.GetUserName(myId));
        me.setUserID("" + myId);
        me.setRoleIconID(R.drawable.role_1);
        userInfos.add(me);

        for (int userId : mAnyChatSDK.GetOnlineUser()) {
            RoleInfo info = new RoleInfo();
            info.setName(anyChatSDK.GetUserName(userId));
            info.setUserID("" + userId);
            info.setRoleIconID(R.drawable.role_2);
            userInfos.add(info);
        }

        mAdapter.addAll(userInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RoleInfo roleInfo = (RoleInfo) l.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), AnyChatLiveActivity.class);
        intent.putExtra("UserID", roleInfo.getUserID());
        startActivity(intent);
    }
}

class _Adapter extends ArrayAdapter<RoleInfo> {
    public _Adapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.cell_role, null);
            holder = new ViewHolder();
            holder.mAvatarView = (ImageView) convertView.findViewById(R.id.role_iv_avatar);
            holder.mNameView = (TextView) convertView.findViewById(R.id.role_tv_name);
            holder.mIdView = (TextView) convertView.findViewById(R.id.role_tv_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RoleInfo item = getItem(position);
        holder.mAvatarView.setImageResource(item.getRoleIconID());
        holder.mNameView.setText(item.getName());
        holder.mIdView.setText(item.getUserID());
        return convertView;
    }

    private class ViewHolder {
        public ImageView mAvatarView;
        public TextView mNameView;
        public TextView mIdView;
    }
}

