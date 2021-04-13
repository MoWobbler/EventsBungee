package net.simpvp.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;


/** Command /ea to change the active state of the /event command.
 * 
 * When it is active, players can do /event. When it is inactive, players cannot do /event.
 * 
 * If given with an argument [true/false], then it is set to that state.
 * If no argument is given, then it is toggled.
 */
public class EaCommand implements CommandExecutor {
	private static String eventName;
	private static String message;

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

		Boolean newState = null;

		if (args.length > 0) {
			String arg = args[0].toLowerCase(); // To make it case-insensitive

			/* and the one argument should always be either true or false (notice the singular "argument" :p ) */
			if (!arg.equals("true") && !arg.equals("false")) {
				if (player == null) {
					Events.instance.getLogger().info("Invalid argument. Proper syntax is: /ea [true/false]");
				} else {
					player.sendMessage(ChatColor.RED + "Invalid argument. Proper syntax is: " + ChatColor.AQUA + "/ea [true/false]");
				}
				return true;
			}

			if (arg.equals("true")) {
				newState = true;
			} else if (arg.equals("false")) {
				newState = false;
			}
		}

		/* Toggle the state if no arguments were given */
		else if (args.length == 0) {
			Boolean isActive = Event.getIsActive();

			newState = !isActive;
		}

		String message;
		if (newState) {
			message = "Event has been made active.";
		} else {
			message = "Event has been made inactive.";
		}

		if (player == null) {
			Events.instance.getLogger().info(message);
		} else {
			player.sendMessage(ChatColor.AQUA + message);
		}

		/* If there's more than 1 argument, we assume that all the extra arguments are the name for the event
		 * and that this should be broadcast */
		if (args.length > 1) {
			String sargs = "";

			for (int i = 1; i < args.length; i++)
				sargs += " " + args[i];
			
			setEventName(sargs);
			Player player1 = Iterables.getFirst(Bukkit.getOnlinePlayers(),null);
			Events.instance.getLogger().info(player1.getName());
			Events.instance.getLogger().info(sargs);
			EaCommand.setMessage(
					"[Announcement] Starting event:" + EaCommand.getEventName()
					+ "\n Type /event to join.");
			MessageBungeecord.PlayerList(player1);
		}

		Event.setIsActive(newState);

		return true;
	}
	
	// Set message to be broadcasted
	public static String getEventName() {
		return eventName;
	}
	
	// Get message to be broadcasted
	public static void setEventName(String eventName) {
		EaCommand.eventName = eventName;
	}
	
	// Set message to be broadcasted
	public static String getMessage() {
		return message;
	}
	
	// Get message to be broadcasted
	public static void setMessage(String message) {
		EaCommand.message = message;
	}

}

