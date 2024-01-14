package com.tth.service.util.common;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("StaticCacheUtils")
public class StaticCacheUtils {
    private static Map<String, Object> map = new ConcurrentHashMap<>();

    // 添加数据到指定类型的集合

    public static <T> void put(String key, T data) {
        map.put(key, data);
    }

    // 移除数据

    public static void remove(String key) {
        Object obj = map.get(key);
        if (obj != null) {
            map.remove(key);
        }
    }

    public static void removeByKeyPrefix(String partialKey) {
        for (String key : map.keySet()) {
            if (key.contains(partialKey)) {
                map.remove(key);
            }
        }
    }

    // 获取指定类型的数据集合的副本
    public static <T> T get(String key, Class<T> clazz) {
        Object obj = map.get(key);
        if (obj != null) {
            if (clazz.isInstance(obj)) {
                return clazz.cast(obj);
            }
        }
        return null;
    }

}
