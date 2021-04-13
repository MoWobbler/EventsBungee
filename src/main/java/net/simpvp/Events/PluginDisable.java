package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class PluginDisable implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
	public void onPlayerQuit(PluginDisableEvent event) {
		for (Player p : Events.instance.getServer().getOnlinePlayers()) {
			UUID uuid = p.getUniqueId();
			if (!Event.isPlayerActive(uuid)) {
				return;
			}

			EventPlayer eventPlayer = Event.getEventPlayer(uuid);
			eventPlayer.sendHome(p);
			p.teleport(eventPlayer.getLocation());
		}
	}
}
