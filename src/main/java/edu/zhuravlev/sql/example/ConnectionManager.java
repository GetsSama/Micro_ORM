package edu.zhuravlev.sql.example;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
    void returnConnection(Connection connection);
}
