package fr.acore.spigot.commands.utils;

import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.commands.ACommandSenderCommand;
import fr.acore.spigot.commands.sender.ConsoleSender;
import fr.acore.spigot.commands.sender.CorePlayerSender;
import org.bukkit.command.CommandSender;

public abstract class AConsoleCommand extends ACommandSenderCommand {


    public AConsoleCommand(String name) {
        super(name);
    }

    @Override
    public CommandStats performCommand(ICommandSender<CommandSender> sender, String... args) {
        if(sender instanceof ConsoleSender) {
            return performAPlayerCommand((ConsoleSender) sender, args);
        } else{
            return CommandStats.ONLY_CONSOL;
        }
    }

    public abstract CommandStats performAPlayerCommand(ConsoleSender sender, String... args);
}
