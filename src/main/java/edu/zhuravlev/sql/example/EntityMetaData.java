package edu.zhuravlev.sql.example;

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
