package me.chunyu.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by linyun on 16/5/19.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface BroadcastRecvr {

    String[] actions() default {};

    String ACTION_TYPES = "actionTypes";

    Class[] actionTypes() default {};

    String[] categories() default {};

    String CATEGORY_TYPES = "categoryTypes";

    Class[] categoryTypes() default {};
}
