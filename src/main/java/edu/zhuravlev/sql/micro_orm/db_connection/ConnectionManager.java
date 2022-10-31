package edu.zhuravlev.sql.micro_orm.db_connection;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
    void returnConnection(Connection connection);
}
