package me.skyun.demos.ORMDemo.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import me.skyun.anhelper.Implement.DefaultViewHelper;
import me.skyun.demos.ORMDemo.Logic.IORMDemoUI;
import me.skyun.demos.ORMDemo.Logic.ORMDemoLogic;
import me.skyun.demos.ORMDemo.Model.Group;
import me.skyun.demos.ORMDemo.Model.User;
import me.skyun.test.R;

/**
 * Created by linyun on 15-5-28.
 */
public class ORMDemoActivity extends FragmentActivity implements IORMDemoUI {

    private static final int ID_GROUP = R.id.orm_edit_group;
    private static final int ID_USER = R.id.orm_edit_user;
    private static final int ID_ADD = R.id.orm_tv_add;
    private static final int ID_QUERY = R.id.orm_tv_query;

    private ListView mListView;
    private ORMDemoAdapter mAdapter;

    private ORMDemoLogic mORMDemoLogic;
    private DefaultViewHelper mDefaultViewHelper;

    public ORMDemoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orm_demo);

        mDefaultViewHelper = new DefaultViewHelper(this);
        mORMDemoLogic = new ORMDemoLogic(this, this);

        mListView = (ListView) findViewById(R.id.orm_lv);
        mAdapter = new ORMDemoAdapter(mORMDemoLogic);
        mListView.setAdapter(mAdapter);

        mDefaultViewHelper.render(getWindow().getDecorView(),
            ID_ADD, "add",
            ID_ADD, mOnAddClickListener,
            ID_QUERY, "query_group",
            ID_QUERY, mOnQueryClickListener
        );
    }

    private View.OnClickListener mOnAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String groupName = mDefaultViewHelper.getText(ID_GROUP);
            String userName = mDefaultViewHelper.getText(ID_USER);
            mORMDemoLogic.onAddUser(groupName, userName);
        }
    };

    private View.OnClickListener mOnQueryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String groupName = mDefaultViewHelper.getText(ID_GROUP);
            String userName = mDefaultViewHelper.getText(ID_USER);
            mORMDemoLogic.onQuery(groupName, userName);
        }
    };

    @Override
    public void renderView(List<Group> groups) {
        mAdapter.setData(groups);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderView(User user) {
    }
}
