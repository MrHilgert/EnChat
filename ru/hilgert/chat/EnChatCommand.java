package ru.hilgert.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.hilgert.chat.utils.Utils;

public class EnChatCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender.hasPermission("enchat.command")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					MainClass.configReload();
					sender.sendMessage(Utils.lang("config-reloaded"));
				}else sender.sendMessage(Utils.lang("usage"));
			} else {
				sender.sendMessage(Utils.lang("usage"));
			}
		} else {
			sender.sendMessage(Utils.lang("no-permission"));
		}
		return true;
	}

}
