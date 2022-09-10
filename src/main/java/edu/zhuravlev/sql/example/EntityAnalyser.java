package edu.zhuravlev.sql.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                types[i] = fields[i].getName();

            return types;
        } else {
            throw new RuntimeException("Given class without fields!");
        }
    }
}
