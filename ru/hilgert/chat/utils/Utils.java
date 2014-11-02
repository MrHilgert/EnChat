package ru.hilgert.chat.utils;

import java.util.LinkedList;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.hilgert.chat.MainClass;

public class Utils {

	public static String getPrefix(Player p) {
		return MainClass.pex_enabled ? ru.tehkode.permissions.bukkit.PermissionsEx
				.getPermissionManager().getUser(p).getPrefix()
				: "@prefix";
	}

	public static String getSuffix(Player p) {
		return MainClass.pex_enabled ? ru.tehkode.permissions.bukkit.PermissionsEx
				.getPermissionManager().getUser(p).getSuffix()
				: "@suffix";
	}

	public static String getTag(Player p) {
		return MainClass.sc_enabled ? playerTag(p) : "clan";
	}

	private static String playerTag(Player p) {
		if (MainClass.sc_enabled) {
			net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager cm = new net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager();
			if (cm.getClanPlayer(p) != null) {
				return ChatColor.translateAlternateColorCodes('&', cm
						.getClanPlayer(p).getTagLabel());
			}
			return "";
		} else
			return "clan";
	}

	public static String getChatTemplate(Player p, String prefix,
			String suffix, String clan) {
		return ChatColor.translateAlternateColorCodes(
				'&',
				MainClass.config.getString("chat-template")
						.replace("player", p.getName())
						.replace("prefix", prefix).replace("suffix", suffix)
						.replace("clan", clan).replace("message", "%2$s"));
	}

	public static String getShoutTemplate(Player p, String prefix,
			String suffix, String clan) {
		return ChatColor.translateAlternateColorCodes(
				'&',
				MainClass.config.getString("shout-template")
						.replace("player", p.getName())
						.replace("prefix", prefix).replace("suffix", suffix)
						.replace("clan", clan).replace("message", "%2$s"));
	}

	@SuppressWarnings("deprecation")
	public static List<Player> getLocalRecipients(Player p, double range) {

		Location loc = p.getLocation();

		List<Player> recipients = new LinkedList<Player>();

		double squaredDistance = Math.pow(range, 2);

		for (Player recipient : Bukkit.getServer().getOnlinePlayers()) {

			if (loc.distanceSquared(recipient.getLocation()) > squaredDistance) {
				continue;
			}

			recipients.add(recipient);
		}
		return recipients;
	}

	@SuppressWarnings("deprecation")
	public static List<Player> getAllRecipients() {
		List<Player> recipients = new LinkedList<Player>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			recipients.add(p);
		}

		return recipients;

	}

}
