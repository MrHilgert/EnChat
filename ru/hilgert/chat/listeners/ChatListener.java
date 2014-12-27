package ru.hilgert.chat.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.hilgert.chat.MainClass;
import ru.hilgert.chat.utils.Utils;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if (e.isCancelled())
			return;
		double range = MainClass.config.getDouble("chat-range", 100d);
		if (e.getMessage().equalsIgnoreCase(
				MainClass.config.getString("shout-char"))) {
			e.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&',
							Utils.lang("shout")));
			e.setCancelled(true);
		}

		e.getRecipients().clear();

		String message = e.getPlayer().hasPermission("enchat.chat.colored") ? ChatColor
				.translateAlternateColorCodes('&', e.getMessage()) : e
				.getMessage();

		boolean shout = e.getMessage().startsWith(
				MainClass.config.getString("shout-char", "!"))
				&& p.hasPermission("enchat.channel.global");

		String shoutFormat = Utils.getShoutTemplate(p, Utils.getPrefix(p),
				Utils.getSuffix(p), Utils.getTag(p));
		String chatFormat = Utils.getChatTemplate(p, Utils.getPrefix(p),
				Utils.getSuffix(p), Utils.getTag(p)).replace("@count",
				Utils.getLocalRecipientsLenght(p, range) + "");

		if (shout) {
			e.getRecipients().addAll(Utils.getAllRecipients());
			e.setFormat(shoutFormat);
			e.setMessage(message.substring(MainClass.config.getString(
					"shout-char").length()));
		} else {
			e.getRecipients().addAll(
					Utils.getLocalRecipients(e.getPlayer(), range));
			e.setFormat(chatFormat);
			e.setMessage(message);
		}

	}

}
