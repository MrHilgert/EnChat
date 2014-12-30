package ru.hilgert.chat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

		getCommand("enchat").setExecutor(new EnChatCommand());
	}

	private void initConfig() {

		List<String> matches = new ArrayList<String>();
		matches.add("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$");
		config.addDefault("SimpleClans", false);
		config.addDefault("pex", false);

		config.addDefault("ranged-chat", true);

		config.addDefault("chat-range", 100d);
		config.addDefault("maxUpperChars", 10);
		config.addDefault("shout-char", '!');

		config.addDefault("shout-template",
				"@clan&e@prefix@player&r@suffix: &7@message");
		config.addDefault("chat-template",
				"[@count] @clan&7@prefix@player&r@suffix: &f@message");
	
		config.addDefault("replaceTo", "<Цензура>");
		config.addDefault("matches", matches);

		config.addDefault("lang.shout", "&aИспользуйте: !<Сообщение>");
		config.addDefault("lang.config-reloaded", "&aКонфиг перезагружен");
		config.addDefault("lang.usage", "&aИспользуйте: /enchat <reload>");
		config.addDefault("lang.no-permission" + "",
				"&cУ вас нету прав на использование данной команды");
		config.addDefault(
				"lang.dontCaps",
				"&cНа этом сервере запрещено использовать большое количество больших букв в высоком регистре");
		config.options().copyDefaults(true);

		saveConfig();
		matches = null;
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
