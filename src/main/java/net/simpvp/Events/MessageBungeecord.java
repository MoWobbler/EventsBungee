package net.simpvp.Events;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;


public class MessageBungeecord {

	//Connect player to a server
	public static void connect(Player player, String server) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("Connect");
		output.writeUTF(server);
		
		player.sendPluginMessage(Events.instance,"BungeeCord",output.toByteArray());
	}
	
	//Message all players
	public static void messageAll(String msg) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("Message");
		  out.writeUTF("ALL");
		  out.writeUTF(msg);
		  Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		  player.sendPluginMessage(Events.instance, "BungeeCord", out.toByteArray());
	}
}
