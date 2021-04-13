package net.simpvp.Events;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

/**
 * This class stores data about the current event.
 */
public class Event {

	private static Boolean isActive = false;
	private static Boolean isComplete = false;
	private static Location startLocation;
	private static HashMap<UUID, EventPlayer> eventPlayers = new HashMap<UUID, EventPlayer>();
	private static HashMap<String, Location> teamSpawns = new HashMap<String, Location>();
	private static Boolean keepMyInventory = false;

	/**
	 * Gets whether the event is active or not.
	 * @return Whether event is active.
	 */
	public static Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Sets whether the event is active or not.
	 * @param isActive Whether event is active.
	 */
	public static void setIsActive(Boolean isActive) {
		Event.isActive = isActive;
	}

	/**
	 * Whether the event is complete.
	 * 
	 * As a general rule of thumb, this is true if there is a startLocation.
	 * But if there are multiple spawn locations etc. then they have to all be set for this to be true.
	 * @return If event is completely set.
	 */
	public static Boolean getIsComplete() {
		return isComplete;
	}

	/**
	 * Whether the event is complete.
	 * @param isComplete If event is completely set.
	 */
	public static void setIsComplete(Boolean isComplete) {
		Event.isComplete = isComplete;
	}

	/**
	 * Set's the starting spawn location of the event.
	 * 
	 * All players joining the event will be teleported to this location.
	 * @param location Event's spawn location.
	 */
	public static void setStartLocation(Location location) {
		startLocation = location;
	}

	/**
	 * Returns the spawn location of the event.
	 * @return Event's spawn location.
	 */
	public static Location getStartLocation() {
		return startLocation;
	}

	/**
	 * Saves an EventPlayer object in the eventPlayers HashMap.
	 * 
	 * For later retrieval when the player is to be sent home.
	 * @param uuid UUID of the player.
	 * @param eventPlayer EventPlayer object of the player.
	 */
	public static void saveEventPlayer(UUID uuid, EventPlayer eventPlayer) {
		eventPlayers.put(uuid, eventPlayer);
	}

	/**
	 * Gets a stored EventPlayer object.
	 * @param uuid UUID of the player.
	 * @return EventPlayer object of the player.
	 */
	public static EventPlayer getEventPlayer(UUID uuid) {
		return eventPlayers.get(uuid);
	}

	/**
	 * Deletes a stored EventPlayer object.
	 * @param uuid UUID of the player.
	 */
	public static void removeEventPlayer(UUID uuid) {
		eventPlayers.remove(uuid);
	}

	/**
	 * Gets whether the player is currently playing an event.
	 * @param uuid UUID of the player.
	 * @return Whether player is in an event.
	 */
	public static Boolean isPlayerActive(UUID uuid) {		
		return eventPlayers.containsKey(uuid);
	}

	/**
	 * Get a set of the names of all players currently playing an event.
	 * @return UUID of all players currently playing an event.
	 */
	public static Set<UUID> getActivePlayers() {
		return eventPlayers.keySet();
	}

	/**
	 * Get the spawn location for a particular team.
	 * @param name Name of the team.
	 * @return Location of team's spawn.
	 */
	public static Location getTeamSpawn(String name) {
		return teamSpawns.get(name);
	}

	/**
	 * Saves the team's location.
	 * @param name Name of  the team.
	 * @param location Location of the team's spawn.
	 */
	public static void setTeamSpawn(String name, Location location) {
		teamSpawns.put(name, location);
	}

	/**
	 * Clear all team's spawns.
	 */
	public static void clearTeamSpawns() {
		teamSpawns.clear();
	}

	public static Boolean getKeepMyInventory() {
		return keepMyInventory;
	}

	/**
	 * Set the keepMyInventory variable
	 */

	public static void setKeepMyInventory(Boolean keepMyInventory) {
		Event.keepMyInventory = keepMyInventory;
	}


}

