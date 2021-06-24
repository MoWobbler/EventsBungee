package net.simpvp.Events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;





public class Events extends JavaPlugin implements Listener {

	public static JavaPlugin instance;
	public static boolean enabled = false;


	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new PluginDisable(), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
		getCommand("ea").setExecutor(new EaCommand());
		getCommand("event").setExecutor(new EventCommand());
		getCommand("eventset").setExecutor(new EventSetCommand());
		getCommand("quitevent").setExecutor(new QuitEventCommand());
		getCommand("endevent").setExecutor(new EndEventCommand());
		getCommand("eventrestore").setExecutor(new EventRestoreCommand());
		getCommand("eventlist").setExecutor(new EventListCommand());
		enabled = true;
		instance = this;

	}

	@Override
	public void onDisable(){
		enabled = false;
	}
	


}

