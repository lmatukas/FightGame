package main.Services.Impl;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CacheTest {

    Cache cache = Cache.getInstance();

    @Test
    public void getInsertedObject() {
        String key = "test";
        Integer integer = new Integer(12);
        cache.put(key, integer);
        Object object = cache.get(key);
        assertTrue(object != null);
    }

    @Test
    public void removeInsertedObject() {
        String key2 = "test";
        Integer integer2 = new Integer(13);
        cache.remove(key2);
        assertTrue(cache.get(key2)==null);
    }
}

