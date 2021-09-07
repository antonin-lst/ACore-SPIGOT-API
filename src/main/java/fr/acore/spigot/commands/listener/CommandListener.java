package fr.acore.spigot.commands.listener;

import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.command.ICommandPreform;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.commands.manager.CommandManager;
import fr.acore.spigot.commands.sender.CorePlayerSender;
import fr.acore.spigot.config.Setupable;
import fr.acore.spigot.config.utils.Conf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandListener implements Listener {

    private CommandManager commandManager;

    public CommandListener(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        String cmdComplette = event.getMessage().replace("/", "");
        String cmdBase = cmdComplette.contains(" ") ? cmdComplette.split(" ")[0] : cmdComplette;
        List<String> args = new ArrayList<>();

        if(!cmdBase.equals(cmdComplette)) {
            String data[] = cmdComplette.split(" ");
            for(int i = 1; i < data.length; i++) args.add(data[i]);
        }

        CorePlayer<?> sender = commandManager.getPlugin().getCorePlayer(event.getPlayer());

        for(ICommand forkCommand : commandManager.getForkCommands()) {
            if(forkCommand.getName().equals(cmdBase) || forkCommand.getAlliases().contains(cmdBase)) {
                event.setCancelled(true);
                ICommandPreform performedCommand = commandManager.getArgumentToCommand(forkCommand, args.toArray(new String[0]));
                CommandStats commandStats = performedCommand.getCommand().prePerformCommand(new CorePlayerSender(sender), args.toArray(new String[0]));

                switch (commandStats) {
                    case PERMITION_DENIED:
                        sender.sendMessage(Conf.getPermissionDeniedMessage());
                        break;
                    case ONLY_PLAYER:
                        sender.sendMessage(Conf.getPlayerOnlyMessage());
                        break;
                    case ONLY_CONSOL:
                        sender.sendMessage(Conf.getConsolOnlyMessage());
                        break;
                    case SYNTAX_ERROR:
                        sender.sendMessage(commandManager.replace(Conf.getSyntaxErrorMessage(), "{SYNTAXE}", performedCommand.getCommand().getSyntax() == null ? "Indisponible" : performedCommand.getCommand().getSyntax()));
                        break;
                    case TIMER_CANCEL:
                        //sender.sendMessage(commandManager.replace(Conf.getDelayCancelMessage(), "{DELAY}", ((APlayerCommandTimer) performedCommand.getCommand()).getPlayerTimer((Player)sender).getTimeToString()));
                        break;
                    case SUCCESS:
                        break;
                    case NOT_FORKED:
                        event.setCancelled(false);
                        return;
                    default:
                        break;
                }
            }
        }
    }

}
