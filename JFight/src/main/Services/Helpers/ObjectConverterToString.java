package main.Services.Helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjectConverterToString {

    public static <T> List<Map<String, String>> convertList(List<T> listOfObjects) {
        List<Map<String, String>> list = new ArrayList<>();
        listOfObjects.forEach(ob -> list.add(convertObject(ob)));
        return list;
    }

    public static <T> Map<String, String> convertObject(T object) {
        try {
            Map<String, String> map = new HashMap<>();
            Class<?> objectClass = object.getClass();
            for (Field field : objectClass.getDeclaredFields()) {
                String name = field.getName();
                field.setAccessible(true);
                String value = String.valueOf(field.get(object));
                map.put(name, value);
            }
            return map;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
