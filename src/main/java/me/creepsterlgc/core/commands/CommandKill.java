package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandKill implements CommandCallable {
	
	public Game game;
	
	public CommandKill(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "core.kill")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/kill [player]")); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) {
			
			if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
			
			Player p = (Player) sender;
			p.offer(Keys.HEALTH, (double) 0);
			
			sender.sendMessage(Texts.of(TextColors.YELLOW, "You ", TextColors.GRAY, "have been killed."));
			
		}
		else if(args.length == 1) {
			
			if(!PermissionsUtils.has(sender, "core.kill-others")) {
				sender.sendMessage(Texts.builder("You do not have permissions to kill other players!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			Player p = ServerUtils.getPlayer(args[0]);
			if(p == null) {
				sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			p.offer(Keys.HEALTH, (double) 0);
			
			sender.sendMessage(Texts.of(TextColors.YELLOW, p.getName(), TextColors.GRAY, " has been killed."));
			p.sendMessage(Texts.of(TextColors.GRAY, "You have been killed by ", TextColors.YELLOW, sender.getName()));
			
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tp <player> [target]").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tp <player> [target]").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TP Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
