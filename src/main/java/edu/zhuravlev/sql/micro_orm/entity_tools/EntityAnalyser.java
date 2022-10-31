package edu.zhuravlev.sql.micro_orm.entity_tools;

import edu.zhuravlev.sql.micro_orm.sql_tools.SQLUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.*;

public class EntityAnalyser {
    private EntityAnalyser(){};

    private static String getterName(String fieldName) {
        return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }

    private static String setterName(String fieldName) {
        return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }

    public static String[] getFieldsName(Class<?> entityClass) {
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

    public static String[] getFieldsType(Class<?> entityClass) {
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

    public static Map<String, String> getFieldsNameAndType(Class<?> entityClass){
        Objects.requireNonNull(entityClass);

        Field[] fields = entityClass.getDeclaredFields();

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
        Objects.requireNonNull(o);
        String[] values = new String[fieldsNameAndType.size()];
        //String[] methodsName = new String[fieldsNameAndType.size()];
        int counter = 0;

        for (String fieldName : fieldsNameAndType.keySet()) {
            String methodName = getterName(fieldName);
            try {
                Object value = o.getClass().getMethod(methodName).invoke(o);
                values[counter] = value.toString();
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        return values;
    }

    public static String getId(Object o, String idName) {
        Objects.requireNonNull(o);
        try {
            String idGetter = getterName(idName);
            Object value = o.getClass().getMethod(idGetter).invoke(o);
            return value.toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFields(Object o, ResultSet resultSet, Map<String, String> fieldsNameAndType) {
        Objects.requireNonNull(o);
        Objects.requireNonNull(resultSet);
        Objects.requireNonNull(fieldsNameAndType);

        try {
            int counter = 1;
            for(var pair : fieldsNameAndType.entrySet()) {
                Method rsMethod = SQLUtils.getResultSetReadMethod(pair.getValue());
                Object value = rsMethod.invoke(resultSet, counter);
                String setterName = setterName(pair.getKey());
                o.getClass().getMethod(setterName, SQLUtils.getClassByType(pair.getValue())).invoke(o, value);
                counter++;
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
