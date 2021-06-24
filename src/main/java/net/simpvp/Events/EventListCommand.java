package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /eventlist command
 * 
 * Very simply just prints a list of all players currently playing an
 * event to any admin or console.
 */
public class EventListCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player){
			player = (Player) sender;
		}

		/* String players is a list of all player's names in the events */
		String players = "";

		int playerAmount = 0;

		for (UUID uuid : Event.getActivePlayers()) {
			players += Event.getEventPlayer(uuid).getName() + ", ";
			playerAmount += 1;
		}

		/* Remove the final ", " */
		if (playerAmount > 0)
			players = players.substring(0, players.length() - 2);

		String message = null;

		/* Only players should get the fancy colors, a console should just get a plain message. */
		if (player != null) {
			message = ChatColor.GOLD + "There are " + ChatColor.AQUA + playerAmount + ChatColor.GOLD +  " players currently in the events: " + ChatColor.AQUA;	
		} else {
			message = "There are " + playerAmount + " players currently in the events: ";
		}


		/* If there aren't any eventPlayers, don't post a blank empty line */
		if (playerAmount > 0)
			message += "\n" + players;

		if (player != null) {
			player.sendMessage(message);
		} else {
			Events.instance.getLogger().info(message);
		}

		return true;
	}
}

