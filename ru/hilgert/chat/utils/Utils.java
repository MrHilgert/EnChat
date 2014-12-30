package ru.hilgert.chat.utils;

import java.util.ArrayList;
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
				: "";
	}

	public static String getSuffix(Player p) {
		return MainClass.pex_enabled ? ru.tehkode.permissions.bukkit.PermissionsEx
				.getPermissionManager().getUser(p).getSuffix()
				: "";
	}

	public static String getTag(Player p) {
		return MainClass.sc_enabled ? playerTag(p) : "";
	}

	private static String playerTag(Player p) {
		if (MainClass.sc_enabled) {
			try {
				net.sacredlabyrinth.phaed.simpleclans.SimpleClans sc = (net.sacredlabyrinth.phaed.simpleclans.SimpleClans) MainClass.pm
						.getPlugin("SimpleClans");
				return ChatColor.translateAlternateColorCodes('&', sc
						.getClanManager().getClanPlayer(p).getTagLabel());
			} catch (NullPointerException e) {
				return "";
			}
		}
		return "";
	}

	public static String getChatTemplate(Player p, String prefix,
			String suffix, String clan) {
		if (!MainClass.config.getBoolean("ranged-chat")) {
			return getShoutTemplate(p, prefix, suffix, clan);
		}

		return ChatColor.translateAlternateColorCodes(
				'&',
				MainClass.config.getString("chat-template")
						.replace("@player", p.getName())
						.replace("@prefix", prefix).replace("@suffix", suffix)
						.replace("@clan", clan).replace("@message", "%2$s"));
	}

	public static String getShoutTemplate(Player p, String prefix,
			String suffix, String clan) {
		return ChatColor.translateAlternateColorCodes(
				'&',
				MainClass.config.getString("shout-template")
						.replace("@player", p.getName())
						.replace("@prefix", prefix).replace("@suffix", suffix)
						.replace("@clan", clan).replace("@message", "%2$s"));
	}

	public static List<Player> getLocalRecipients(Player p, double range) {

		Location loc = p.getLocation();

		List<Player> recipients = new ArrayList<Player>();

		double squaredDistance = Math.pow(range, 2);

		for (Player recipient : p.getWorld().getPlayers()) {

			if (loc.distanceSquared(recipient.getLocation()) > squaredDistance) {
				continue;
			}

			recipients.add(recipient);
		}
		return recipients;
	}

	public static int getLocalRecipientsLenght(Player p, double range) {
		return getLocalRecipients(p, range).size();
	}

	public static List<Player> getAllRecipients() {
		List<Player> recipients = new LinkedList<Player>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			recipients.add(p);
		}

		return recipients;
	}

	public static int getUpperCharsCount(String str) {
		int count = 0;
		str = str.replace(" ", "");
		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i)))
				count++;
		}
		return count;
	}

	public static String removeMatches(String message) {
		for (String match : MainClass.config.getStringList("matches")) {
			message = message.replaceAll(match,
					MainClass.config.getString("replaceTo"));
		}
		return message;
	}

	public static String lang(String string) {
		return ChatColor.translateAlternateColorCodes('&',
				MainClass.config.getString("lang." + string));
	}

}