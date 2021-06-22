package fr.azefgh456.acore.commands.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.config.manager.ConfigManager;

public class CommandReload extends ICommand{

	private ConfigManager configM;
	
	public CommandReload(ConfigManager configM) {
		this.configM = configM;
		addAlliases("rl");
	}
	
	@Override
	public CommandStats performCommand(CommandSender sender, String... args) {
		configM.reloadConfig();
		if(sender instanceof Player)
			sender.sendMessage("Configuration reload");
		return CommandStats.SUCCESS;
	}

	@Override
	public String getPermission() {
		return "acore.reload";
	}

	@Override
	public String getSyntax() {
		return "/acore:rl or acore:reload";
	}

}
