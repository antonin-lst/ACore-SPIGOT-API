package fr.azefgh456.acore.storage.requette.utils.contraint;

public class Contraint {

	private ConstraintValues type;
	private String constraint;
	
	public Contraint(ConstraintValues type, String constraint) {
		this.type = type;
		this.constraint = constraint;
	}
	
	@Override
	public String toString() {
		if(type.getValues().equals(ConstraintValues.WHERE.getValues())) {
			return " " + type.getValues() + " " + constraint;
		}
		return ", CONSTRAINT " + type.getValues() + " (" + constraint + ")";
	}
	
public static enum ConstraintValues{
		
		PRIMARY("Primary Key"),
		FOREIGN("Foreign Key"),
		NOT_NULL("Not Null"),
		WHERE("WHERE");
		
		private String values;
		
		ConstraintValues(String values){
			this.values = values;
		}
		
		public String getValues() {
			return this.values;
		}
		
	}
	
}
