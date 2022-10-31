package edu.zhuravlev.sql.micro_orm.keeper;


import edu.zhuravlev.sql.micro_orm.entity_metadata.EntityMetaData;

import java.sql.Connection;
import java.util.List;

public interface EntityKeeper {
    void save(Object entity, EntityMetaData entityData, Connection connection);

    void saveAll(List<Object> entityList, EntityMetaData entityData, Connection connection);

    void update(Object entity, EntityMetaData entityData, Connection connection);

    Object read(String id, EntityMetaData entityData, Connection connection);

    List<Object> readAll(EntityMetaData entityData, Connection connection);

    void delete(Object entity, EntityMetaData entityData, Connection connection);

    void deleteAll(List<Object> deletedEntities, EntityMetaData entityData, Connection connection);

    void dropTable(EntityMetaData entityData, Connection connection);
}
