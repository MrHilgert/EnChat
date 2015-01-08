package ru.hilgert.chat.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.hilgert.chat.Chat;
import ru.hilgert.chat.IChat;
import ru.hilgert.chat.EnChat;

public class Utils {

	public static String getPrefix(Player p) {
		return EnChat.pex_enabled ? ru.tehkode.permissions.bukkit.PermissionsEx
				.getPermissionManager().getUser(p).getPrefix()
				: "";
	}

	public static String getSuffix(Player p) {
		return EnChat.pex_enabled ? ru.tehkode.permissions.bukkit.PermissionsEx
				.getPermissionManager().getUser(p).getSuffix()
				: "";
	}

	public static String getTag(Player p) {
		return EnChat.sc_enabled ? playerTag(p) : "";
	}

	private static String playerTag(Player p) {
		if (EnChat.sc_enabled) {
			try {
				net.sacredlabyrinth.phaed.simpleclans.SimpleClans sc = (net.sacredlabyrinth.phaed.simpleclans.SimpleClans) EnChat.pm
						.getPlugin("SimpleClans");
				return ChatColor.translateAlternateColorCodes('&', sc
						.getClanManager().getClanPlayer(p).getTagLabel());
			} catch (NullPointerException e) {
				return "";
			}
		}
		return "";
	}

	public static String getChatTemplate(String chat, Player p, String prefix,
			String suffix, String clan) {
		return ChatColor.translateAlternateColorCodes(
				'&',
				EnChat.config.getString("chat-template")
						.replace("@player", p.getName())
						.replace("@prefix", prefix).replace("@suffix", suffix)
						.replace("@clan", clan).replace("@message", "%2$s"));
	}

	public static IChat getChatByMessage(String message) {
		try {
			for (IChat chat : EnChat.chats) {
				if (!chat.isDefault()
						&& message.toLowerCase().startsWith(
								chat.getChatPrefix())) {
					return chat;
				}
			}
		} catch (NullPointerException ex) {
			return new Chat(EnChat.config.getString("default-chat"));
		}

		return new Chat(EnChat.config.getString("default-chat"));
	}

	public static List<Player> getLocalRecipients(Player p, double range,
			String permission) {

		Location loc = p.getLocation();

		List<Player> recipients = new ArrayList<Player>();

		double squaredDistance = Math.pow(range, 2);

		for (Player recipient : p.getWorld().getPlayers()) {

			if (loc.distanceSquared(recipient.getLocation()) > squaredDistance
					&& recipient.hasPermission(permission)) {
				continue;
			}

			recipients.add(recipient);
		}
		return recipients;
	}

	public static int getRecipientsSize(Player p, double range,
			String permission) {
		if (range <= 0) {
			return getLocalRecipients(p, range, permission).size();
		}
		return getAllRecipients(permission).size();
	}

	public static List<Player> getAllRecipients(String permission) {
		List<Player> recipients = new LinkedList<Player>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission(permission))
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
		for (String match : EnChat.config.getStringList("matches")) {
			message = message.replaceAll(match,
					EnChat.config.getString("replaceTo"));
		}
		return message;
	}

	public static String lang(String string) {
		return ChatColor.translateAlternateColorCodes('&',
				EnChat.config.getString("lang." + string));
	}

}