package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /quitevent command
 *
 * Let's players leave the event anytime.
 */
public class QuitEventCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		Player quitter = null;

		if (player != null) {
			UUID uuid = player.getUniqueId();

			if (Event.isPlayerActive(uuid)) {
				quitter = player;
			} else if (!player.isOp() || args.length == 0) {
				player.sendMessage(ChatColor.RED + "You are not in any event. If you need help, try asking for an admin.");
				return true;
			}
		}

		if (quitter == null) {
			if (args.length < 1) {
				sender.sendMessage("You are not in any event. To kick somebody from the event, do /quitevent <player>");
				return true;
			}

			quitter = Events.instance.getServer().getPlayer(args[0]);
			if (quitter == null) {
				sender.sendMessage("No such player found: " + args[0]);
				return true;
			}
		}

		if (!Event.isPlayerActive(quitter.getUniqueId())) {
			sender.sendMessage(String.format("Target player %s is not in an event", quitter.getName()));
			return true;
		}

		EventPlayer event_quitter = Event.getEventPlayer(quitter.getUniqueId());
		event_quitter.setIsQuitting(true);
		quitter.setHealth(0);

		return true;
	}
}
