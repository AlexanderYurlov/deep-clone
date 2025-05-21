package com.ecwid.utility.copy.copiers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleCopier<T> implements Copier<T> {

    enum PrimitiveWrapperType {
        BOOLEAN(Boolean.class.getName()),
        CHARACTER(Character.class.getName()),
        BYTE(Byte.class.getName()),
        SHORT(Short.class.getName()),
        INTEGER(Integer.class.getName()),
        LONG(Long.class.getName()),
        FLOAT(Float.class.getName()),
        DOUBLE(Double.class.getName()),
        VOID(Void.class.toString());
        private final String className;

        private static final Set<String> PRIMITIVE_WRAPERS_SET = Arrays.stream(PrimitiveWrapperType.values())
                .map(primitiveWrapperType -> primitiveWrapperType.className)
                .collect(Collectors.toSet());

        PrimitiveWrapperType(String className) {
            this.className = className;
        }

        static boolean isPrimitiveWrapperType(Class clazz) {
            return PRIMITIVE_WRAPERS_SET.contains(clazz.getName());
        }

    }

    @Override
    public Predicate<Class> isAssignable() {
        return clazz -> clazz.isPrimitive() ||
                PrimitiveWrapperType.isPrimitiveWrapperType(clazz) ||
                clazz.isEnum() ||
                clazz.isRecord() ||
                clazz.isSynthetic() ||
                clazz == String.class;
    }

    @Override
    public T copy(T original, HashMap<Object, Object> existObjectMap) {
        return original;
    }

}
