package edu.zhuravlev.sql.micro_orm;

import edu.zhuravlev.sql.micro_orm.db_connection.ConnectionManager;
import edu.zhuravlev.sql.micro_orm.db_connection.SimpleConnectionManagerImpl;
import edu.zhuravlev.sql.micro_orm.entity_metadata.EntityMetaData;
import edu.zhuravlev.sql.micro_orm.entity_metadata.MetaDataPool;
import edu.zhuravlev.sql.micro_orm.entity_metadata.MetaDataPoolInitializer;
import edu.zhuravlev.sql.micro_orm.entity_tools.EntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.entity_tools.SimpleEntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.keeper.EntityKeeper;
import edu.zhuravlev.sql.micro_orm.keeper.EntityKeeperWrapper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EntityManager implements UtilEntityManager{
    private static EntityManager thisInstance;
    private final EntityKeeper entityKeeper;
    private final ConnectionManager connectionManager;
    private final EntityAnnotationProcessor annotationProcessor;

    private EntityManager() {
        this.entityKeeper = EntityKeeperWrapper.createEntityKeeperWrapper();
        this.connectionManager = SimpleConnectionManagerImpl.create();
        this.annotationProcessor = SimpleEntityAnnotationProcessor.getEntityAnnotationProcessor();
        MetaDataPoolInitializer.fillThePool(annotationProcessor, connectionManager.getConnection());
    }

    public static EntityManager createEntityManager() {
        if (thisInstance == null)
            thisInstance = new EntityManager();
        return thisInstance;
    }

    @Override
    public void save(Object entity) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entity);
        Connection connection = connectionManager.getConnection();
        entityKeeper.save(entity, entityMetaData, connection);
        connectionManager.returnConnection(connection);
    }

    @Override
    public void saveAll(List<Object> entityList) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityList.get(0));
        Connection connection = connectionManager.getConnection();
        entityKeeper.saveAll(entityList, entityMetaData, connection);
        connectionManager.returnConnection(connection);
    }

    @Override
    public void update(Object entity) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entity);
        Connection connection = connectionManager.getConnection();
        entityKeeper.update(entity, entityMetaData, connection);
        connectionManager.returnConnection(connection);
    }

    @Override
    public <T> T read(String id, Class<T> entityClass) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityClass);
        Connection connection = connectionManager.getConnection();
        Object result = entityKeeper.read(id, entityMetaData, connection);
        connectionManager.returnConnection(connection);
        return entityClass.cast(result);
    }

    @Override
    public <T> List<T> readAll(Class<T> entityClass) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityClass);
        Connection connection = connectionManager.getConnection();
        List<Object> results = entityKeeper.readAll(entityMetaData, connection);
        connectionManager.returnConnection(connection);
        List<T> typedResults = new ArrayList<>(results.size());
        for (Object elem : results)
            typedResults.add(entityClass.cast(elem));
        return typedResults;
    }

    @Override
    public void delete(Object entity) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entity);
        Connection connection = connectionManager.getConnection();
        entityKeeper.delete(entity, entityMetaData, connection);
        connectionManager.returnConnection(connection);
    }

    @Override
    public void deleteAll(List<Object> deletedEntities) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(deletedEntities.get(0));
        Connection connection = connectionManager.getConnection();
        entityKeeper.deleteAll(deletedEntities, entityMetaData, connection);
        connectionManager.returnConnection(connection);
    }

    @Override
    public void dropTable(Class<?> entityClass) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityClass);
        Connection connection = connectionManager.getConnection();
        entityKeeper.dropTable(entityMetaData, connection);
        connectionManager.returnConnection(connection);
    }
}
