package com.vsa.paperknife;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by albertovecinasanchez on 4/9/15.
 */
public class PaperKnife {

    private static final String TAG = PaperKnife.class.getSimpleName();

    public static void parse(Object object) {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CellSource.class)) {
                CellSource annotation = method.getAnnotation(CellSource.class);
                Log.d(TAG, "Item Id: " + annotation.value());
            }
        }
    }

    public static Builder map(CellElement element) {
        return new Builder(element);
    }

    public static class Builder {

        private HashMap<String, Object> mHashMap = new HashMap<>();

        public Builder (CellElement element) {
            Method[] methods = element.getClass().getMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(CellSource.class)) {
                    CellSource cellSourceAnnotation = method.getAnnotation(CellSource.class);
                    String targetId = cellSourceAnnotation.value();
                    try {
                        Object data = method.invoke(element);
                        mHashMap.put(targetId, data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        public Builder with(CellInteractor interactor) {
            return this;
        }

        public void into (ViewTarget viewTarget){

            Method[] methods = viewTarget.getClass().getMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(CellTarget.class)) {
                    CellTarget cellTargetAnnotation = method.getAnnotation(CellTarget.class);
                    String targetId = cellTargetAnnotation.value();
                    try {
                        Object data = mHashMap.get(targetId);
                        method.invoke(viewTarget, data);
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
