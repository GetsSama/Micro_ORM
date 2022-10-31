package edu.zhuravlev.sql.micro_orm.entity_metadata;

import java.util.Map;

public interface EntityMetaData {
    Map<String, String> getFieldsNameAndType ();
    String getTableName ();
    String getIdFieldName();
    Class<?> getEntityClass();
    boolean isTableExist();
    void tableCreated();
    void tableDropped();
}
