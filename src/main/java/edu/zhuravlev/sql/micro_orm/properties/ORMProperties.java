package edu.zhuravlev.sql.micro_orm.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

public class ORMProperties extends AbstractProperties{
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        if (!scope.isEmpty())
            this.scope = scope;
        else
            throw new RuntimeException("Setting property 'scope' can't be empty!");
    }

    @Override
    public Map<String, String> getPropertiesMap() {
        if (properties == null) {
            properties = new HashMap<>();
            properties.put("scope", scope);
        }
        return properties;
    }
}
