package edu.zhuravlev.sql.micro_orm.db_connection;

import edu.zhuravlev.sql.micro_orm.CommonProperties;

import java.util.HashMap;
import java.util.Map;

class DatabaseProperties implements CommonProperties {
    private String url;
    private String user;
    private String password;
    private String dbDriver;

    DatabaseProperties() {}

    DatabaseProperties(String url, String user, String password, String dbDriver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dbDriver = dbDriver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setUrl(String url) {
        if (!url.isEmpty())
            this.url = url;
        else
            throw new RuntimeException("Setting property 'url' can't be empty!");
    }

    public void setUser(String user) {
        if (!user.isEmpty())
            this.user = user;
        else
            throw new RuntimeException("Setting property 'user' can't be empty!");
    }

    public void setPassword(String password) {
        if (!password.isEmpty())
            this.password = password;
        else
            throw new RuntimeException("Setting property 'password' can't be empty!");
    }

    public void setDbDriver(String dbDriver) {
        if (!dbDriver.isEmpty())
            this.dbDriver = dbDriver;
        else
            throw new RuntimeException("Setting property 'dbDriver' can't be empty!");
    }

    @Override
    public Map<String, String> getPropertiesMap() {
        Map<String, String> propMap = new HashMap<>();
        propMap.put("url", url);
        propMap.put("user", user);
        propMap.put("password", password);
        propMap.put("dbDriver", dbDriver);
        return propMap;
    }

    @Override
    public String toString() {
        return "DatabaseProperties{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", dbDriver='" + dbDriver + '\'' +
                '}';
    }
}
