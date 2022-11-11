package edu.zhuravlev.sql.micro_orm.properties;

import java.util.Map;
import java.util.Set;

abstract class AbstractProperties implements CommonProperties{
    protected Map<String, String> properties;

    @Override
    public String getProperty(String propertyName) {
        return getPropertiesMap().get(propertyName);
    }

    @Override
    public Set<String> getPropertiesSet() {
        return getPropertiesMap().keySet();
    }

    abstract Map<String, String> getPropertiesMap();
}
