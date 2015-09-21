package me.skyun.test.ORMDemo.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by linyun on 15-5-28.
 */
@DatabaseTable
public class User {

    @DatabaseField(id = true)
    public String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public Group group;

    public User() {
    }

    public User(String name, Group group) {
        this.name = name;
        this.group = group;
    }
}