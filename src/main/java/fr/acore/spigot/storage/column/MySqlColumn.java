package fr.acore.spigot.storage.column;

import fr.acore.spigot.api.storage.column.Column;
import fr.acore.spigot.api.storage.column.IColumn;
import fr.acore.spigot.api.storage.column.foreign.ManyToOne;
import fr.acore.spigot.api.storage.column.type.ColumnType;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.api.storage.utils.CustomSize;
import fr.acore.spigot.storage.table.MySqlTable;

import java.lang.reflect.Field;

public class MySqlColumn implements IColumn {

    private MySqlTable table;

    private String name;
    private ColumnType type;
    private CustomSize size;
    private Field field;
    private Column column;
    private ManyToOne manyToOne;

    public MySqlColumn(MySqlTable table, String name, ColumnType type, CustomSize size){
        this.table = table;
        this.name = name;
        this.type = type;
        this.size = size;
    }

    @Override
    public ITable getTable() {
        return table;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ColumnType getType() {
        return type;
    }

    @Override
    public CustomSize getSize() {
        return size;
    }

    @Override
    public void setSize(CustomSize size) {
        this.size = size;
    }

    @Override
    public Field getInjectedField() {
        return this.field;
    }

    @Override
    public void injectField(Field columnField) {
        this.field = columnField;
        if(field.getDeclaredAnnotation(Column.class) != null) {
            this.column = field.getDeclaredAnnotation(Column.class);
        }else if(field.getDeclaredAnnotation(ManyToOne.class) != null){
            this.manyToOne = field.getDeclaredAnnotation(ManyToOne.class);
        }

    }

    @Override
    public boolean isInjected() {
        return field != null;
    }

    @Override
    public boolean isPrimary() {
        return (column != null && column.primary()) || (manyToOne != null && manyToOne.primary());
    }

    @Override
    public boolean isForeign() {
        return manyToOne != null;
    }

    @Override
    public String getForeign() {
        if(!isForeign()) return "";
        return manyToOne.foreign();
    }

    @Override
    public boolean isNullable() {
        if(isForeign()) return manyToOne.nullable();
        return column != null && column.nullable();
    }

    @Override
    public String defaultValue() {
        if(isForeign()) return manyToOne.defaultValue();
        return column != null ? column.defaultValue() : "";
    }

    @Override
    public boolean isUnique() {
        if(isForeign()) return false;
        return column != null && column.isUnique();
    }

    @Override
    public boolean isAutoIncrement() {
        if(isForeign()) return false;
        return column != null && column.isAutoIncrement();
    }

    @Override
    public <T> Object values(T obj) {
        Object o = null;
        try {
            field.setAccessible(true);
            if(manyToOne != null) {
                String[] datas = manyToOne.foreign().split("\\.");
                ITable targetTable = getTable().getSchema().getTable(datas[0]);
                IColumn targetColumn = targetTable.getColumn(datas[1]);
                o = targetColumn.values(field.get(obj));
            }else {
                o = field.get(obj);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return o;
    }

}
