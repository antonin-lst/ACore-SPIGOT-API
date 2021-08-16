package fr.acore.spigot.api.menu;

public class MenuSize {

    private int rows, colones;
    public int getRows() { return this.rows;}

    public MenuSize(int rows){
        this(rows, 9);
    }

    public MenuSize(int rows, int colones) {
        this.rows = rows;
        this.colones = colones;
    }

    public int getSize() {
        if(rows > 1) return rows * 9;

        return colones;
    }

}
