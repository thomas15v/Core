package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWarp;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpRemove {

	public CommandWarpRemove(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.warp.remove")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp remove <name>")); return; }
		
		String name = args[1].toLowerCase();
		CoreWarp warp = CoreDatabase.getWarp(name);
		
		if(warp == null) { sender.sendMessage(Texts.builder("Warp does not exist!").color(TextColors.RED).build()); return; }
		
		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "core.warp.remove-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not own this warp!"));
			return;
		}
		
		warp.delete();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Warp ", TextColors.YELLOW, name, TextColors.GRAY, " has been removed."));
		
	}

}
