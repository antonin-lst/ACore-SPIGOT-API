package fr.acore.spigot.api.storage.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	public String columnName() default "";
	public boolean primary() default false;
	public String foreign() default "";
	public boolean nullable() default true;
	public String defaultValue() default "";
	public boolean isUnique() default false;
	public boolean isAutoIncrement() default false;
	
}
