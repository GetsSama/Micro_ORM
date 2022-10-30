package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.List;

public class EntityKeeperWrapper implements EntityKeeper {
    private static EntityKeeperWrapper thisInstance;

    private EntityKeeperWrapper(){};

    public static EntityKeeper createEntityKeeperWrapper() {
        if (thisInstance == null)
            thisInstance = new EntityKeeperWrapper();
        return thisInstance;
    }

    //---------------------------------------------------------------------------------------------------
    // It is hardcode realization for "suitableImpl" method.
    // A more functional and flexible implementation is needed in the future.
    private EntityKeeper suitableImpl(EntityMetaData entityData){return SimpleEntityKeeperImpl.create();}
    //---------------------------------------------------------------------------------------------------

    @Override
    public void save(Object entity, EntityMetaData entityData, Connection connection) {
        suitableImpl(entityData).save(entity, entityData, connection);
    }

    @Override
    public void saveAll(List<Object> entityList, EntityMetaData entityData, Connection connection) {
        suitableImpl(entityData).saveAll(entityList, entityData, connection);
    }

    @Override
    public void update(Object entity, EntityMetaData entityData, Connection connection) {
        suitableImpl(entityData).update(entity, entityData, connection);
    }

    @Override
    public Object read(String id, EntityMetaData entityData, Connection connection) {
        Object result;
        result = suitableImpl(entityData).read(id, entityData, connection);
        return result;
    }

    @Override
    public List<Object> readAll(EntityMetaData entityData, Connection connection) {
        List<Object> result;
        result = suitableImpl(entityData).readAll(entityData, connection);
        return result;
    }

    @Override
    public void delete(Object entity, EntityMetaData entityData, Connection connection) {
        suitableImpl(entityData).delete(entity, entityData, connection);
    }

    @Override
    public void deleteAll(List<Object> deletedEntities, EntityMetaData entityData, Connection connection) {
        suitableImpl(entityData).deleteAll(deletedEntities, entityData, connection);
    }

    @Override
    public void dropTable(EntityMetaData entityData, Connection connection) {
        suitableImpl(entityData).dropTable(entityData, connection);
    }
}
