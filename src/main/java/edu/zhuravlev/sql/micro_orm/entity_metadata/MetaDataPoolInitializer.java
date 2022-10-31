package edu.zhuravlev.sql.micro_orm.entity_metadata;

import edu.zhuravlev.sql.micro_orm.entity_tools.EntityAnalyser;
import edu.zhuravlev.sql.micro_orm.entity_tools.EntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.sql_tools.SQLUtils;

import java.sql.Connection;
import java.util.List;

public class MetaDataPoolInitializer {
    private MetaDataPoolInitializer() {}

    public static void fillThePool(EntityAnnotationProcessor annotationProcessor, Connection connection) {
        List<Class<?>> entityClasses = annotationProcessor.getEntityClasses();
        for (var clazz : entityClasses) {
            EntityMetaDataBuilder builder = new EntityMetaDataBuilder();
            String tableName = annotationProcessor.getTableName(clazz);
            builder.addEntityClass(clazz);
            builder.addFieldsNameAndType(EntityAnalyser.getFieldsNameAndType(clazz));
            builder.addTableName(tableName);
            builder.addIdFieldName(annotationProcessor.getIdName(clazz));
            builder.addIsTableExist(SQLUtils.isDBContainsMapping(connection, tableName));
        }
    }
}
