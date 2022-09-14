package edu.zhuravlev.sql.example;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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

        isDBHaveMapping = SQLUtils.isDBContainsTable(connection, tableName);
        thisEntityClass = entityClass;
    }

    private int create() {
        try(Statement statement = connection.createStatement()) {
            String sqlStatement = SQLCreator.getCreateStatement(tableName, fieldsNameAndType);
            System.out.println("Generated SQL -----> " + sqlStatement);
            return statement.executeUpdate(sqlStatement);
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
        System.out.println("Generated SQL -----> " + insertSQL);

        try (Statement statement = connection.createStatement()) {
            int updRows = statement.executeUpdate(insertSQL);
            System.out.println("Execute: inserted " + updRows + " rows");
        } catch (SQLException e) {
            SQLUtils.printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(List<Object> entityList) {
        System.out.println(SQLCreator.getInsertStatement(tableName, fieldsNameAndType, new String[] {"2", "Sveta", "mathandmath", "RU", "secret"}));
    }

    @Override
    public void update(Object entity) {
        var updated = new HashMap<>(Map.of("name", "Kolya", "email", "zurik.new"));
        System.out.println(SQLCreator.getUpdateStatement(tableName, updated, "1"));
    }

    @Override
    public Object read(String id) {
        System.out.println(SQLCreator.getSelectStatement(tableName, "1"));
        return null;
    }

    @Override
    public List<Object> readAll() {
        return null;
    }

    @Override
    public void delete(String id) {
        System.out.println(SQLCreator.getDeleteStatement(tableName, "2"));
    }

    @Override
    public void delete(List<String> deletedEntities) {

    }

    @Override
    public void dropTable() {
        System.out.println(SQLCreator.getDropStatement(tableName));
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
