package edu.zhuravlev.sql.micro_orm.db_connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {
    Connection getConnection() throws SQLException;
    void closeAll() throws SQLException;
}
