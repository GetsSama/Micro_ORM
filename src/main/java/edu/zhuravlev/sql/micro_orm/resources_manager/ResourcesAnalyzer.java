package edu.zhuravlev.sql.micro_orm.resources_manager;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ResourcesAnalyzer {
    private ResourcesAnalyzer(){}

    //------------------------------------------------------------------------------------------------------------
    /*
    The path to resource dir and file could search automatically. Now it is hardcode.
    */
    //------------------------------------------------------------------------------------------------------------
    public static String getSearchArea() {
        String searchAreaPath = null;

        //!!!!!!!!!!!!!!!!!!!!!
        String defaultResource = "C:\\Users\\User\\IdeaProjects\\postgresqlCRUD\\src\\main\\resources\\scope.txt";
        //!!!!!!!!!!!!!!!!!!!!!

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(defaultResource)))) {
            String path = br.readLine();
            if (path != null)
                searchAreaPath = path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return searchAreaPath;
    }
}
