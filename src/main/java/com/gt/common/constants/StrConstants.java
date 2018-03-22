package com.gt.common.constants;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class StrConstants {
    public static final String APP_TITLE = "GT - Inventory Mgmt System";
    public static final String COMPANY_NAME = "A Company";
    public static final String DEPARTMENT = "Department";

    /**
     * @return map of all declared `static final` fields, to be used by @{@link com.gt.common.ResourceManager}
     */
    public static Map<String, String> getMap() {
        Map<String, String> mp = new HashMap<>();

        Class<StrConstants> c = StrConstants.class;
        for (Field f : c.getDeclaredFields()) {
            int mod = f.getModifiers();

            if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && Modifier.isFinal(mod)) {
                try {
                    mp.put(f.getName(), f.get(null).toString());
                } catch (IllegalAccessException e) {
                    //shouldn't throw
                    e.printStackTrace();
                }
            }
        }

        return mp;
    }

    public static void main(String[] args) {
        System.out.println(new StrConstants().getMap().get("APP_TITLE"));

    }
}
