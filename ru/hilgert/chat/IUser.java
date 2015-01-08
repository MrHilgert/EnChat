package ru.hilgert.chat;

import org.bukkit.entity.Player;

public interface IUser {

	boolean isMuted(IChat chat);

	String setMuted(IChat chat, boolean muted);

	String getPrefix();

	String getSuffix();

	String getName();

	String getTag();

	Player getPlayer();

}
