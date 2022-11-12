package edu.zhuravlev.sql.micro_orm.db_connection;

import javax.sql.DataSource;

public class ConnectionManagerFactory {
    private ConnectionManagerFactory() {};

    public static ConnectionManager createConnectionManager() {
        return SimpleConnectionManager.create();
    }

    public static ConnectionManager createConnectionManager(DataSource dataSource) {
        return DataSourceConnectionManager.create(dataSource);
    }
}
