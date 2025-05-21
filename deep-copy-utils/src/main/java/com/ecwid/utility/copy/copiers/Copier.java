package com.ecwid.utility.copy.copiers;


import com.ecwid.utility.copy.CopyUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Predicate;

public interface Copier<T> {

    Unsafe UNSAFE = unsafe();

    Predicate<Class> isAssignable();
    T copy(T original, HashMap<Object, Object> existObjectMap);

    default Object createNewObject(Class clazz) {
        try {
            Constructor constructor;
            try {
                constructor = clazz.getConstructor((Class[]) null);
            } catch (Exception ex) {
                constructor = clazz.getDeclaredConstructor((Class[]) null);
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (IllegalArgumentException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            try {
                return UNSAFE.allocateInstance(clazz);
            } catch (InstantiationException ex) {
                throw new RuntimeException("Error constructing instance of class: " + clazz.getName(), e);
            }
        }
    }

    private static Unsafe unsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.trySetAccessible();
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
