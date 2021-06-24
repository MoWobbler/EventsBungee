package net.simpvp.Events;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team;

/** Represents data about a player currently participating in the event.
 * The data is for the player's info outside the event, so that we can
 * teleport them back from the event as if nothing had happened. */
public class EventPlayer {

	private UUID uuid;
	private String name;
	private Location location;
	private Integer foodLevel;
	private Integer playerLevel;
	private float playerXP;
	private ItemStack[] armorContents;
	private ItemStack[] inventoryContents;
	private Double playerHealth;
	private Collection<PotionEffect> potionEffects;
	private GameMode playerGameMode;
	private Boolean isQuitting;

	public EventPlayer(UUID uuid, String name, Location location, Integer foodLevel, Integer playerLevel, float playerXP, ItemStack[] armorContents,
			ItemStack[] inventoryContents, Double playerHealth, Collection<PotionEffect> potionEffects, GameMode playerGameMode, Boolean isQuitting) {
		this.uuid = uuid;
		this.name = name;
		this.location = location;
		this.foodLevel = foodLevel;
		this.playerLevel = playerLevel;
		this.playerXP = playerXP;
		this.armorContents = armorContents;
		this.inventoryContents = inventoryContents;
		this.playerHealth = playerHealth;
		this.potionEffects = potionEffects;
		this.playerGameMode = playerGameMode;
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
	 * Get's the player's food level outside the events.
	 * @return player's return food level.
	 */
	public int getFoodLevel() {
		return foodLevel;
	}

	/**
	 * Get the player's xp level outside the event.
	 * @return player's return xp level.
	 */
	public int getPlayerLevel() {
		return playerLevel;
	}

	/**
	 * Get the player's progress to next xp level outside the event.
	 * @return player's return xp progress.
	 */
	public float getPlayerXP() {
		return playerXP;
	}

	/**
	 * Get the player's armor contents outside the event.
	 * @return player's return armor contents.
	 */
	public ItemStack[] getArmorContents() {
		return armorContents;
	}

	/**
	 * Get the players inventory contents outside the event.
	 * @return player's return inventory contents.
	 */
	public ItemStack[] getInventoryContents() {
		return inventoryContents;
	}

	/**
	 * Get the player's health outside the event.
	 * @return the player's return health.
	 */
	public Double getPlayerHealth() {
		return playerHealth;
	}

	/**
	 * Get a collection of the player's potion effects outside the event.
	 * @return player's return potion effects.
	 */
	public Collection<PotionEffect> getPotionEffects() {
		return potionEffects;
	}

	/**
	 * Get the player's gamemode outside the event.
	 * @return player's return gamemode.
	 */
	public GameMode getPlayerGameMode() {
		return playerGameMode;
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

		Events.instance.getLogger().info(player.getName());

		
		MessageBungeecord.connect(player);

		this.remove();
	}

}

