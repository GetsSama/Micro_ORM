package edu.zhuravlev.sql.micro_orm.properties;

import java.util.Map;
import java.util.Set;

public interface CommonProperties {
    String getProperty(String propertyName);
    Set<String> getPropertiesSet();
}
