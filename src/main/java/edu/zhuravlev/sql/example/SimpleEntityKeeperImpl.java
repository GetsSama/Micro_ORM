package edu.zhuravlev.sql.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static edu.zhuravlev.sql.example.SQLUtils.*;

class SimpleEntityKeeperImpl implements EntityKeeper {

    private SimpleEntityKeeperImpl(){};

    private int create(EntityMetaData entityData, Connection connection) {
        try(Statement statement = connection.createStatement()) {
            String sqlStatement = SQLCreator.getCreateStatement(entityData.getTableName(), entityData.getFieldsNameAndType());
            System.out.println("Generated SQL -----> " + sqlStatement);
            int upd = statement.executeUpdate(sqlStatement);
            entityData.tableCreated();
            return upd;
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    //Old method, uses when app was without generics
//    private void typeCheck(Object o) {
//        Objects.requireNonNull(o);
//        Class<?> objClass = o.getClass();
//        if(!Objects.equals(objClass, thisEntityClass))
//            throw new RuntimeException("Uncorrected entity object type!");
//    }

    @Override
    public void save(Object entity, EntityMetaData entityData, Connection connection) {
        //typeCheck(entity);
        if (!entityData.isTableExist())
            create(entityData, connection);

        String[] values = EntityAnalyser.getValues(entity, entityData.getFieldsNameAndType());
        String insertSQL = SQLCreator.getInsertStatement(entityData.getTableName(), entityData.getFieldsNameAndType(), values);
        printGeneratedSQL(insertSQL);

        try (Statement statement = connection.createStatement()) {
            int updRows = statement.executeUpdate(insertSQL);
            printExecutedResult(updRows, "inserted");
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Object> entityList, EntityMetaData entityData, Connection connection) {
       /* for (var entity : entityList)
            typeCheck(entity);*/

        if(!entityData.isTableExist())
            create(entityData, connection);

        int batchLimit = 20;
        try(Statement statement = connection.createStatement()) {
            int counter = 0;
            String[] values;
            String insertedSQL;
            for (var entity : entityList) {
                values = EntityAnalyser.getValues(entity, entityData.getFieldsNameAndType());
                insertedSQL = SQLCreator.getInsertStatement(entityData.getTableName(), entityData.getFieldsNameAndType(), values);
                printGeneratedSQL(insertedSQL);
                statement.addBatch(insertedSQL);
                counter++;
                if(counter==batchLimit) {
                    int[] updRows = statement.executeBatch();
                    printExecutedResult(updRows.length, "inserted");
                    counter = 0;
                }
            }
            if(counter != 0) {
                int[] updRows = statement.executeBatch();
                printExecutedResult(updRows.length, "inserted");
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object entity, EntityMetaData entityData, Connection connection) {
        //typeCheck(entity);
        if(!entityData.isTableExist())
            throw new RuntimeException("DB don't have mapping for this entity");

        List<String> valuesEntity = Arrays.asList(EntityAnalyser.getValues(entity, entityData.getFieldsNameAndType()));
        Map<String, String> fieldAndValue = new LinkedHashMap<>();
        Iterator<String> valIter = valuesEntity.iterator();

        for(var field : entityData.getFieldsNameAndType().keySet())
            fieldAndValue.put(field, valIter.next());


        Object equivalentEntity = read(fieldAndValue.get(entityData.getIdFieldName()), entityData, connection);
        List<String> valuesEntityInDB = Arrays.asList(EntityAnalyser.getValues(equivalentEntity, entityData.getFieldsNameAndType()));
        Iterator<String> oldIter = valuesEntityInDB.iterator();
        Iterator<String> newIter = valuesEntity.iterator();

        for(var pair : fieldAndValue.entrySet()){
            String oldValue = oldIter.next();
            String newValue = newIter.next();
            if(!oldValue.equals(newValue))
                pair.setValue(newValue);
        }

        try(Statement statement = connection.createStatement()) {
            String updateSQL = SQLCreator.getUpdateStatement(entityData.getTableName(), fieldAndValue, fieldAndValue.get(entityData.getIdFieldName()));
            printGeneratedSQL(updateSQL);
            int updRows = statement.executeUpdate(updateSQL);
            printExecutedResult(updRows, "updated");
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object read(String id, EntityMetaData entityData, Connection connection) {
        if(!entityData.isTableExist())
            throw new RuntimeException("DB don't have mapping for this entity");

        Object entityInstance;
        try {
            entityInstance = entityData.getEntityClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try(Statement statement = connection.createStatement()) {
            String selectSQL = SQLCreator.getSelectStatement(entityData.getTableName(), id);
            printGeneratedSQL(selectSQL);
            ResultSet resultSet = statement.executeQuery(selectSQL);

            if(resultSet.next())
                EntityAnalyser.setFields(entityInstance, resultSet, entityData.getFieldsNameAndType());

        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        return entityInstance;
    }

    @Override
    public List<Object> readAll(EntityMetaData entityData, Connection connection) {
        if(!entityData.isTableExist())
            throw new RuntimeException("DB don't have mapping for this entity");

        Object entityInstance;
        List<Object> entitiesAll = new LinkedList<>();

        try(Statement statement = connection.createStatement()) {
            String readAllSQL = "SELECT * FROM " + entityData.getTableName();
            printGeneratedSQL(readAllSQL);
            ResultSet resultSet = statement.executeQuery(readAllSQL);

            while (resultSet.next()) {
                try {
                    entityInstance = entityData.getEntityClass().getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                EntityAnalyser.setFields(entityInstance, resultSet, entityData.getFieldsNameAndType());
                entitiesAll.add(entityInstance);
            }

        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        return entitiesAll;
    }

    @Override
    public void delete(Object entity, EntityMetaData entityData, Connection connection) {
        //typeCheck(entity);
        if(!entityData.isTableExist())
            throw new RuntimeException("DB don't have mapping for this entity");

        String idValue = EntityAnalyser.getId(entity, entityData.getIdFieldName());
        String deleteSQL = SQLCreator.getDeleteStatement(entityData.getTableName(), idValue);
        printGeneratedSQL(deleteSQL);

        try(Statement statement = connection.createStatement()) {
            int updRows = statement.executeUpdate(deleteSQL);
            printExecutedResult(updRows, "deleted");
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(List<Object> deletedEntities, EntityMetaData entityData, Connection connection) {
        /*for(var entity : deletedEntities)
            typeCheck(entity);*/

        if(!entityData.isTableExist())
            throw new RuntimeException("DB don't have mapping for this entity");

        int batchLimit = 20;
        try(Statement statement = connection.createStatement()) {
            int counter = 0;
            String[] values;
            String insertedSQL;
            for (var entity : deletedEntities) {
                String idValue = EntityAnalyser.getId(entity, entityData.getIdFieldName());
                String deleteSQL = SQLCreator.getDeleteStatement(entityData.getTableName(), idValue);
                printGeneratedSQL(deleteSQL);
                statement.addBatch(deleteSQL);
                counter++;
                if(counter==batchLimit) {
                    int[] updRows = statement.executeBatch();
                    printExecutedResult(updRows.length, "deleted");
                    counter = 0;
                }
            }
            if(counter != 0) {
                int[] updRows = statement.executeBatch();
                printExecutedResult(updRows.length, "deleted");
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable(EntityMetaData entityData, Connection connection) {
        try(Statement statement = connection.createStatement()) {
            String dropSQL = SQLCreator.getDropStatement(entityData.getTableName());
            printGeneratedSQL(dropSQL);
            statement.executeUpdate(dropSQL);
            System.out.println("Drop " + entityData.getTableName() + " table");
            entityData.tableDropped();
            //KeeperPool.removeMapping(thisEntityClass);
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
