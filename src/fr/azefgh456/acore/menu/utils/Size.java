package fr.azefgh456.acore.menu.utils;

public class Size {
	
	private int rows, colones;
	public int getRows() { return this.rows;}
	
	public Size(int rows, int colones) {
		this.rows = rows;
		this.colones = colones;
	}
	
	public int getSize() {
		if(rows > 1) return rows * 9;
		
		return colones;
	}

}
