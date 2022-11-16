package edu.zhuravlev.sql.micro_orm.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zhuravlev.sql.micro_orm.properties.CommonProperties;
import edu.zhuravlev.sql.micro_orm.properties.DatabaseProperties;
import edu.zhuravlev.sql.micro_orm.properties.ORMProperties;

import java.io.*;
import java.net.URL;

public class ResourcesAnalyzer {
    private static CommonProperties dbProperties;
    private static CommonProperties ormProperties;

    private ResourcesAnalyzer(){}

    public static CommonProperties getDBProperties() {
        if (dbProperties == null) {
            String defaultResource = "db_properties.json";
            ObjectMapper objectMapper = new ObjectMapper();
            CommonProperties properties;

            URL resource = Thread.currentThread().getContextClassLoader().getResource(defaultResource);

            if (resource == null) {
                throw new IllegalArgumentException("Unable to get resource '" + defaultResource + "' Are you sure the resources exists?");
            }

            try {
                properties = objectMapper.readValue(resource, DatabaseProperties.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dbProperties = properties;
        }

        return dbProperties;
    }

    public static CommonProperties getOrmProperties() {
        if (ormProperties == null) {
            String defaultResource = "orm_properties.json";
            ObjectMapper objectMapper = new ObjectMapper();
            CommonProperties properties;

            URL resource = Thread.currentThread().getContextClassLoader().getResource(defaultResource);

            if (resource == null) {
                throw new IllegalArgumentException("Unable to get resource '" + defaultResource + "' Are you sure the resources exists?");
            }

            try {
                properties = objectMapper.readValue(resource, ORMProperties.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ormProperties = properties;
        }

        return ormProperties;
    }


}
