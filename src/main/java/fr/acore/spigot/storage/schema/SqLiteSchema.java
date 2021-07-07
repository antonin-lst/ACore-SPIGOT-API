package fr.acore.spigot.storage.schema;

import fr.acore.spigot.api.storage.database.IDatabase;
import fr.acore.spigot.api.storage.schema.ISchema;
import fr.acore.spigot.storage.table.SqLiteTable;

import java.sql.Connection;
import java.util.List;

public class SqLiteSchema implements ISchema<SqLiteTable> {
    @Override
    public IDatabase<?> getDataBase() {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public List<SqLiteTable> getTables() {
        return null;
    }

    @Override
    public SqLiteTable getTable(String name) {
        return null;
    }

    @Override
    public void addTable(SqLiteTable table) {

    }

    @Override
    public void createTable(Class<?> tableClazz) {

    }

    @Override
    public void createTable(SqLiteTable table) {

    }

    @Override
    public void drop(String tableName) {

    }

    @Override
    public void drop() {

    }
}
