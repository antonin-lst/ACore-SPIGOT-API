package fr.acore.spigot.storage.database;

import fr.acore.spigot.api.storage.database.DBUser;
import fr.acore.spigot.api.storage.database.IDatabase;
import fr.acore.spigot.api.storage.database.driver.DatabaseDriver;
import fr.acore.spigot.api.storage.database.exception.ConnectionException;
import fr.acore.spigot.api.storage.database.exception.NotConnectedException;
import fr.acore.spigot.api.storage.exception.schema.SchemaNotFounException;
import fr.acore.spigot.storage.schema.SqLiteSchema;

import java.sql.Connection;
import java.util.List;

public class SqLiteDatabase implements IDatabase<SqLiteSchema> {

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void setHost(String host) {

    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public void setPort(int port) {

    }

    @Override
    public DBUser getDbUser() {
        return null;
    }

    @Override
    public void setDbUser(DBUser user) {

    }

    @Override
    public void connect() throws ConnectionException {

    }

    @Override
    public void disconect() throws NotConnectedException {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

    @Override
    public DatabaseDriver getDriver() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<SqLiteSchema> getSchemas() {
        return null;
    }

    @Override
    public SqLiteSchema getSchema(String name) throws SchemaNotFounException {
        return null;
    }

    @Override
    public void addSchema(SqLiteSchema schema) {

    }

    @Override
    public void addSchema(String name) {

    }

    @Override
    public void setDefaultSchema(String name) throws SchemaNotFounException {

    }

    @Override
    public SqLiteSchema getDefaultSchema() {
        return null;
    }
}
