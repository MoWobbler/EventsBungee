package net.simpvp.Events;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /quitevent command
 *
 * Let's players leave the event world anytime.
 */
public class QuitEventCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}


		if (Event.isPlayerActive(player.getUniqueId())) {
			EventPlayer event_quitter = Event.getEventPlayer(player.getUniqueId());
			event_quitter.setIsQuitting(true);
		}


		//Tp back to survival server
		MessageBungeecord.connect(player, "Survival");
		return true;
	}
}
