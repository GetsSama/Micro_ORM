package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.*;

class EntityKeeperImpl implements EntityKeeper {

    private final Map<String, String> fieldsNameAndType;
    private final Connection connection;
    private final String tableName;

    private boolean isDBHaveMapping;


    public EntityKeeperImpl(Map<String, String> fieldsNameAndType, Connection connection, String tableName) {
        Objects.requireNonNull(fieldsNameAndType);
        Objects.requireNonNull(connection);
        Objects.requireNonNull(tableName);

        this.fieldsNameAndType = fieldsNameAndType;
        this.connection = connection;
        this.tableName = tableName;

        isDBHaveMapping = SQLUtils.isDBContainsTable(connection, tableName);
    }


    @Override
    public void save(Object entity) {
        System.out.println(SQLCreator.getCreateStatement(tableName,fieldsNameAndType));
    }

    @Override
    public void save(List<Object> entityList) {

    }

    @Override
    public void update(Object entity) {
        var updated = new HashMap<>(Map.of("name", "Kolya", "email", "zurik.new"));
    }

    @Override
    public Object read(String id) {
        return null;
    }

    @Override
    public List<Object> readAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(List<String> deletedEntities) {

    }

    @Override
    public void dropTable() {

    }

    @Override
    public String toString() {
        return "EntityKeeperImpl{" +
                "fieldsNameAndType=" + fieldsNameAndType +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
