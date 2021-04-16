package net.simpvp.Events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;





public class Events extends JavaPlugin implements Listener {

	public static Events instance;
	public static boolean enabled = false;

	public Events() {
		instance = this;
	}

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new PluginDisable(), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageBungeecord());
		getCommand("ea").setExecutor(new EaCommand());
		getCommand("event").setExecutor(new EventCommand());
		getCommand("eventset").setExecutor(new EventSetCommand());
		getCommand("quitevent").setExecutor(new QuitEventCommand());
		getCommand("endevent").setExecutor(new EndEventCommand());
		getCommand("eventrestore").setExecutor(new EventRestoreCommand());
		enabled = true;
		setInstance(this);
	}

	@Override
	public void onDisable(){
		enabled = false;
	}
	
	// Get instance
	public static Events getInstance() {
		return instance;
	}
	
	// Set instance
	private static void setInstance(Events instance) {
		Events.instance = instance;
	}

}

