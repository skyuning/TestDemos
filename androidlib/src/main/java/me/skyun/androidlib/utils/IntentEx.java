package me.skyun.androidlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by linyun on 16/2/27.
 */
@SuppressLint("ParcelCreator")
public class IntentEx extends Intent {

    public IntentEx setAction(String action) {
        return (IntentEx) super.setAction(action);
    }

    public IntentEx setType(String type) {
        return (IntentEx) super.setType(type);
    }
    public IntentEx setData(Uri data) {
        return (IntentEx) super.setData(data);
    }

    public IntentEx setAction(Class actionType) {
        setAction(actionType.getCanonicalName());
        return this;
    }

    public IntentEx addCategories(Class... categories) {
        for (Class category : categories) {
            addCategory(category.getCanonicalName());
        }
        return this;
    }

    public IntentEx putKeyValueExtras(Object... keyValues) {
        for (int i = 0; i < keyValues.length; i += 2) {
            putExtraEx((String) keyValues[i], keyValues[i + 1]);
        }
        return this;
    }

    public IntentEx putObjectExtras(Object... objects) {
        for (Object obj : objects) {
            if (obj != null) {
                putExtraEx(obj.getClass().getCanonicalName(), obj);
            }
        }
        return this;
    }

    public void localBroadcast(Context context) {
        if (context != null) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(this);
        }
    }

    private IntentEx putExtraEx(String key, Object value) {
        if (value instanceof Serializable) {
            return (IntentEx) putExtra(key, (Serializable) value);
        } else if (value instanceof Parcelable) {
            return (IntentEx) putExtra(key, (Parcelable) value);
        }

        try {
            Method putExtraMethod = getClass().getMethod("putExtra", String.class, value.getClass());
            putExtraMethod.setAccessible(true);
            putExtraMethod.invoke(this, key, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }
}
