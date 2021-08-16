package fr.acore.spigot.commands.cmds;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.commands.ACommandSenderCommand;
import fr.acore.spigot.config.manager.ConfigManager;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CommandReload extends ACommandSenderCommand {

    private ACoreSpigotAPI instance;

    public CommandReload(ACoreSpigotAPI instance){
        super("reload");
        setAlliases(Arrays.asList("rl"));
    }

    @Override
    public CommandStats performCommand(ICommandSender<CommandSender> sender, String... args) {
        instance.getManager(ConfigManager.class).reload();
        sender.getSender().sendMessage("Configuration reload");

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
