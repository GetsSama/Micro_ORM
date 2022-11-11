package edu.zhuravlev.sql.micro_orm.resources_manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zhuravlev.sql.micro_orm.CommonProperties;

import java.io.*;
import java.net.URL;

public class ResourcesAnalyzer {
    private ResourcesAnalyzer(){}

    public static CommonProperties getDBProperties() {
        String defaultResource = "db_properties.json";
        ObjectMapper objectMapper = new ObjectMapper();
        CommonProperties properties;

        URL resource = Thread.currentThread().getContextClassLoader().getResource(defaultResource);

        if (resource == null) {
            throw new IllegalArgumentException("Unable to get resource '" + defaultResource + "' Are you sure the resources exists?");
        }

        try {
            properties = (CommonProperties) objectMapper.readValue(resource, Class.forName("edu.zhuravlev.sql.micro_orm.db_connection.DatabaseProperties"));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    public static String getEntitySearchArea() {
        String searchAreaPath;
        String defaultResource = "scope.txt";

        URL resource = Thread.currentThread().getContextClassLoader().getResource(defaultResource);

        if (resource == null) {
            throw new IllegalArgumentException("Unable to get resource '" + defaultResource + "' Are you sure the resources exists?");
        }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getFile())))) {
            String path = br.readLine();
            if (path != null)
                searchAreaPath = path;
            else
                throw new RuntimeException("Empty resource file '" + defaultResource + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return searchAreaPath;
    }


}
