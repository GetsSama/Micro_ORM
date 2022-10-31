package edu.zhuravlev.sql.micro_orm.entity_tools;

import edu.zhuravlev.sql.micro_orm.entity_metadata.SimpleEntityMetaDataImpl;

import java.util.List;

public class SimpleEntityAnnotationProcessor implements EntityAnnotationProcessor{
    private static SimpleEntityAnnotationProcessor thisInstance;

    private SimpleEntityAnnotationProcessor(){}

    public static EntityAnnotationProcessor getEntityAnnotationProcessor() {
        if (thisInstance == null)
            thisInstance = new SimpleEntityAnnotationProcessor();
        return thisInstance;
    }

    @Override
    public List<Class<?>> getEntityClasses() {
        return null;
    }

    @Override
    public String getTableName(Class<?> entityClass) {
        return null;
    }

    @Override
    public String getIdName(Class<?> entityClass) {
        return null;
    }
}
