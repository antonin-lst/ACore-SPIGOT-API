package fr.azefgh456.acore.commands.cmds;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.command.CommandSender;

import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.commands.utils.ICommand;

public class RestartCommand extends ICommand {
	
	public RestartCommand() {
		setAlliases(Arrays.asList("rl", "restart"));
	}
	
	@Override
	public CommandStats performCommand(CommandSender sender, String... args) {
		System.out.println("restarting");
		
		Thread restartHook = new Thread() {
			@Override
            public void run()
            {
				
				try {
					System.out.println("tentative de restart");
					Runtime.getRuntime().exec( new String[] {"sh", "restart.sh faction ElapsedSpigot.jar"});
					System.out.println("script executer");
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		};
		restartHook.start();
		
		System.out.println("its end of the fucking life");
		//org.spigotmc.RestartCommand.restart();
		return CommandStats.SUCCESS;
	}

	@Override
	public String getPermission() {
		return "acore.restart";
	}

	@Override
	public String getSyntax() {
		return "/reload or /rl";
	}

}
