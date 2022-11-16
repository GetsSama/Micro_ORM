package edu.zhuravlev.sql.micro_orm.entity_tools;

public class EntityAnnotationProcessorFactory {
    private static final EntityAnnotationProcessor simpleProcessor = SimpleEntityAnnotationProcessor.getEntityAnnotationProcessor();

    private EntityAnnotationProcessorFactory() {}

    // As long as it exists just one simple realisation
    public static EntityAnnotationProcessor getEntityAnnotationProcessor() {
        return simpleProcessor;
    }
}
