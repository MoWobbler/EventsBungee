package net.simpvp.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

/** Class is responsible for listening to player deaths
 * and making sure no xp is dropped in the events
 * and that players don't keep any set gamemodes
 */
public class PlayerDeath implements Listener {

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		if (!player.isOp() && player.getGameMode() != GameMode.SURVIVAL) {
			player.setGameMode(GameMode.SURVIVAL);
		}

		EventPlayer eplayer = Event.getEventPlayer(player.getUniqueId());
		if (eplayer == null) {
			return;
		}

		event.setDroppedExp(0);

		if (Event.getKeepMyInventory() == true) {
			if (!eplayer.getIsQuitting()) {
				event.setKeepInventory(true);
			}
			event.getDrops().clear();

		}

		// It doesn't seem to like it if we just call respawn before this
		// event has finished processing, so schedule it to run as a task ASAP.
		new BukkitRunnable() {
			@Override
			public void run() {
				player.spigot().respawn();
			}
		}.runTaskLater(Events.instance, 0);
	}
}
