package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /endevent command.
 * 
 * This command can be used by all OPs and console.
 * It sends all players currently in any event home.
 */
public class EndEventCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		/* If non-op */
		if (player != null && !player.isOp()) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		for (UUID uuid : Event.getActivePlayers()) {
			Player tPlayer = Events.instance.getServer().getPlayer(uuid);
			EventPlayer eventPlayer = Event.getEventPlayer(uuid);
			eventPlayer.setIsQuitting(true);

			/* We can't do this to offline players */
			if (tPlayer != null)
				tPlayer.setHealth(0);

		}

		Event.clearTeamSpawns();
		Event.setIsActive(false);

		return true;
	}

}

