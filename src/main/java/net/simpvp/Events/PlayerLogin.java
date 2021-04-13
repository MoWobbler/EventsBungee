package net.simpvp.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		if (event.getPlayer().isOp()) {
			return;
		}
		
		if (!Event.getIsActive()) {
			event.disallow(null, "Please wait for an event to start!");
			return;
		}
	}
	
	
}
