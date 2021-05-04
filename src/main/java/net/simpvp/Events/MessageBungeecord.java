package net.simpvp.Events;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.entity.Player;

import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;


public class MessageBungeecord implements PluginMessageListener{
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	if (!channel.equals("BungeeCord")) {
	  return;
	}
	ByteArrayDataInput in = ByteStreams.newDataInput(message);
	String subchannel = in.readUTF();
	if (subchannel.equals("PlayerList")) {	  
		  String[] playerList = in.readUTF().split(", ");
		  messagePlayer(playerList,player,EaCommand.getMessage());

	    }
	  }
	
	public static void connect(Player player, String server) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("Connect");
		output.writeUTF(server);
		
		player.sendPluginMessage(Events.instance,"BungeeCord",output.toByteArray());
	}
	
	
	 /* First method called by the broadcast command
	 *  Sends a request to the bungeecord server
	 *  This method triggers the onPluginMessageReceived event
	 */
	public static void PlayerList(Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("PlayerList");
		  out.writeUTF("ALL");
		  player.sendPluginMessage(Events.instance, "BungeeCord", out.toByteArray());
	}
	
	 /* This method sends a message to all players
	 */
	public static void messagePlayer(String[] listOfPlayers,Player sender, String msg) {
		for (String s: listOfPlayers) {
			 if (s != null) {		  
					ByteArrayOutputStream b = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(b);
					
					try {
						out.writeUTF("Message");
						out.writeUTF(s);
						Events.instance.getLogger().info(s);
						out.writeUTF(ChatColor.LIGHT_PURPLE + msg);
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					sender.sendPluginMessage(Events.instance, "BungeeCord", b.toByteArray());	  
	    		  }
	    	  }
	}
	


}
