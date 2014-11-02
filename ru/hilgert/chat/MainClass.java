package ru.hilgert.chat;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.hilgert.chat.listeners.ChatListener;

public class MainClass extends JavaPlugin {

	public static boolean sc_enabled = false;
	public static boolean pex_enabled = false;

	public static PluginManager pm;
	public static FileConfiguration config;

	public static File cFile;

	public void onEnable() {

		cFile = new File(getDataFolder(), "config.yml");

		config = getConfig();

		initConfig();

		pm = Bukkit.getPluginManager();

		sc_enabled = pm.getPlugin("SimpleClans") != null
				&& config.getBoolean("SimpleClans", true);

		pex_enabled = pm.getPlugin("PermissionsEx") != null
				&& pm.getPlugin("PermissionsEx").isEnabled()
				&& config.getBoolean("pex", true);

		pm.registerEvents(new ChatListener(), this);

	}

	private void initConfig() {

		config.addDefault("SimpleClans", false);
		config.addDefault("pex", false);

		config.addDefault("chat-range", 100d);
		config.addDefault("shout-char", '!');

		config.addDefault("shout-template",
				"clanprefix&eplayer&rsuffix: &7message");
		config.addDefault("chat-template",
				"clanprefix&7player&rsuffix: &fmessage");

		config.options().copyDefaults(true);

		saveConfig();

	}

	public static void configSave() {
		try {
			config.save(cFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void configReload() {
		config = YamlConfiguration.loadConfiguration(cFile);
	}

}
