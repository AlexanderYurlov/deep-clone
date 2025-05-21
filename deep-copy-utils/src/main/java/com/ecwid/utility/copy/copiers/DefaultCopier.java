package com.ecwid.utility.copy.copiers;

import com.ecwid.utility.copy.CopyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class DefaultCopier<T> implements Copier<T> {

    private static final Logger LOG = Logger.getLogger(DefaultCopier.class.getName());

    @Override
    public Predicate<Class> isAssignable() {
        return c -> true;
    }

    @Override
    public T copy(T original, HashMap<Object, Object> existObjectMap) {
        if (existObjectMap.containsKey(original)) {
            return (T) existObjectMap.get(original);
        }
        T copy;
        var clazz = original.getClass();
        copy = (T) createNewObject(clazz);
        existObjectMap.put(original, copy);
        var fields = getFields(clazz);
        copyFields(fields, original, copy, existObjectMap);
        return copy;
    }

    private void copyFields(List<Field> fields, T original, T copy, HashMap<Object, Object> existObjectMap) {
        try {
            for (Field field : fields) {
                if (!field.trySetAccessible()) {
                    LOG.warning("field " + field.getName() + " is not accessible");
                    continue;
                }
                var obj = field.get(original);
                if (obj != null) {
                    var copiedField = CopyUtils.copy(obj, existObjectMap);
                    field.set(copy, copiedField);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private List<Field> getFields(Class clazz) {
        var fieldList = new ArrayList<Field>();
        while (clazz != null) {
            var fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    fieldList.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

}
