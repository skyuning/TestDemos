package me.skyun.test.ORMDemo.Logic;

import java.util.List;

import me.skyun.test.ORMDemo.Model.Group;
import me.skyun.test.ORMDemo.Model.User;

/**
 * Created by linyun on 15-5-28.
 */
public interface IORMDemoUI {

    public void renderView(List<Group> groups);

    public void renderView(User user);
}
