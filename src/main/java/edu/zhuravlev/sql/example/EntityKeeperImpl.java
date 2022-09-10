package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

class EntityKeeperImpl implements EntityKeeper {

    private final String[] attributesName;
    private final String[] attributesType;
    private final Connection connection;
    private final String tableName;

    public EntityKeeperImpl(String[] attributesName, String[] attributesType, Connection connection, String tableName) {
        Objects.requireNonNull(attributesName);
        Objects.requireNonNull(attributesType);
        Objects.requireNonNull(connection);
        Objects.requireNonNull(tableName);

        this.attributesName = attributesName;
        this.attributesType = attributesType;
        this.connection = connection;
        this.tableName = tableName;
    }

    private boolean isFirstSave = true;

    @Override
    public void save(Object entity) {

    }

    @Override
    public void save(List<Object> entityList) {

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
}
