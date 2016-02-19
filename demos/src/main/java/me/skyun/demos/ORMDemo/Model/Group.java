package me.skyun.demos.ORMDemo.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by linyun on 15-5-28.
 */
@DatabaseTable
public class Group {

    @DatabaseField(id = true)
    public String name;

    @ForeignCollectionField
    public ForeignCollection<User> users;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }
}
