package com.ecwid.utility.copy.copiers;

import com.ecwid.utility.copy.CopyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class MapCopier implements Copier<Map> {

    private static final Logger LOG = Logger.getLogger(DefaultCopier.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    public Predicate<Class> isAssignable() {
        return Map.class::isAssignableFrom;
    }

    @Override
    public Map copy(Map original, HashMap<Object, Object> existObjectMap) {
        Map copy;
        try {
            copy = original.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            LOG.warning("Class " + original.getClass() + " is not instanciable");
            return original;
        }
        for (var o : original.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            copy.put(CopyUtils.copy(entry.getKey(), existObjectMap), CopyUtils.copy(entry.getValue(), existObjectMap));
        }
        return copy;
    }
}
