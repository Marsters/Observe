package me.marsters.Observe.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.marsters.Observe.Observe;

public class Save implements CommandExecutor {
	
	private Observe plugin;
	
	public Save(Observe plugin) {
		this.plugin = plugin;
		plugin.getCommand("save").setExecutor(this);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		Observe.save();
		return true;
	}
	
	
	
}
