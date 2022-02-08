package com.test.learnkotlin.utils;

import androidx.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflexUtil2 {
    public static <T> T getTypeInstance(@NonNull Object origin, @NonNull Class<?> clazz) {
        return getTypeInstance(origin.getClass(), clazz);
    }

    public static <T> T getTypeInstance(@NonNull Class<?> origin, @NonNull Class<?> clazz) {
        Class<?> targetClass = getTargetClass(origin, clazz);
        if (targetClass != null) {
            try {
                @SuppressWarnings("unchecked")
                T instance = (T) targetClass.newInstance();
                return instance;
            } catch (IllegalAccessException | InstantiationException | ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> Class<T> getTargetClass(@NonNull Object origin, @NonNull Class<T> clazz) {
        return getTargetClass(origin.getClass(), clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getTargetClass(@NonNull Class<?> origin, @NonNull Class<T> clazz) {
        try {
            Class<T> target = null;
            Type genericSuperclass = origin.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                for (Type type : actualTypeArguments) {
                    if (type instanceof Class && clazz.isAssignableFrom((Class<T>) type)) {
                        target = (Class<T>) type;
                        break;
                    }
                }
            }
            return target;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
