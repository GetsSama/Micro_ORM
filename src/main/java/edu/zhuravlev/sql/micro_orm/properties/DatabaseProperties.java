package edu.zhuravlev.sql.micro_orm.properties;

import java.util.HashMap;
import java.util.Map;

class DatabaseProperties extends AbstractProperties {
    private String url;
    private String user;
    private String password;
    private String dbDriver;

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
    Map<String, String> getPropertiesMap() {
        if (properties == null) {
            properties = new HashMap<>();
            properties.put("url", url);
            properties.put("user", user);
            properties.put("password", password);
            properties.put("dbDriver", dbDriver);
        }
        return properties;
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
