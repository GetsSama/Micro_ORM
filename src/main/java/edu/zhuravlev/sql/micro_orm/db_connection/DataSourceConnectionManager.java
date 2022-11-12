package edu.zhuravlev.sql.micro_orm.db_connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

class DataSourceConnectionManager implements ConnectionManager{
    private static DataSource thisDataSource;

    private static ConnectionManager thisInst;

    private DataSourceConnectionManager() {}

    public static ConnectionManager create(DataSource dataSource) {
        if (thisInst == null) {
            thisInst = new DataSourceConnectionManager();
            thisDataSource = dataSource;
        }
        return thisInst;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return thisDataSource.getConnection();
    }

    @Override
    public void closeAll() throws SQLException {
    }
}
