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

		if (e.isCancelled())
			return;

		if(e.getMessage().equalsIgnoreCase(MainClass.config.getString("shout-char"))){
			 e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Utils.lang("shout"))); e.setCancelled(true);
		}
		
		e.getRecipients().clear();

		String message = e.getPlayer().hasPermission("enchat.chat.colored") ? ChatColor
				.translateAlternateColorCodes('&', e.getMessage()) : e
				.getMessage();
			
		boolean shout = false;
		shout = e.getMessage().charAt(0) == MainClass.config.getString(
				"shout-char", "!").charAt(0);

		Player p = e.getPlayer();
		
		String shoutFormat = Utils.getShoutTemplate(p, Utils.getPrefix(p), Utils.getSuffix(p), Utils.getTag(p));
		String chatFormat = Utils.getChatTemplate(p, Utils.getPrefix(p), Utils.getSuffix(p), Utils.getTag(p));
		
		if (shout) {
			e.getRecipients().addAll(Utils.getAllRecipients());
			e.setFormat(shoutFormat);
			e.setMessage(message.substring(1));
		} else {
			e.getRecipients().addAll(Utils.getLocalRecipients(e.getPlayer(), MainClass.config.getDouble("chat-range", 100d)));
			e.setFormat(chatFormat);
			e.setMessage(message);
		}
		

	}

}
