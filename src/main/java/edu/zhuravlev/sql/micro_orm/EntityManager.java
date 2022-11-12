package edu.zhuravlev.sql.micro_orm;

import edu.zhuravlev.sql.micro_orm.db_connection.ConnectionManager;
import edu.zhuravlev.sql.micro_orm.db_connection.ConnectionManagerFactory;
import edu.zhuravlev.sql.micro_orm.entity_metadata.EntityMetaData;
import edu.zhuravlev.sql.micro_orm.entity_metadata.MetaDataPool;
import edu.zhuravlev.sql.micro_orm.entity_metadata.MetaDataPoolInitializer;
import edu.zhuravlev.sql.micro_orm.entity_tools.EntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.entity_tools.SimpleEntityAnnotationProcessor;
import edu.zhuravlev.sql.micro_orm.keeper.EntityKeeper;
import edu.zhuravlev.sql.micro_orm.keeper.EntityKeeperWrapper;
import edu.zhuravlev.sql.micro_orm.sql_tools.SQLUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityManager implements UtilEntityManager{
    private static EntityManager thisInstance;
    private final EntityKeeper entityKeeper;
    private final ConnectionManager connectionManager;
    private final EntityAnnotationProcessor annotationProcessor;

    private EntityManager() {
        this.entityKeeper = EntityKeeperWrapper.createEntityKeeperWrapper();
        this.connectionManager = ConnectionManagerFactory.createConnectionManager();
        this.annotationProcessor = SimpleEntityAnnotationProcessor.getEntityAnnotationProcessor();
        try {
            MetaDataPoolInitializer.fillThePool(annotationProcessor, connectionManager.getConnection());
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }
    public static EntityManager createEntityManager() {
        if (thisInstance == null)
            thisInstance = new EntityManager();
        return thisInstance;
    }

    @Override
    public void save(Object entity) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entity);
         try (Connection connection = connectionManager.getConnection()) {
             entityKeeper.save(entity, entityMetaData, connection);
         } catch (SQLException e) {
             SQLUtils.printSQLException(e);
             throw new RuntimeException(e);
         }
    }

    @Override
    public void saveAll(List<Object> entityList) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityList.get(0));
        try(Connection connection = connectionManager.getConnection()) {
            entityKeeper.saveAll(entityList, entityMetaData, connection);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object entity) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entity);
        try(Connection connection = connectionManager.getConnection()) {
            entityKeeper.update(entity, entityMetaData, connection);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T read(String id, Class<T> entityClass) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityClass);
        try(Connection connection = connectionManager.getConnection()) {
            Object result = entityKeeper.read(id, entityMetaData, connection);
            return entityClass.cast(result);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> readAll(Class<T> entityClass) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityClass);
        try(Connection connection = connectionManager.getConnection()) {
            List<Object> results = entityKeeper.readAll(entityMetaData, connection);
            List<T> typedResults = new ArrayList<>(results.size());
            for (Object elem : results)
                typedResults.add(entityClass.cast(elem));
            return typedResults;
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Object entity) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entity);
        try(Connection connection = connectionManager.getConnection()) {
            entityKeeper.delete(entity, entityMetaData, connection);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(List<Object> deletedEntities) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(deletedEntities.get(0));
        try(Connection connection = connectionManager.getConnection()) {
            entityKeeper.deleteAll(deletedEntities, entityMetaData, connection);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable(Class<?> entityClass) {
        EntityMetaData entityMetaData = MetaDataPool.getMetaData(entityClass);
        try(Connection connection = connectionManager.getConnection()) {
            entityKeeper.dropTable(entityMetaData, connection);
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void freeResources() {
        try {
            connectionManager.closeAll();
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
