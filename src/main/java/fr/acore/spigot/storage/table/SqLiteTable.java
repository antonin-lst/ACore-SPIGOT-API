package fr.acore.spigot.storage.table;

import fr.acore.spigot.api.storage.column.IColumn;
import fr.acore.spigot.api.storage.column.foreign.ModificationConstraintType;
import fr.acore.spigot.api.storage.constraint.Constraint;
import fr.acore.spigot.api.storage.constraint.ConstraintType;
import fr.acore.spigot.api.storage.constraint.query.IQueryConstraint;
import fr.acore.spigot.api.storage.constraint.query.by.IByConstraint;
import fr.acore.spigot.api.storage.constraint.query.column.IColumnQueryConstraint;
import fr.acore.spigot.api.storage.schema.ISchema;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.api.storage.table.OneToManyCollection;

import java.sql.Connection;
import java.util.List;

public class SqLiteTable implements ITable {
    @Override
    public ISchema<?> getSchema() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Class<?> getRessourceClass() {
        return null;
    }

    @Override
    public boolean isInjected() {
        return false;
    }

    @Override
    public void inject(Class<?> ressourceClazz) {

    }

    @Override
    public void addColumn(IColumn column) {

    }

    @Override
    public void setColumn(List<IColumn> columns) {

    }

    @Override
    public List<IColumn> getColumns() {
        return null;
    }

    @Override
    public IColumn getColumn(String name) {
        return null;
    }

    @Override
    public List<IColumn> getPrimaryKey() {
        return null;
    }

    @Override
    public List<IColumn> getForeignKey() {
        return null;
    }

    @Override
    public List<OneToManyCollection> getOneToManyCollections() {
        return null;
    }

    @Override
    public void addOneToManyCollection(OneToManyCollection oneToManyColletion) {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public <T> void insert(T obj) {

    }

    @Override
    public <T> void delete(T obj) {

    }

    @Override
    public <T> void delete(T obj, Constraint<? extends ConstraintType> constraint) {

    }

    @Override
    public <T> void delete(T obj, Constraint<? extends ConstraintType> constraint, ModificationConstraintType deleteType) {

    }

    @Override
    public void truncate() {

    }

    @Override
    public <T> List<T> select(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> List<T> select(Class<T> clazz, List<IByConstraint> byConstraints) {
        return null;
    }

    @Override
    public <T> List<T> select(Class<T> clazz, IColumnQueryConstraint columnConstraint, List<IByConstraint> byConstraints) {
        return null;
    }

    @Override
    public <T> List<T> select(Class<T> clazz, IQueryConstraint queryConstraint) {
        return null;
    }

    @Override
    public <T> List<T> select(Class<T> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints) {
        return null;
    }

    @Override
    public <T> List<T> select(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint) {
        return null;
    }

    @Override
    public List<?> select(Class<?> clazz, Object parent) {
        return null;
    }

    @Override
    public List<?> select(Class<?> clazz, List<IByConstraint> byConstraints, Object parent) {
        return null;
    }

    @Override
    public List<?> select(Class<?> clazz, IQueryConstraint queryConstraint, Object parent) {
        return null;
    }

    @Override
    public List<?> select(Class<?> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints, Object parent) {
        return null;
    }

    @Override
    public List<?> select(Class<?> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints, Object parent) {
        return null;
    }

    @Override
    public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint) {
        return null;
    }

    @Override
    public <T> T selectFirst(Class<T> clazz, IQueryConstraint queryConstraint) {
        return null;
    }

    @Override
    public <T> T selectFirst(Class<T> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints) {
        return null;
    }

    @Override
    public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, List<IByConstraint> byConstraints) {
        return null;
    }

    @Override
    public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint) {
        return null;
    }

    @Override
    public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints) {
        return null;
    }

    @Override
    public <T> void update(T obj) {

    }

    @Override
    public <T> void update(T obj, IColumnQueryConstraint columnConstraint) {

    }

    @Override
    public <T> void update(T obj, IQueryConstraint queryConstraint) {

    }

    @Override
    public <T> void update(T obj, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint) {

    }
}
