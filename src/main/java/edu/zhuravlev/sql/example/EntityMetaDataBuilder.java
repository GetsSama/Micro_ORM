package edu.zhuravlev.sql.example;

import java.util.Map;
import java.util.Objects;

public class EntityMetaDataBuilder {
    private Map<String, String> fieldsNameAndType;
    private String tableName;
    private String idFieldName;
    private Class<?> entityClass;
    private boolean isTableExist;
    private boolean flag = false;

    public EntityMetaDataBuilder() {}

    public void addFieldsNameAndType(Map<String, String> fieldsNameAndType) {
        if (this.fieldsNameAndType == null)
            this.fieldsNameAndType = Objects.requireNonNull(fieldsNameAndType);
        else
            throw new IllegalArgumentException("This builder already have parameter FieldsNameAndType!");
    }

    public void addTableName(String tableName) {
        if (this.tableName == null)
            this.tableName = Objects.requireNonNull(tableName);
        else
            throw new IllegalArgumentException("This builder already have parameter TableName!");
    }

    public void addIdFieldName(String idFieldName) {
        if (this.idFieldName == null)
            this.idFieldName = Objects.requireNonNull(idFieldName);
        else
            throw new IllegalArgumentException("This builder already have parameter IdFieldName!");
    }

    public void addEntityClass(Class<?> entityClass) {
        if (this.entityClass == null) {
            Objects.requireNonNull(entityClass);
            if (EntityAnnotationProcessor.isEntityClass(entityClass))
                this.entityClass = entityClass;
            else
                throw new IllegalArgumentException("Given: " + entityClass.getName() + "; Isn't Entity class!");
        } else
            throw new IllegalArgumentException("This builder already have parameter EntityClass!");
    }

    public void addIsTableExist(boolean isTableExist) {
        if (!flag) {
            this.isTableExist = isTableExist;
            flag = true;
        } else
            throw new IllegalArgumentException("This builder already have parameter IsTableExist!");
    }

    public EntityMetaData built() {
        if (correctObjectCheck())
            return new SimpleEntityMetaDataImpl(fieldsNameAndType, tableName, idFieldName, entityClass, isTableExist);
        else
            throw new RuntimeException("Uncompleted EntityMetaData object!");
    }

    private boolean correctObjectCheck() {
        return  this.fieldsNameAndType != null &&
                this.tableName != null &&
                this.idFieldName != null &&
                this.entityClass != null &&
                this.flag;
    }
}
