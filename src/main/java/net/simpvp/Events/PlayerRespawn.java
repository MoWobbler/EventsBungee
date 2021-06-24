package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Team;

/** Monitors people respawning
 * If somebody participating in the event respawns, it teleports them home
 * and gives them back previous items and health, etc */
public class PlayerRespawn implements Listener {

	private static EventPlayer eventPlayer;

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerRespawn(PlayerRespawnEvent event) {

		UUID uuid = event.getPlayer().getUniqueId();

		/* If player is not participating in the event */
		if (!Event.isPlayerActive(uuid))
			return;

		eventPlayer = Event.getEventPlayer(uuid);
		Player player = event.getPlayer();
		Location teamSpawn = null;
		Team team = player.getScoreboard().getPlayerTeam(player);

		if (team != null)
			teamSpawn = Event.getTeamSpawn(team.getName());

		/* If isQuitting is true, or if the player is not in a team with a registered team spawn, then send player home */
		if (eventPlayer.getIsQuitting() || teamSpawn == null) {
			eventPlayer.sendHome(player);
		} else {
			/* else we send the player to their team's spawn location */
			event.setRespawnLocation(teamSpawn);
			player.sendMessage(ChatColor.AQUA + "Respawning at your team's spawn."
					+ "\nTo leave the event, type " + ChatColor.GOLD + "/quitevent");
		}

	}

}

