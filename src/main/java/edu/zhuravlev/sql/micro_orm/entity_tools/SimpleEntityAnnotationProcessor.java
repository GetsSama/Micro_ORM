package edu.zhuravlev.sql.micro_orm.entity_tools;

import edu.zhuravlev.sql.micro_orm.annotations.Entity;
import edu.zhuravlev.sql.micro_orm.annotations.Id;
import edu.zhuravlev.sql.micro_orm.properties.ResourcesAnalyzer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SimpleEntityAnnotationProcessor implements EntityAnnotationProcessor{
    private static SimpleEntityAnnotationProcessor thisInstance;

    private SimpleEntityAnnotationProcessor(){}

    public static EntityAnnotationProcessor getEntityAnnotationProcessor() {
        if (thisInstance == null)
            thisInstance = new SimpleEntityAnnotationProcessor();
        return thisInstance;
    }

    @Override
    public List<Class<?>> getEntityClasses() {
        String context = ResourcesAnalyzer.getOrmProperties().getProperty("scope");
        List<Class<?>> allClassesInContext = EntityClassFinder.find(context);
        List<Class<?>> entityClasses = new ArrayList<>(allClassesInContext.size());

        for (Class<?> clazz : allClassesInContext)
            if (clazz.isAnnotationPresent(Entity.class))
                entityClasses.add(clazz);

        if (!entityClasses.isEmpty())
            return entityClasses;
        else
            throw new RuntimeException("In search area '" + context + "' no Entity classes!");
    }

    @Override
    public String getTableName(Class<?> entityClass) {
        if (EntityAnnotationProcessor.isEntityClass(entityClass)) {
            Entity entityAnno = entityClass.getAnnotation(Entity.class);
            String value = entityAnno.value();
            if (value.equals("default_class_name"))
                return entityClass.getSimpleName();
            else
                return value;
        } else
            throw new RuntimeException("Given class '" + entityClass.getName() + "' no Entity class!");
    }

    @Override
    public String getIdName(Class<?> entityClass) {
        if (EntityAnnotationProcessor.isEntityClass(entityClass)) {
            List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());
            if (fields.isEmpty())
                throw new RuntimeException("Class " + entityClass.getName() + " haven't got any fields!");

            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class))
                    return field.getName();
            }

            throw new RuntimeException("Entity class '" + entityClass.getName() + "' haven't got Id!");
        } else
            throw new RuntimeException("Given class '" + entityClass.getName() + "' no Entity class!");
    }
}
