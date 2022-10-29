package edu.zhuravlev.sql.example;

import java.sql.Connection;

public class SimpleConnectionManagerImpl implements ConnectionManager {
    private static SimpleConnectionManagerImpl thisInstance;

    private SimpleConnectionManagerImpl(){}

    public static SimpleConnectionManagerImpl create() {
        if (thisInstance == null)
            thisInstance = new SimpleConnectionManagerImpl();
        return thisInstance;
    }

    @Override
    public Connection getConnection() {
        return TmpConnectionRealization.getConnection();
    }

    @Override
    public void returnConnection(Connection connection) {
        TmpConnectionRealization.close();
    }
}
