package edu.zhuravlev.sql.example;

import java.lang.reflect.Field;
import java.util.*;

class EntityAnalyser {
    private EntityAnalyser(){};

    public static String[] getFieldsName(Class entityClass) {
        Objects.requireNonNull(entityClass);
        String[] names;
        Field[] fields = {};
        fields = entityClass.getDeclaredFields();

        if (fields.length != 0) {
            names = new String[fields.length];

            for (int i=0; i<fields.length; i++)
                names[i] = fields[i].getName();

            return names;
        } else {
            throw new RuntimeException("Given class without fields!");
        }
    }

    public static String[] getFieldsType(Class entityClass) {
        Objects.requireNonNull(entityClass);
        String[] types;
        Field[] fields = {};
        fields = entityClass.getDeclaredFields();

        if (fields.length != 0) {
            types = new String[fields.length];

            for (int i=0; i< fields.length; i++)
                types[i] = fields[i].getType().getSimpleName();

            return types;
        } else {
            throw new RuntimeException("Given class without fields!");
        }
    }

    public static Map<String, String> getFieldsNameAndType(Class entityClass){
        Objects.requireNonNull(entityClass);

        Field[] fields = {};
        fields = entityClass.getDeclaredFields();

        if (fields.length != 0) {
            Map<String, String> nameAndType = new LinkedHashMap<>((int) (fields.length/0.8));

            for (var field : fields)
                nameAndType.put(field.getName(), field.getType().getSimpleName());

            return nameAndType;
        } else {
            throw new RuntimeException("Given class without fields!");
        }
    }
}
