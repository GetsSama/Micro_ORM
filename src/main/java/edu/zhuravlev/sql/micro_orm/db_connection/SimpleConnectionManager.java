package edu.zhuravlev.sql.micro_orm.db_connection;

import edu.zhuravlev.sql.micro_orm.properties.CommonProperties;
import edu.zhuravlev.sql.micro_orm.properties.ResourcesAnalyzer;
import edu.zhuravlev.sql.micro_orm.sql_tools.SQLUtils;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

class SimpleConnectionManager implements ConnectionManager{
    private static ConnectionManager thisInst;
    private final CommonProperties properties = ResourcesAnalyzer.getDBProperties();
    private Connection connectionInst;
    private ConnWrapper connectionWrapper;
    private SimpleConnectionManager(){};

    public static ConnectionManager create() {
        if (thisInst == null)
            thisInst = new SimpleConnectionManager();
        return thisInst;
    }

    @Override
    public Connection getConnection() {
        if (connectionInst == null) {
            createConnect();
            connectionWrapper = new ConnWrapper(connectionInst);
        }
        return connectionWrapper;
    }

    @Override
    public void closeAll() throws SQLException {
        connectionInst.close();
        System.out.println("Connection close!");
    }

    private void createConnect() {
        try {
            Class.forName(properties.getProperty("dbDriver"));
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("password");

            connectionInst = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection with DB successful!");
        } catch (ClassNotFoundException | SQLException e) {
            if (e instanceof SQLException)
                SQLUtils.printSQLException((SQLException)e);
            throw new RuntimeException(e);
        }
    }

    class ConnWrapper implements Connection {
        private Connection thisCon;

        ConnWrapper(Connection connection) {
            thisCon = connection;
        }

        @Override
        public Statement createStatement() throws SQLException {
            return thisCon.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return thisCon.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return thisCon.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return thisCon.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            thisCon.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return thisCon.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            thisCon.commit();
        }

        @Override
        public void rollback() throws SQLException {
            thisCon.rollback();
        }

        @Override
        public void close() throws SQLException {
        }

        @Override
        public boolean isClosed() throws SQLException {
            return thisCon.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return thisCon.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            thisCon.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return thisCon.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            thisCon.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return thisCon.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            thisCon.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return thisCon.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return thisCon.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            thisCon.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return thisCon.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return thisCon.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return thisCon.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return thisCon.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            thisCon.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            thisCon.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return thisCon.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return thisCon.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return thisCon.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            thisCon.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            thisCon.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return thisCon.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return thisCon.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return thisCon.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return thisCon.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return thisCon.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return thisCon.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return thisCon.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return thisCon.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return thisCon.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return thisCon.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return thisCon.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            thisCon.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            thisCon.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return thisCon.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return thisCon.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return thisCon.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return thisCon.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            thisCon.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return thisCon.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            thisCon.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            thisCon.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return thisCon.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return thisCon.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return thisCon.isWrapperFor(iface);
        }
    }
}
