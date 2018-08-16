package main.Services.Helpers;

import java.lang.reflect.Field;

public final class NotNullOrEmpty {

    private NotNullOrEmpty(){}

    public static <T> boolean paramsNotNullOrEmpty(T model) {

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            try {
                if (field.get(model) != null && fieldType.equals(String.class)) {
                    String a = (String) field.get(model);
                    if (a.isEmpty()) {
                        return false;
                    }
                } else if (field.get(model) == null) {
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
        return true;
    }
}
