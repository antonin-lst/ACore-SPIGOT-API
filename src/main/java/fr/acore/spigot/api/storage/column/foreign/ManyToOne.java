package fr.acore.spigot.api.storage.column.foreign;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.acore.spigot.api.storage.column.type.ColumnType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ManyToOne{

	public boolean primary() default false;
	public String foreign();
	public ColumnType type() default ColumnType.INTEGER;
	public String columnName() default "";
	public boolean nullable() default true;
	public String defaultValue() default "";
	
}
