package me.skyun.test.ORMDemo.UI;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.skyun.test.ORMDemo.Logic.ORMDemoLogic;
import me.skyun.test.ORMDemo.Model.Group;
import me.skyun.test.ORMDemo.Model.User;
import me.skyun.test.R;

/**
 * Created by linyun on 15-5-28.
 */
public class ORMDemoAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_GROUP = 0;
    private static final int ITEM_TYPE_USER = 1;

    private ORMDemoLogic mORMDemoLogic;
    private List<Object> mData = new ArrayList<Object>();

    public ORMDemoAdapter(ORMDemoLogic ORMDemoLogic) {
        mORMDemoLogic = ORMDemoLogic;
    }

    public void setData(List<Group> groups) {
        mData.clear();
        for (Group group : groups) {
            mData.add(group);
            mData.addAll(group.users);
        }
    }
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (getItem(position) instanceof Group) ? ITEM_TYPE_GROUP : ITEM_TYPE_USER;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == ITEM_TYPE_GROUP)
            return getGruopView(position, convertView, parent, (Group) getItem(position));
        else
            return getItemView(position, convertView, parent, (User) getItem(position));
    }

    public View getGruopView(int position, View convertView, ViewGroup parent, final Group group) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_group, null);
            holder.nameView = (TextView) convertView.findViewById(R.id.group_tv);
            holder.delView = convertView.findViewById(R.id.group_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(group.name);
        holder.delView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mORMDemoLogic.onDeleteGroup(group);
            }
        });
        return convertView;
    }

    public View getItemView(int position, View convertView, ViewGroup parent, final User user) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_item, null);
            holder.nameView = (TextView) convertView.findViewById(R.id.item_tv);
            holder.delView = convertView.findViewById(R.id.item_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(user.name);
        holder.delView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mORMDemoLogic.onDeleteUser(user);
                mData.remove(user);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView nameView;
        View delView;
    }
}

