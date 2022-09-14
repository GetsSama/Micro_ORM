package edu.zhuravlev.sql.example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

class EntityAnalyser {
    private EntityAnalyser(){};

    private static String getterName(String fieldName) {
        return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }

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

    public static String[] getValues(Object o, Map<String, String> fieldsNameAndType) {
        String[] values = new String[fieldsNameAndType.size()];
        //String[] methodsName = new String[fieldsNameAndType.size()];
        int counter = 0;

        for (String fieldName : fieldsNameAndType.keySet()) {
            String methodName = getterName(fieldName);
            try {
                Object value = o.getClass().getMethod(methodName).invoke(o);
                values[counter] = value.toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        return values;
    }
}
