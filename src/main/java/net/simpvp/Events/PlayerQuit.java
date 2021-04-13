package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Responsible for teleporting players who leave back out of the events.
 */
public class PlayerQuit implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
	public void onPlayerQuit(PlayerQuitEvent event) {

		UUID uuid = event.getPlayer().getUniqueId();

		if (!Event.isPlayerActive(uuid))
			return;

		EventPlayer eventPlayer = Event.getEventPlayer(uuid);

		Player player = event.getPlayer();

		eventPlayer.sendHome(player);



	}

}

