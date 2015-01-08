package ru.hilgert.chat;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.hilgert.chat.utils.Utils;

public class Chat implements IChat {

	private final String chatName;

	private final HashMap<Player, String> lastMessages = new HashMap<Player, String>();
	
	public Chat(String chatName) {
		this.chatName = chatName;
	}

	@Override
	public String getTemplate() {
		return ChatColor.translateAlternateColorCodes('&',
				MainClass.config.getString("chats." + getName() + ".format"));
	}

	@Override
	public boolean isRanged() {
		return MainClass.config.getBoolean("chats." + getName() + ".ranged");
	}

	@Override
	public double getRange() {
		return isRanged() ? MainClass.config.getDouble("chats." + getName()
				+ ".range") : 0.0;
	}

	@Override
	public String getName() {
		return chatName;
	}

	@Override
	public String getChatPrefix() {
		return isDefault() ? "" : MainClass.config.getString("chats."
				+ getName() + ".prefix");
	}

	@Override
	public String getFormattedTemplate(Player player) {
		return getTemplate()
				.replace("@player", player.getName())
				.replace("@clan", Utils.getTag(player))
				.replace("@prefix", Utils.getPrefix(player))
				.replace("@suffix", Utils.getSuffix(player))
				.replace("@message", "%2$s")
				.replace(
						"@count",
						Utils.getLocalRecipientsLenght(player, getRange(),
								getSeePermission()) + "");
	}

	@Override
	public boolean hasPermission(Player p) {
		return p.hasPermission("enchat.chat." + getName());
	}

	@Override
	public List<Player> getRecipients(Player p) {
		if (isRanged()) {
			return Utils.getLocalRecipients(p, getRange(), getSeePermission());
		}
		return Utils.getAllRecipients(getSeePermission());
	}

	@Override
	public String getSeePermission() {
		return "enchat.chat." + getName() + ".see";
	}

	@Override
	public boolean isDefault() {
		return MainClass.config.getString("default-chat").equalsIgnoreCase(
				getName());
	}

	@Override
	public String getLastMessage(Player player) {
		return lastMessages.containsKey(player) ? lastMessages.get(player) : "";
	}

	@Override
	public void setLastMessage(Player player, String message) {
		lastMessages.put(player, message);
	}

}
