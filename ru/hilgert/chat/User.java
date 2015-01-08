package ru.hilgert.chat;

import org.bukkit.entity.Player;

import ru.hilgert.chat.utils.Utils;

public class User implements IUser {

	private final Player player;
	private final String prefix;
	private final String suffix;
	private final String clanTag;

	public User(Player base) {
		this.player = base;
		this.prefix = Utils.getPrefix(player);
		this.suffix = Utils.getSuffix(player);
		this.clanTag = Utils.getTag(player);
	}

	@Override
	public boolean isMuted(IChat chat) {
		return chat.getMutes().contains(player.getName());
	}

	/*
	 * oh my god...
	 */
	@Override
	public String setMuted(IChat chat, boolean muted) {
		if (chat.getMutes().contains(player.getName()) && muted) {
			return Utils.lang("already_muted").replace("{PLAYER}",
					player.getName());
		}
		if (!chat.getMutes().contains(player.getName()) && !muted) {
			return Utils.lang("not_muted")
					.replace("{PLAYER}", player.getName());
		}

		if (muted) {
			chat.getMutes().add(getName());
			return Utils.lang("muted").replace("{PLAYER}", player.getName());
		} else {
			for (int i = 0; i < chat.getMutes().size(); i++) {
				if (chat.getMutes().get(i).equalsIgnoreCase(getName())) {
					chat.getMutes().remove(i);
					return Utils.lang("unmuted").replace("{PLAYER}",
							player.getName());
				}
			}
		}

		return "";
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getSuffix() {
		return suffix;
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public String getTag() {
		return clanTag;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

}
