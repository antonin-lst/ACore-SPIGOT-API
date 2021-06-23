package fr.acore.spigot.api.plugin.module;

import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.version.Version;
import fr.acore.spigot.api.version.Version.ParseVersionException;
import fr.acore.spigot.module.AManager;

public interface IModule extends IPlugin<AManager>{

	public Version getApiVersion() throws ParseVersionException;
	
	public boolean isValidLicence();
	public void setValidLicence();
	
	public boolean isLicenceChecked();
	public void setLicenceChecked();
	
}
