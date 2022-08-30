package org.apache.struts.faces.util;

import java.util.Map;

/**
 * This class has some static utils-methods.
 *
 * @author Stefan Graff
 *
 * @since Struts 1.4.1
 */
public class Utils {

    /**
     * Returns the value of a map as expected class.
     *
     * @param <T> the expected class
     * @param clazz the expected class
     * @param map the map with the values
     * @param key the key to access the map
     *
     * @return the value as expected class
     *
     * @throws ClassCastException if the key is of an
     *     inappropriate type for this map or if the
     *     value is not null and is not assignable to
     *     the type T.
     * @throws NullPointerException if the specified key
     *     is null and this map does not permit null keys.
     */
    public static <T> T getMapValue(Class<T> clazz, Map<String, Object> map, String key) {
        final Object ret = map.get(key);
        return clazz.cast(ret);
    }

}