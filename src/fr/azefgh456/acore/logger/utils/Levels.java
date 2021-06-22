package fr.azefgh456.acore.logger.utils;

public enum Levels {

	INFO("§7[INFO] §f"),
	WARN("§c[WARN] §f"),
	ERR("§4[ERREUR] §f");
	
	private String desc;
	
	Levels(String desc){
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
}
