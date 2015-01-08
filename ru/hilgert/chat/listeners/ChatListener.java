package ru.hilgert.chat.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.hilgert.chat.IChat;
import ru.hilgert.chat.EnChat;
import ru.hilgert.chat.utils.Utils;

public class ChatListener implements Listener {

	private static int maxUpperChars;

	public ChatListener() {
		maxUpperChars = EnChat.config.getInt("maxUpperChars");
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (e.isCancelled())
			return;

		e.getRecipients().clear();

		String message = e.getPlayer().hasPermission("enchat.chat.colored") ? ChatColor
				.translateAlternateColorCodes('&', e.getMessage()) : e
				.getMessage();
		message = p.hasPermission("enchat.matches.bypass") ? message : Utils
				.removeMatches(message);
		if (Utils.getUpperCharsCount(message) >= maxUpperChars
				&& !p.hasPermission("enchat.caps.bypass")) {
			e.setCancelled(true);
			p.sendMessage(Utils.lang("dontCaps"));
			return;
		}
		IChat chat = Utils.getChatByMessage(message);
		if (chat.getLastMessage(p).equalsIgnoreCase(message)
				&& !p.hasPermission("enchat.spam.bypass")) {
			p.sendMessage(Utils.lang("dontSpam"));
			e.setCancelled(true);
			return;
		}
		if (e.getMessage().equalsIgnoreCase(chat.getChatPrefix())) {
			e.getPlayer().sendMessage(
					Utils.lang("useChat").replace("{CHAT_PREFIX}",
							chat.getChatPrefix()));
			e.setCancelled(true);
			return;
		}

		if (chat.hasPermission(p)) {
			e.getRecipients().addAll(chat.getRecipients(p));
			e.setFormat(chat.getFormattedTemplate(p));
			e.setMessage(message.substring(chat.getChatPrefix().length()));
			if (!p.hasPermission("enchat.spam.bypass"))
				chat.setLastMessage(p, message);
		} else {
			p.sendMessage(Utils.lang("no-permission"));
			e.setCancelled(true);
			return;
		}

	}
}
