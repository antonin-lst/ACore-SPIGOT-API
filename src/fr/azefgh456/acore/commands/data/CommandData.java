package fr.azefgh456.acore.commands.data;

import java.util.Arrays;
import java.util.Map;

import fr.azefgh456.acore.commands.APlayerCommandTimer;
import fr.azefgh456.acore.commands.manager.CommandManager;
import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.storage.requette.sync.IContainRequette;
import fr.azefgh456.acore.storage.requette.sync.IDeleteRequette;
import fr.azefgh456.acore.storage.requette.sync.IInsertRequette;
import fr.azefgh456.acore.storage.requette.sync.ISelectRequette;
import fr.azefgh456.acore.storage.requette.sync.IUpdateRequette;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint.ConstraintValues;
import fr.azefgh456.acore.storage.utils.DataBuilder;
import fr.azefgh456.acore.storage.utils.MultiData;
import fr.azefgh456.acore.utils.time.PlayerTimerBuilder;

public class CommandData extends MultiData<APlayerCommandTimer, String>{

	private CommandManager commandM;
	
	public CommandData(CommandManager commandM) {
		super("CommandStorage", "command VARCHAR(90), uuid VARCHAR(90), current BIGINT(30)", new Contraint(ConstraintValues.PRIMARY, "command, uuid"));
		this.commandM = commandM;
	}
	
	@Override
	public void load() {

	}
	
	public void loadCommand(APlayerCommandTimer command) {
		commandM.logConsol(command.getName());
		DataBuilder data = commandM.executeCustomTypeRequette(new ISelectRequette(storageName, Arrays.asList("uuid", "current"), new Contraint(ConstraintValues.WHERE, "command = ?"), command.getName()));
		
		for(Map<String, Object> entry : data.getDatas()) {
			if(!command.addPlayer((String)entry.get("uuid"), (long)entry.get("current"))) delete(command, (String)entry.get("uuid"));
		}
		
	}

	private void delete(APlayerCommandTimer command, String string) {
		commandM.simpleRequette(new IDeleteRequette(storageName, new Contraint(ConstraintValues.WHERE, "command = ? AND uuid = ?"), Arrays.asList(command.getName(), string)));
	}

	@Override
	public void save() {
		for(ICommand cmd : commandM.getCommands()) {
			if(cmd instanceof APlayerCommandTimer) {
				APlayerCommandTimer cmdTimer = (APlayerCommandTimer) cmd;
				for(PlayerTimerBuilder timerBuilder : cmdTimer.getTimer()) {
					if(!contain(cmdTimer, timerBuilder.getPlayer())) {
						commandM.simpleRequette(new IInsertRequette(storageName, Arrays.asList("command", "uuid", "current"), Arrays.asList(cmdTimer.getName(), timerBuilder.getPlayer(), timerBuilder.getCurrent())));
					}else {
						commandM.simpleRequette(new IUpdateRequette(storageName, Arrays.asList("current"),new Contraint(ConstraintValues.WHERE, "command = ? AND uuid = ?"), Arrays.asList(timerBuilder.getCurrent(), cmd.getName(), timerBuilder.getPlayer())));
					}
					
				}
			}
		}
	}

	@Override
	protected boolean contain(APlayerCommandTimer timer, String uuid) {
		return commandM.containRequette(new IContainRequette(storageName, Arrays.asList("command", "uuid"), Arrays.asList(timer.getName(), uuid)));
	}
	
}
