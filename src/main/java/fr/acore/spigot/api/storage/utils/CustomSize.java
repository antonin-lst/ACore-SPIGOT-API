package fr.acore.spigot.api.storage.utils;

import java.util.ArrayList;
import java.util.List;

public class CustomSize {

	private Integer[] sizeEntry;
	
	public CustomSize(Integer... sizeEntry) {
		this.sizeEntry = sizeEntry;
	}
	
	public Integer[] getSizeEntry() {
		return this.sizeEntry;
	}

	public static CustomSize fromSqlType(String cType) {
		String size = cType.split("\\(")[1].replace(")", "");
		if(size.contains(",")) {
			String[] sizesString = size.split(",");
			List<Integer> sizes = new ArrayList<>();
			for(String s : sizesString) sizes.add(Integer.parseInt(s));
			return new CustomSize(sizes.toArray(new Integer[0]));
		}else {
			return new CustomSize(Integer.parseInt(size));
		}
		
	}

	public String toSql() {
		StringBuilder builder = new StringBuilder("");
		int i = 1;
		for(Integer sz : sizeEntry) {
			builder.append(sz);
			if(i < sizeEntry.length) builder.append(",");
			i++;
		}
		
		return builder.toString();
	}
	
	
}
