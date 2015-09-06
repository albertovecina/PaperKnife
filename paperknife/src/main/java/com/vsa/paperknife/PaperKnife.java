package com.vsa.paperknife;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by albertovecinasanchez on 4/9/15.
 */
public class PaperKnife {

    public static final String TAG = PaperKnife.class.getSimpleName();

    public static Builder map(CellElement element) {
        return new Builder(element);
    }

    public static class Builder {

        private CellElement mElement;

        private HashMap<String, Object> mHashMap = new HashMap<>();

        public Builder (CellElement element) {
            mElement = element;
            Method[] methods = mElement.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(DataSource.class)) {
                    DataSource dataSourceAnnotation = method.getAnnotation(DataSource.class);
                    String sourceId = dataSourceAnnotation.value();
                    try {
                        method.setAccessible(true);
                        Object data = method.invoke(mElement);
                        mHashMap.put(sourceId, data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        Log.e(PaperKnife.TAG, "Source method not accesible: " + sourceId);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        public Builder with(CellProvider cellProvider) {
            Method[] methods = cellProvider.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(DataSource.class)) {
                    DataSource dataSourceAnnotation = method.getAnnotation(DataSource.class);
                    String sourceId = dataSourceAnnotation.value();
                    try {
                        Object data = method.invoke(cellProvider, mElement);
                        mHashMap.put(sourceId, data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        Log.e(PaperKnife.TAG, "Source method not accesible: " + sourceId);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
            return this;
        }

        public void into (CellTarget cellTarget){

            Method[] methods = cellTarget.getClass().getMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(DataTarget.class)) {
                    DataTarget dataTargetAnnotation = method.getAnnotation(DataTarget.class);
                    String targetId = dataTargetAnnotation.value();
                    try {
                        Object data = mHashMap.get(targetId);
                        if(data != null)
                            method.invoke(cellTarget, data);
                        else
                            Log.e(PaperKnife.TAG, "There is no source for target: " + targetId);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }





}
