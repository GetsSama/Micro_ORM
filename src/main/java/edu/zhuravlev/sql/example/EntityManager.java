package edu.zhuravlev.sql.example;

import java.util.List;

public class EntityManager implements UtilEntityManager{
    private static EntityManager thisInstance;
    private final EntityKeeper entityKeeper;
    private final ConnectionManager connectionManager;

    public EntityManager() {
        this.entityKeeper = EntityKeeperWrapper.createEntityKeeperWrapper();
        this.connectionManager = SimpleConnectionManagerImpl.create();

    }

    @Override
    public void save(Object entity) {

    }

    @Override
    public void saveAll(List<Object> entityList) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public <T> T read(String id, Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> List<T> readAll(Class<T> entityClass) {
        return null;
    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void deleteAll(List<Object> deletedEntities) {

    }

    @Override
    public void dropTable(Class<?> entityClass) {

    }
}
