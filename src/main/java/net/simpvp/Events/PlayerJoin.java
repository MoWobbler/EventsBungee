package net.simpvp.Events;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team;

/** Monitors people joining the server
 * When they join, send them to spawn
 * and reset their stuff
 */

public class PlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		

		
		Player player = event.getPlayer();

		if (!player.isOp()) {
			player.setFoodLevel(20);
			player.setLevel(0);
			player.setExp(0);
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
			for (PotionEffect potionEffect : player.getActivePotionEffects())
				player.removePotionEffect(potionEffect.getType());
			player.setGameMode(GameMode.SURVIVAL);
			player.performCommand("event");
		}
		Team team = player.getScoreboard().getPlayerTeam(player);
		if (team != null) {
			team.removePlayer(player);
		}

		
		return;
		
	}
}
