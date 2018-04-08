package com.orange.score.common.utils;

import java.lang.reflect.Method;

public class MethodUtil {

    public static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);

        try {
            if (method != null) return method.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
