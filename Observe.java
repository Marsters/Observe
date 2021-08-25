package me.marsters.Observe;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.marsters.Observe.Commands.Command;
import me.marsters.Observe.Commands.Save;

public class Observe extends JavaPlugin {
	
		
	public static JSONObject playerdata;
	
	@Override
	public void onEnable() {
		getLogger().info("Observe has started up.");
		this.getCommand("observe").setExecutor(new Command(this));
		this.getCommand("save").setExecutor(new Save(this));
		playerdata = getJSONFile();
	}
	
	@Override 
	public void onDisable() {
		save();
	} 
	
	public static void save() {
		try (FileWriter file = new FileWriter("C:\\Users\\devma\\Desktop\\boob\\ELSE\\SERVER\\plugins\\Observe\\playerdata.json")) {
			file.write(playerdata.toJSONString());
			file.flush();
			Bukkit.getServer().broadcastMessage("Saved all player data. (I hope....)");
			Bukkit.getLogger().info(playerdata.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JSONObject getJSONFile() {
		 JSONParser jsonParser = new JSONParser();
		 try (FileReader reader = new FileReader("C:\\Users\\devma\\Desktop\\boob\\ELSE\\SERVER\\plugins\\Observe\\playerdata.json")) {
			 Object obj = jsonParser.parse(reader);
			 
			 JSONObject json = (JSONObject) obj;
			 System.out.println(json);
			 return json;
		 } catch(FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JSONObject();
	}
	
	@SuppressWarnings("unchecked")
	public static void addToPlayerData(String key, JSONArray value) {
		playerdata.put(key, value);
	}
	
}
