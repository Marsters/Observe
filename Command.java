package me.marsters.Observe.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.marsters.Observe.Observe;
import me.marsters.Observe.Utils;
import net.minecraft.world.level.World;

public class Command implements CommandExecutor {
	
	private Observe plugin;
	
	public Command(Observe plugin) {
		this.plugin = plugin;
		plugin.getCommand("observe").setExecutor(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String name, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.colour("&4&lERROR! &cThe console cannot execute this command!"));
			return true;
		}
		JSONObject playerdata = Observe.playerdata;
		Player p = (Player) sender;		
		JSONArray pdata = (JSONArray) playerdata.get(p.getUniqueId().toString());	
		try {
			boolean state = (boolean) pdata.get(0);
			if (state == true) {       
				pdata.set(0, false);
				p.teleport(convertStrToLoc(p, (String) pdata.get(1)));
				p.setGameMode(GameMode.SURVIVAL);
			} else if(state == false){
				pdata.set(0, true);
				pdata.set(1, p.getLocation().toString());
				p.setGameMode(GameMode.SPECTATOR);
			} else {
				pdata = new JSONArray();
				pdata.add(true);
				pdata.add(1, p.getLocation().toString());
				p.setGameMode(GameMode.SPECTATOR);
			}
			Observe.addToPlayerData(p.getUniqueId().toString(), pdata);
		} catch(Exception e) {
			pdata = new JSONArray();
			pdata.add(true);
			pdata.add(p.getLocation().toString());
			p.setGameMode(GameMode.SPECTATOR);
			Observe.addToPlayerData(p.getUniqueId().toString(), pdata);
			e.printStackTrace();
		}
		return true;
	}
	
	static Location convertStrToLoc(Player p, String str) {
		String[] s = str.split(",");
		CraftWorld w = (CraftWorld) p.getWorld();
		double x = Double.parseDouble(s[1].replace("x=", " "));
		double y = Double.parseDouble(s[2].replace("y=", " "));
		double z = Double.parseDouble(s[3].replace("z=", " "));
		float pitch = Float.parseFloat(s[4].replace("pitch=", " "));
		float yaw = Float.parseFloat(s[5].replace("yaw=", " ").replace('}', ' '));
		return new Location(w, x, y, z, pitch, yaw);
	}
}
