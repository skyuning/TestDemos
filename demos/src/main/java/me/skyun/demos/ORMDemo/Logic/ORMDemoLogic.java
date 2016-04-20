package me.skyun.demos.ORMDemo.Logic;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.skyun.androidlib.implement.ORMLiteApi;
import me.skyun.androidlib.interfaces.IORMApi;
import me.skyun.demos.ORMDemo.Model.Group;
import me.skyun.demos.ORMDemo.Model.ORMDemoDBHelper;
import me.skyun.demos.ORMDemo.Model.User;

/**
 * Created by linyun on 15-5-28.
 */
public class ORMDemoLogic {

    private IORMDemoUI mORMDemoUI;
    private IORMApi mORMApi;

    public ORMDemoLogic(Context context, IORMDemoUI ORMDemoUI) {
        mORMDemoUI = ORMDemoUI;
        mORMApi = new ORMLiteApi(OpenHelperManager.getHelper(context, ORMDemoDBHelper.class));
    }

    public void onAddUser(String groupName, String itemName) {
        doAddUser(groupName, itemName);
        showGroup(groupName);
    }

    private void doAddUser(String groupName, String name) {
        Group group = new Group(groupName);
        User user = new User(name, group);

        try {
            mORMApi.beginTransaction();
            mORMApi.insertIfNotExists(group);
            mORMApi.insertIfNotExists(user);
            mORMApi.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                mORMApi.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }


    public void onDeleteUser(User user) {
        mORMApi.delete(user);
    }

    public void onDeleteGroup(Group group) {
        mORMApi.delete(group);
        showGroup(null);
    }

    public void onQuery(String groupName, String userName) {
        if (TextUtils.isEmpty(userName))
            showGroup(groupName);
        else {
            if (TextUtils.isEmpty(groupName))
                showUser(userName);
            else
                showGroupAndUser(groupName, userName);
        }
    }

    private void showGroup(String groupName) {
        List<Group> groups;

        if (TextUtils.isEmpty(groupName)) {
            groups = mORMApi.queryAll(Group.class);
        } else {
            groups = new ArrayList<Group>(1);
            Group group = mORMApi.queryForId(Group.class, groupName);
            if (group != null)
                groups.add(group);
        }
        mORMDemoUI.renderView(groups);
    }

    private void showUser(String userName) {
        User user = mORMApi.queryForId(User.class, userName);
        if (user != null)
            mORMDemoUI.renderView(user);
    }

    private void showGroupAndUser(String groupName, String userName) {
        Group group = mORMApi.queryForId(Group.class, groupName);
        for (User user : group.users) {
            if (user.name.equals(userName)) {
                List<Group> groups = new ArrayList<Group>(1);
                groups.add(group);
                mORMDemoUI.renderView(groups);
            }
        }
    }
}
