package ru.hilgert.chat;

import java.util.List;

import org.bukkit.entity.Player;

public interface IChat {

	String getTemplate();

	boolean isRanged();

	double getRange();

	String getName();
	
	String getChatPrefix();

	String getFormattedTemplate(Player player);
	
	boolean hasPermission(Player p);
	
	String getSeePermission();
	
	List<Player> getRecipients(Player p);
	
	boolean isDefault();
	
	String getLastMessage(Player player);
	
	void setLastMessage(Player player, String message);
	
}
