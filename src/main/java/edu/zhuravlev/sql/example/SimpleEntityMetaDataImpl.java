package edu.zhuravlev.sql.example;

import java.util.Map;
import java.util.Objects;

public class SimpleEntityMetaDataImpl implements EntityMetaData{
    private final Map<String, String> fieldsNameAndType;
    private final String tableName;
    private final String idFieldName;
    private final Class<?> entityClass;
    private boolean isTableExist;

    public SimpleEntityMetaDataImpl(Map<String, String> fieldsNameAndType, String tableName, String idFieldName, Class<?> entityClass, boolean isTableExist) {
        this.fieldsNameAndType = fieldsNameAndType;
        this.tableName = tableName;
        this.idFieldName = idFieldName;
        this.entityClass = entityClass;
        this.isTableExist = isTableExist;
    }

    @Override
    public Map<String, String> getFieldsNameAndType() {
        return fieldsNameAndType;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getIdFieldName() {
        return idFieldName;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public boolean isTableExist() {
        return isTableExist;
    }

    @Override
    public void tableCreated() {
        isTableExist = true;
    }

    @Override
    public void tableDropped() {
        isTableExist = false;
    }
}
