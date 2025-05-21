package com.ecwid.utility.copy;


import com.ecwid.utility.copy.copiers.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CopyUtils {

    private static final List<Copier> COPIERS = new LinkedList<>() {{
        add(new SimpleCopier());
        add(new ArrayCopier());
        add(new CollectionCopier());
        add(new MapCopier());
        add(new DefaultCopier());
    }};

    public static <T> T copy(T original) {
        return copy(original, new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public static <T> T copy(T original, HashMap<Object, Object> existObjectMap) {
        if (original == null) {
            return null;
        }
        var clazz = original.getClass();
        return (T) COPIERS.stream()
                .filter(p -> p.isAssignable().test(clazz))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No copier for " + clazz.getName()))
                .copy(original, existObjectMap);
    }

}
