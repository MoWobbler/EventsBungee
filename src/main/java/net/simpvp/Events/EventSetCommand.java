package net.simpvp.Events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * This class handles the /eventset command
 * 
 * /eventset is used to set information about the event, such as spawn location.
 */
public class EventSetCommand implements CommandExecutor {

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

		/* Whether the respective coordinate has been set.
		 * 1 = set by default, 2 = set explicitly.
		 * We only set it to 1 from player locations, as while CommandBlocks
		 * do have a base location, we don't want that location to be used
		 * by default (CommandBlocks should always explicitly specify location,
		 * with the exception of the world.) */
		int set_world = 0;
		int set_x = 0;
		int set_y = 0;
		int set_z = 0;

		/* Whether there was a base location to use for relative coords */
		boolean existing_location = false;

		Location location = new Location(null, 0, 0, 0);
		Team team = null;

		if (player != null) {
			location = player.getLocation();
			existing_location = true;
			set_world = 1;
			set_x = 1;
			set_y = 1;
			set_z = 1;
		}
		if (sender instanceof BlockCommandSender) {
			location = ((BlockCommandSender) sender).getBlock().getLocation();
			/* Adjust x and z coordinates so the player spawns in the center of the block */
			location.setX(location.getX() + 0.5);
			location.setZ(location.getZ() + 0.5);
			set_world = 1;
			existing_location = true;
		}

		boolean keepMyInventory = false;
		Event.setKeepMyInventory(keepMyInventory);
		
		for (String arg : args) {

			if (arg.toLowerCase().startsWith("world=")) {
				if (set_world >= 2) {
					String msg = "Tried to double-define world";
					error_log(msg, sender);
					return true;
				}
				String sWorld = arg.substring(6);
				World world;
				try {
					world = Events.instance.getServer().getWorld(sWorld);
					location.setWorld(world);
				} catch (Exception e) {
					String msg = "Invalid arguments, unable to get world " + sWorld;
					error_log(msg, sender);
					return true;
				}

				set_world = 2;
				/* If we're explicitly specifying a world, it's
				 * an error to use the default coordinates. */
				if (set_x == 1) {
					set_x = 0;
				}
				if (set_y == 1) {
					set_y = 0;
				}
				if (set_z == 1) {
					set_z = 0;
				}

			} else if (arg.toLowerCase().startsWith("x=")) {
				if (set_x >= 2) {
					String msg = "Tried to double-define x coordinate";
					error_log(msg, sender);
					return true;
				}

				String sX = arg.substring(2);
				boolean relative = false;
				if (sX.startsWith("~")) {
					if (!existing_location) {
						String msg = "Cannot set relative X coordinate from command sender without location: " + sX;
						error_log(msg, sender);
						return true;
					}
					sX = sX.substring(1);
					relative = true;
				}
				Integer x = null;
				if (!sX.isEmpty()) {
					try {
						x = Integer.parseInt(sX);
					} catch (Exception e) {
						String msg = "Invalid x coordinate '" + sX + "': " + e;
						error_log(msg, sender);
						return true;
					}
				}

				if (relative) {
					if (x == null) {
						x = 0;
					}
					location.setX(location.getX() + x);
				} else {
					location.setX(x + 0.5);
				}

				set_x = 2;

			} else if (arg.toLowerCase().startsWith("y=")) {
				if (set_y >= 2) {
					String msg = "Tried to double-define y coordinate";
					error_log(msg, sender);
					return true;
				}

				String sY = arg.substring(2);
				boolean relative = false;
				if (sY.startsWith("~")) {
					if (!existing_location) {
						String msg = "Cannot set relative Y coordinate from command sender without location: " + sY;
						error_log(msg, sender);
						return true;
					}
					sY = sY.substring(1);
					relative = true;
				}
				Integer y = null;
				if (!sY.isEmpty()) {
					try {
						y = Integer.parseInt(sY);
					} catch (Exception e) {
						String msg = "Invalid y coordinate '" + sY + "': " + e;
						error_log(msg, sender);
						return true;
					}
				}

				if (relative) {
					if (y == null) {
						y = 0;
					}
					location.setY(location.getY() + y);
				} else {
					location.setY(y + 0.5);
				}

				set_y = 2;

			} else if (arg.toLowerCase().startsWith("z=")) {
				if (set_z >= 2) {
					String msg = "Tried to double-define z coordinate";
					error_log(msg, sender);
					return true;
				}

				String sZ = arg.substring(2);
				boolean relative = false;
				if (sZ.startsWith("~")) {
					if (!existing_location) {
						String msg = "Cannot set relative Z coordinate from command sender without location: " + sZ;
						error_log(msg, sender);
						return true;
					}
					sZ = sZ.substring(1);
					relative = true;
				}
				Integer z = null;
				if (!sZ.isEmpty()) {
					try {
						z = Integer.parseInt(sZ);
					} catch (Exception e) {
						String msg = "Invalid z coordinate '" + sZ + "': " + e;
						error_log(msg, sender);
						return true;
					}
				}

				if (relative) {
					if (z == null) {
						z = 0;
					}
					location.setZ(location.getZ() + z);
				} else {
					location.setZ(z + 0.5);
				}

				set_z = 2;

			} else if (arg.toLowerCase().startsWith("team=")) {
				if (team != null) {
					String msg = "Tried to double-define team";
					error_log(msg, sender);
					return true;
				}

				String sTeam = arg.substring(5);
				team = Events.instance.getServer().getScoreboardManager().getMainScoreboard().getTeam(sTeam);
				if (team == null) {
					String message = "No such scoreboard team '" + sTeam + "'. Type /scoreboard teams list"
						+ "\nfor a list of scoreboard teams.";
					error_log(message, sender);
					return true;
				}

			} else if (arg.toLowerCase().startsWith("keepinventory=")) {
					String sKeepInventory = arg.substring(14);
					if (sKeepInventory.toLowerCase().equalsIgnoreCase("true")) {
						keepMyInventory = true;
						Event.setKeepMyInventory(keepMyInventory);
					} else if (sKeepInventory.toLowerCase().equalsIgnoreCase("false")) {
						keepMyInventory = false;
						Event.setKeepMyInventory(keepMyInventory);
					} else {
						String message = String.format("Unknown keepinventory value '%s', must be true/false");
						error_log(message, sender);
						return true;
					}
			} else {
				/* An invalid argument was passed. */
				String message = "You tried to use an invalid argument with /eventset."
					+ "\nThe argument in question was: " + arg
					+ "\nThe correct syntax is: /eventset [x=] [y=] [z=] [world=] [team=]"
					+ "\nExample: /eventset x=26 y=75 z=-154 world=events";
				error_log(message, sender);
				return true;
			}

		}

		/* If any of the location arguments have been passed, we try to create a Bukkit.Location out of them */
		if (set_world == 0 || set_x == 0 || set_y == 0 || set_z == 0) {
			String msg = "Non-player command users must set a location with /eventset\n"
				+ "Proper syntax is /eventset [x=<x>] [y=<y>] [z=<z>] [world=<world>]";
			error_log(msg, sender);
			return true;
		}

		/* The given location is not the event's start location
		 * but the spawn location of a team */
		if (team != null) {
			Event.setTeamSpawn(team.getName(), location);

			String message = "Location of " + team.getName() + " team's spawn location has been set.";
			if (player == null) {
				sender.sendMessage(message);
			} else {
				player.sendMessage(ChatColor.AQUA + message);
			}
			if (!(sender instanceof ConsoleCommandSender)) {
				Events.instance.getLogger().info("Eventset: " + message);
			}

		/* The given location is the event's start location */
		} else {
			Event.setStartLocation(location);
			Event.setIsComplete(true);
			String message = "The event spawn has been set to the specified location.";
			if (player == null) {
				sender.sendMessage(message);
			} else {
				player.sendMessage(ChatColor.AQUA + message);
			}
			if (!(sender instanceof ConsoleCommandSender)) {
				Events.instance.getLogger().info("Eventset: " + message);
			}
		}

		return true;
	}

	private void error_log(String msg, CommandSender sender) {
		if (sender instanceof Player) {
			((Player) sender).sendMessage(ChatColor.RED + msg);
		} else {
			sender.sendMessage(msg);
		}
		if (!(sender instanceof ConsoleCommandSender)) {
			Events.instance.getLogger().info("Eventset error: " + msg);
		}
	}
}

