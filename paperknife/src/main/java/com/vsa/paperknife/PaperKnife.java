package com.vsa.paperknife;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by albertovecinasanchez on 4/9/15.
 */
public class PaperKnife {

    public static final String TAG = PaperKnife.class.getSimpleName();

    private PaperKnifeBuilder mPaperKnifeBuilder;

    public PaperKnife(CellDataProvider cellDataProvider) {
        this(cellDataProvider, null);
    }

    public PaperKnife(CellDataProvider cellDataProvider,
                      CellListenerProvider cellListenerProvider) {
        mPaperKnifeBuilder = new PaperKnifeBuilder();
        mPaperKnifeBuilder.dataProvider(cellDataProvider);
        if (cellListenerProvider != null)
            mPaperKnifeBuilder.listenerProvider(cellListenerProvider);
    }

    public void bind(CellElement element, CellViewHolder cellViewHolder) {
        mPaperKnifeBuilder.element(element);
        mPaperKnifeBuilder.into(cellViewHolder);
    }

    @Deprecated
    public static PaperKnifeBuilder map(CellElement element) {
        return new PaperKnifeBuilder(element);
    }

    public static class PaperKnifeBuilder {

        private CellElement mElement;
        private CellListenerProvider mListenerProvider;
        private CellDataProvider mDataProvider;

        private Map<String, Method> mDataProviderMap;
        private Map<String, Method> mListenerProviderMap;

        private PaperKnifeBuilder() {

        }

        private PaperKnifeBuilder(CellElement element) {
            mElement = element;
        }

        public PaperKnifeBuilder dataProvider(CellDataProvider cellDataProvider) {
            mDataProvider = cellDataProvider;
            mDataProviderMap = new HashMap<>();
            Method[] methods = cellDataProvider.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(DataSource.class)) {
                    DataSource dataSourceAnnotation = method.getAnnotation(DataSource.class);
                    String[] sourceIdArray = dataSourceAnnotation.value();
                    for (String sourceId : sourceIdArray)
                        mDataProviderMap.put(sourceId, method);
                }

            }
            return this;
        }

        public void element(CellElement element) {
            mElement = element;
        }

        public PaperKnifeBuilder listenerProvider(CellListenerProvider cellListenerProvider) {
            mListenerProvider = cellListenerProvider;
            mListenerProviderMap = getListenerProviderMap(mListenerProvider);
            return this;
        }

        public void into(CellViewHolder cellViewHolder) {

            if (mDataProvider != null) {
                Method[] methods = cellViewHolder.getClass().getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(DataTarget.class)) {
                        DataTarget dataTargetAnnotation = method.getAnnotation(DataTarget.class);
                        String targetId = dataTargetAnnotation.value();
                        try {
                            Method dataProviderMethod = mDataProviderMap.get(targetId);
                            if (dataProviderMethod == null) {
                                Log.e(TAG, "No data source provided for target: " + targetId);
                            } else {
                                Object data = dataProviderMethod.invoke(mDataProvider, mElement);
                                method.invoke(cellViewHolder, data);
                            }
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG, "Invalid arguments for map: " + targetId);
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                }
            } else {
                Log.e(TAG, "You must provide a data provider object. Method dataProvider(CellDataProvider)");
            }

            if (mListenerProviderMap != null) {
                Field[] declaredFields = cellViewHolder.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (declaredField.isAnnotationPresent(ListenerTarget.class)) {
                        ListenerTarget listenerTargetAnnotation = declaredField
                                .getAnnotation(ListenerTarget.class);
                        String listenerTargetId = listenerTargetAnnotation.value();
                        try {
                            Method listenerProviderMethod = mListenerProviderMap.get(listenerTargetId);
                            Object listenerTarget = declaredField.get(cellViewHolder);
                            Object listener = listenerProviderMethod.invoke(mListenerProvider, mElement);

                            if (listener != null) {
                                Method listenerSetterMethod = getSetterFor(
                                        listenerProviderMethod.getReturnType(), listenerTarget);
                                if (listenerSetterMethod != null) {
                                    listenerSetterMethod.invoke(listenerTarget, listener);
                                } else {
                                    Log.e(TAG, "There is no setter for the listener provided: "
                                            + listenerProviderMethod.getReturnType());
                                }
                            } else {
                                Log.e(TAG, "Cannot get a listener from the source method: " +
                                        listenerProviderMethod.getName());
                            }
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, "Cannot access private fields.");
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        private Method getSetterFor(Class setterClass, Object setterTarget) {

            String setterMethodName = "set" + setterClass.getSimpleName();

            try {
                return setterTarget.getClass().getMethod(setterMethodName, setterClass);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }

        }

        private Map<String, Method> getListenerProviderMap(CellListenerProvider cellListenerProvider) {
            Map<String, Method> listenerProviderMap = new HashMap<>();
            Method[] declaredMethods = cellListenerProvider.getClass().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.isAnnotationPresent(ListenerSource.class)) {
                    ListenerSource listenerTarget = declaredMethod
                            .getAnnotation(ListenerSource.class);
                    String listenerTargetId = listenerTarget.value();
                    Log.d(TAG, "LISTENER TARGET: " + listenerTargetId);
                    listenerProviderMap.put(listenerTargetId, declaredMethod);

                }
            }
            return listenerProviderMap;
        }

    }


}
