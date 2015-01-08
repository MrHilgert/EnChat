package ru.hilgert.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.hilgert.chat.utils.Utils;

public class Chat implements IChat {

	private final String chatName;

	private final String template;
	private final boolean ranged;
	private final double range;
	private final String prefix;
	private final boolean isDefault;
	private final String fName;

	private final HashMap<Player, String> lastMessages = new HashMap<Player, String>();
	private final List<String> mutes = new ArrayList<String>();

	public Chat(String chatName) {
		this.chatName = chatName;

		template = ChatColor.translateAlternateColorCodes('&',
				EnChat.config.getString("chats." + getName() + ".format"));

		ranged = EnChat.config.getBoolean("chats." + getName() + ".ranged");

		range = isRanged() ? EnChat.config.getDouble("chats." + getName()
				+ ".range") : 0.0;

		prefix = isDefault() ? "" : EnChat.config.getString("chats."
				+ getName() + ".prefix");

		isDefault = EnChat.config.getString("default-chat")
				.equalsIgnoreCase(getName());
		if (!EnChat.config.contains("chats." + getName() + ".mutes")) {
			EnChat.config.set("chats." + getName() + ".mutes", mutes);
		}
		fName = EnChat.config.getString("chats." + getName() + ".name");
	}

	@Override
	public String getTemplate() {
		return template;
	}

	@Override
	public boolean isRanged() {
		return ranged;
	}

	@Override
	public double getRange() {
		return range;
	}

	@Override
	public String getName() {
		return chatName;
	}

	@Override
	public String getChatPrefix() {
		return prefix;
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
						Utils.getRecipientsSize(player, getRange(),
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
		return isDefault;
	}

	@Override
	public String getLastMessage(Player player) {
		return lastMessages.containsKey(player) ? lastMessages.get(player) : "";
	}

	@Override
	public void setLastMessage(Player player, String message) {
		lastMessages.put(player, message);
	}

	@Override
	public List<String> getMutes() {
		return mutes;
	}

	@Override
	public String getFormattedName() {
		return fName;
	}

}
