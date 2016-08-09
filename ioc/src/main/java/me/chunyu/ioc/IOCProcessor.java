package me.chunyu.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by linyun on 16/5/24.
 */

public class IoCProcessor {

    public static final String POST_FIX = "$$Processor";

    public static void process(Object obj) {
        String processorClassName = obj.getClass().getName() + POST_FIX;
        try {
            Class processorClass = Class.forName(processorClassName);
            Constructor constructor = processorClass.getConstructor(obj.getClass());
            Runnable processor = (Runnable) constructor.newInstance(obj);
            processor.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
