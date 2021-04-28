package net.simpvp.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import net.md_5.bungee.api.ChatColor;

public class PlayerLogin implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		
		
		if (!Event.getIsActive() && !event.getPlayer().isOp()) {
			event.disallow(null, ChatColor.RED + "Please wait for an event to start!");
			return;
		}
	}
	
	
}
