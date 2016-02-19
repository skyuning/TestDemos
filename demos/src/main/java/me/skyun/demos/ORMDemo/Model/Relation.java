package me.skyun.demos.ORMDemo.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by linyun on 15-6-4.
 */
@DatabaseTable
public class Relation {

    @DatabaseField(id = true)
    public int id;

    @ForeignCollectionField
    public Group group;

    @ForeignCollectionField
    public User user;
}
