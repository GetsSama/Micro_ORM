package edu.zhuravlev.sql.example;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EntityKeeperCreator {
    public static List<EntityKeeper<?>> createEKList(List<Class<?>> entities, Connection connection) {
        List<EntityKeeper<?>> allKeepers = new ArrayList<>(entities.size());

        for (var clazz : entities)
            allKeepers.add(KeeperFactory.createEntityKeeper(clazz, connection));

        return allKeepers;
    }
}
