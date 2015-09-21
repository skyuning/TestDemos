package me.skyun.test.ORMDemo.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by linyun on 15-5-29.
 * 只用{@link com.j256.ormlite.android.apptools.OpenHelperManager} 打开
 */
public class ORMDemoDBHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something
    // appropriate for your app
    private static final String DB_NAME = "ORMDemo.db";

    // any time you make changes to your database objects, you may have to
    // increase the database version
    private static final int DB_VERSION = 1;

    private DatabaseConnection mConnection;

    public ORMDemoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.d(ORMDemoDBHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Group.class);
        } catch (SQLException e) {
            Log.e(ORMDemoDBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.d(ORMDemoDBHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Group.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(ORMDemoDBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
