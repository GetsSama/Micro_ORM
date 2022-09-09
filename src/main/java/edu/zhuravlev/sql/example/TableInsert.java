package edu.zhuravlev.sql.example;

import java.sql.*;
import java.util.Map;
import java.util.Objects;

public class TableInsert {
    private final String tableName;
    private Map<String, String> tableSchema;
    private final Connection connection;

    private String insertSQLTemplate;

    private void initialize(Connection connection, String tableName) {

    }

    public TableInsert(Connection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }




    private String generateQueryByTableName(){
        return null;
    }
}
