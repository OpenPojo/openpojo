package com.openpojo.reflection.cache;

import java.util.Map;
import java.util.WeakHashMap;

import com.openpojo.reflection.PojoClass;

/**
 * This is the Cache to hold references for PojoClasses, to prevent looking them up over and over.
 * @author oshoukry
 */
public class PojoCache {
    private static Map<String, PojoClass> pojoClassCache = new WeakHashMap<String, PojoClass>();

    /**
     * Retrieve an implementation from Cache.
     * 
     * @param name
     *            Fully Qualified Class Name.
     * @return
     *         Cached PojoReference, or null if none found.
     */
    public static PojoClass getPojoClass(String name) {
        return pojoClassCache.get(name);
    }

    /**
     * Add a PojoClass definition to the Cache.
     * 
     * @param name
     * @param pojoClass
     *            The entry to add to the cache.
     */
    public static void addPojoClass(String name, PojoClass pojoClass) {
        // Ensure that we don't have a "Strong" reference to the key in the map, otherwise no cleanup will occur.
        pojoClassCache.put(new String(name), pojoClass);
    }
}
