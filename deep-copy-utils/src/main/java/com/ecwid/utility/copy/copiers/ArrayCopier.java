package com.ecwid.utility.copy.copiers;

import com.ecwid.utility.copy.CopyUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

public class ArrayCopier implements Copier<Object[]> {

    @Override
    public Predicate<Class> isAssignable() {
        return Class::isArray;
    }

    @Override
    public Object[] copy(Object[] original, HashMap<Object, Object> existObjectMap) {
        return Arrays.stream(original)
                .map(o -> CopyUtils.copy(o, existObjectMap))
                .toArray();
    }
}
