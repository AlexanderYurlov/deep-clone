package com.ecwid.utility.copy.copiers;

import com.ecwid.utility.copy.CopyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class CollectionCopier implements Copier<Collection> {

    private static final Logger LOG = Logger.getLogger(DefaultCopier.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    public Predicate<Class> isAssignable() {
        return Collection.class::isAssignableFrom;
    }

    @Override
    public Collection copy(Collection original, HashMap<Object, Object> existObjectMap) {
        Collection copy;
        try {
            copy = original.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            LOG.warning("Class " + original.getClass() + " is not instanciable");
            return original;
        }
        for (var o : original) {
            copy.add(CopyUtils.copy(o, existObjectMap));
        }
        return copy;
    }
}
