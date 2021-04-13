package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/** Represents data about a player currently participating in the event.
 * The data is for the player's info outside the event, so that we can
 * teleport them back from the event as if nothing had happened. */
public class EventPlayer {

	private UUID uuid;
	private String name;
	private Location location;
	private Boolean isQuitting;

	public EventPlayer(UUID uuid, String name, Boolean isQuitting) {
		this.uuid = uuid;
		this.name = name;
		this.isQuitting = isQuitting;
	}

	/**
	 * Saves the player to a HashMap in the Event class.
	 * Effectively storing the data about the player for later retrieval.
	 */
	public void save() {
		Event.saveEventPlayer(this.uuid, this);
	}

	/**
	 * Delete the data about this player from the HashMap in the Event class.
	 * For when the player is out of the event and so the info is no longer relevant.
	 */
	public void remove() {
		Event.removeEventPlayer(this.uuid);
	}

	/**
	 * Gets the player's name.
	 * @return the player's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the player's location outside the events.
	 * (the location they should be teleported back to when the event is over.)
	 * @return player's return location.
	 */
	public Location getLocation() {
		return location;
	}


	/**
	 * Get whether the player will be quitting on this death.
	 *
	 * On events where players respawn, this will be false until they shouldn't respawn anymore.
	 * On events without respawning, this will always be true.
	 * @return whether player is quitting event.
	 */
	public Boolean getIsQuitting() {
		return isQuitting;
	}

	/**
	 * Set whether the player should quit the event on next death.
	 * @param isQuitting Whether the player is quitting on next death.
	 */
	public void setIsQuitting(Boolean isQuitting) {
		this.isQuitting = isQuitting;
	}

	/**
	 *  Sends the player home from the events.
	 *
	 *  This method should be called when the player in the PlayerRespawnEvent is to be sent home,
	 *  and when they manually decide they want to quit the event.
	 *
	 *  Takes a Player object for the player (we can't store that in EventPlayers)
	 *  And whether to teleport the player.
	 *  If the player is dead, then we don't want to teleport them,
	 *  but then we'll instead already have set their respawn location.
	 *  @param player Bukkit.entity.Player object for the player 
	 */
	public void sendHome(final Player player) {

		Events.instance.getLogger().info("Ported " + name + " home from events.");

		player.sendMessage(ChatColor.AQUA + "Teleporting you to the lobby. To return home do /quitevent.");

		Team team = player.getScoreboard().getPlayerTeam(player);
		if (team != null)
			team.removePlayer(player);

		this.remove();
	}

}

