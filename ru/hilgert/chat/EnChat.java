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

public class EnChat extends JavaPlugin {

	public static boolean sc_enabled = false;
	public static boolean pex_enabled = false;

	public static PluginManager pm;
	public static FileConfiguration config;

	public static final List<IChat> chats = new ArrayList<IChat>();

	public static File cFile;

	public void onEnable() {

		cFile = new File(getDataFolder(), "config.yml");

		config = getConfig();

		initConfig();
		loadChats();
		pm = Bukkit.getPluginManager();

		sc_enabled = pm.getPlugin("SimpleClans") != null
				&& config.getBoolean("SimpleClans", true);

		pex_enabled = pm.getPlugin("PermissionsEx") != null
				&& pm.getPlugin("PermissionsEx").isEnabled()
				&& config.getBoolean("pex", true);

		pm.registerEvents(new ChatListener(), this);

		getCommand("enchat").setExecutor(new EnChatCommand());
	}

	private static void loadChats() {
		chats.clear();
		for (String chat : config.getConfigurationSection("chats").getKeys(
				false)) {
			chats.add(new Chat(chat));
		}
	}

	private static void initConfig() {

		List<String> matches = new ArrayList<String>();
		matches.add("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$");
		matches.add("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}:\\d{1,5}$");
		config.addDefault("SimpleClans", false);
		config.addDefault("pex", false);

		config.addDefault("maxUpperChars", 10);

		config.addDefault("default-chat", "default");

		config.addDefault("chats.default.name", "Локальный");
		config.addDefault("chats.default.format",
				"[@count] @clan@prefix@player@suffix: @message");
		config.addDefault("chats.default.ranged", true);
		config.addDefault("chats.default.range", 100.0);

		config.addDefault("chats.global.name", "Глобальный");
		config.addDefault("chats.global.prefix", "#w");
		config.addDefault("chats.global.format",
				"@clan@prefix@player@suffix: @message");
		config.addDefault("chats.global.ranged", false);
		config.addDefault("chats.global.range", 0.0);

		config.addDefault("replaceTo", "<Цензура>");
		config.addDefault("matches", matches);

		config.addDefault("lang.useChat",
				"&aИспользуйте: {CHAT_PREFIX}<Сообщение>");
		config.addDefault("lang.config-reloaded", "&aКонфиг перезагружен");
		config.addDefault("lang.usage", "&aИспользуйте: /enchat <reload>");
		config.addDefault("lang.no-permission",
				"&cУ вас нету прав для данного действия");
		config.addDefault(
				"lang.dontCaps",
				"&cНа этом сервере запрещено использовать большое количество больших букв в высоком регистре");
		config.options().copyDefaults(true);

		configSave();
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
		initConfig();
	}

}
