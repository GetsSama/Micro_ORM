package edu.zhuravlev.sql.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static edu.zhuravlev.sql.example.SQLUtils.*;

class EntityKeeperImpl implements EntityKeeper {

    private final Map<String, String> fieldsNameAndType;
    private final Connection connection;
    private final String tableName;
    private final Class thisEntityClass;
    private boolean isDBHaveMapping;


    public EntityKeeperImpl(Class entityClass, Map<String, String> fieldsNameAndType, Connection connection, String tableName) {
        Objects.requireNonNull(fieldsNameAndType);
        Objects.requireNonNull(connection);
        Objects.requireNonNull(tableName);

        this.fieldsNameAndType = fieldsNameAndType;
        this.connection = connection;
        this.tableName = tableName;

        thisEntityClass = entityClass;
        isDBHaveMapping = SQLUtils.isDBContainsMapping(connection,thisEntityClass);
    }

    private int create() {
        try(Statement statement = connection.createStatement()) {
            String sqlStatement = SQLCreator.getCreateStatement(tableName, fieldsNameAndType);
            System.out.println("Generated SQL -----> " + sqlStatement);
            int upd = statement.executeUpdate(sqlStatement);
            isDBHaveMapping = true;
            return upd;
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    private void typeCheck(Object o) {
        Objects.requireNonNull(o);
        Class objClass = o.getClass();
        if(!Objects.equals(objClass, thisEntityClass))
            throw new RuntimeException("Uncorrected entity object type!");
    }

    @Override
    public void save(Object entity) {
        typeCheck(entity);

        if (!isDBHaveMapping)
            create();

        String[] values = EntityAnalyser.getValues(entity, fieldsNameAndType);
        String insertSQL = SQLCreator.getInsertStatement(tableName, fieldsNameAndType, values);
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
    public void saveAll(List<Object> entityList) {
        for (var entity : entityList)
            typeCheck(entity);

        if(!isDBHaveMapping)
            create();

        int batchLimit = 20;
        try(Statement statement = connection.createStatement()) {
            int counter = 0;
            String[] values;
            String insertedSQL;
            for (var entity : entityList) {
                values = EntityAnalyser.getValues(entity, fieldsNameAndType);
                insertedSQL = SQLCreator.getInsertStatement(tableName, fieldsNameAndType, values);
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
    public void update(Object entity) {
        typeCheck(entity);
        if(!isDBHaveMapping)
            throw new RuntimeException("DB don't have mapping for this entity");

        List<String> valuesEntity = Arrays.asList(EntityAnalyser.getValues(entity, fieldsNameAndType));
        Map<String, String> fieldAndValue = new LinkedHashMap<>();
        Iterator<String> valIter = valuesEntity.iterator();

        for(var field : fieldsNameAndType.keySet())
            fieldAndValue.put(field, valIter.next());


        Object equivalentEntity = read(fieldAndValue.get("id"));
        List<String> valuesEntityInDB = Arrays.asList(EntityAnalyser.getValues(equivalentEntity, fieldsNameAndType));
        Iterator<String> oldIter = valuesEntityInDB.iterator();
        Iterator<String> newIter = valuesEntity.iterator();

        for(var pair : fieldAndValue.entrySet()){
            String oldValue = oldIter.next();
            String newValue = newIter.next();
            if(!oldValue.equals(newValue))
                pair.setValue(newValue);
        }

        try(Statement statement = connection.createStatement()) {
            String updateSQL = SQLCreator.getUpdateStatement(tableName, fieldAndValue, fieldAndValue.get("id"));
            printGeneratedSQL(updateSQL);
            int updRows = statement.executeUpdate(updateSQL);
            printExecutedResult(updRows, "updated");
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object read(String id) {
        if(!isDBHaveMapping)
            throw new RuntimeException("DB don't have mapping for this entity");

        Object entityInstance;
        try {
            entityInstance = thisEntityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try(Statement statement = connection.createStatement()) {
            String selectSQL = SQLCreator.getSelectStatement(tableName, id);
            printGeneratedSQL(selectSQL);
            ResultSet resultSet = statement.executeQuery(selectSQL);

            if(resultSet.next())
                EntityAnalyser.setFields(entityInstance, resultSet, fieldsNameAndType);

        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        return entityInstance;
    }

    @Override
    public List<Object> readAll() {
        if(!isDBHaveMapping)
            throw new RuntimeException("DB don't have mapping for this entity");

        Object entityInstance;
        List<Object> entitiesAll = new LinkedList<>();

        try(Statement statement = connection.createStatement()) {
            String readAllSQL = "SELECT * FROM " + tableName;
            printGeneratedSQL(readAllSQL);
            ResultSet resultSet = statement.executeQuery(readAllSQL);

            while (resultSet.next()) {
                try {
                    entityInstance = thisEntityClass.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                EntityAnalyser.setFields(entityInstance, resultSet, fieldsNameAndType);
                entitiesAll.add(entityInstance);
            }

        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }

        return entitiesAll;
    }

    @Override
    public void delete(Object entity) {
        typeCheck(entity);
        if(!isDBHaveMapping)
            throw new RuntimeException("DB don't have mapping for this entity");

        String idValue = EntityAnalyser.getId(entity);
        String deleteSQL = SQLCreator.getDeleteStatement(tableName, idValue);
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
    public void deleteAll(List<Object> deletedEntities) {
        for(var entity : deletedEntities)
            typeCheck(entity);

        if(!isDBHaveMapping)
            throw new RuntimeException("DB don't have mapping for this entity");

        int batchLimit = 20;
        try(Statement statement = connection.createStatement()) {
            int counter = 0;
            String[] values;
            String insertedSQL;
            for (var entity : deletedEntities) {
                String idValue = EntityAnalyser.getId(entity);
                String deleteSQL = SQLCreator.getDeleteStatement(tableName, idValue);
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
    public void dropTable() {
        try(Statement statement = connection.createStatement()) {
            String dropSQL = SQLCreator.getDropStatement(tableName);
            printGeneratedSQL(dropSQL);
            statement.executeUpdate(dropSQL);
            System.out.println("Drop " + tableName + " table");
            isDBHaveMapping = false;
            KeeperPool.removeMapping(thisEntityClass);
        } catch (SQLException e) {
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "EntityKeeperImpl{" +
                "fieldsNameAndType=" + fieldsNameAndType +
                ", tableName='" + tableName + '\'' +
                ", thisEntityClass=" + thisEntityClass +
                '}';
    }
}
