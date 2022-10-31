package edu.zhuravlev.sql.micro_orm.resources_manager;

import java.io.*;
import java.net.URL;

public class ResourcesAnalyzer {
    private ResourcesAnalyzer(){}
    public static String getSearchArea() {
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
