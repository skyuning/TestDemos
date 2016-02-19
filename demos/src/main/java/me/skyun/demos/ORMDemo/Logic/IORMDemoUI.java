package me.skyun.demos.ORMDemo.Logic;

import java.util.List;

import me.skyun.demos.ORMDemo.Model.Group;
import me.skyun.demos.ORMDemo.Model.User;

/**
 * Created by linyun on 15-5-28.
 */
public interface IORMDemoUI {

    public void renderView(List<Group> groups);

    public void renderView(User user);
}
