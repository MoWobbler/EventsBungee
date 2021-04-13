package net.simpvp.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;

/** This class is responsible for handling the /event command.
 * 
 * Saves all the player's information.
 *  Teleports users to the set starting location of the event.
 *  And prepares them for the event. */
public class EventCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		/* Command sender must be an ingame player */
		if (player == null) {
			Events.instance.getLogger().info("You must be a player to use this command.");
			return true;
		}

		/* Event must be active */
		if (!Event.getIsActive()) {
			player.sendMessage(ChatColor.RED + "Event is currently closed for new contestants.\n"
					+ "Please wait until the next event starts.\n"
					+ "To quit the event, do /quitevent.");
			return true;
		}

		/* If the event is not setup yet */
		if (!Event.getIsComplete()) {
			sender.sendMessage(ChatColor.RED + "Woops. Looks like the event is set to active,\n" +
					"but I'm missing some information about the event.\n" +
					"Please tell the nice admin that they're missing something :)");
			return true;
		}

		UUID uuid = player.getUniqueId();

		/* Player is already in the event */
		if (Event.isPlayerActive(uuid)) {
			sender.sendMessage(ChatColor.RED + "You cannot join the same event twice.\n" +
					"If you wish to quit the event, type /quitevent");
			return true;
		}

		if (player.isInsideVehicle()) {
			sender.sendMessage(ChatColor.RED + "You cannot join events while in a vehicle.");
			return true;
		}

		if (player.getLocation().getY() < 0.0) {
			sender.sendMessage(ChatColor.RED + "You cannot join events from your current location.");
			return true;
		}

		if (player.isOp()) {
			player.performCommand("rg bypass off");
		}

		/* Everything seems to be in order. Saving all player info for when the event is over */
		String sPlayer = player.getName();
		int playerLevel = player.getLevel();
		ItemStack[] inventoryContents = player.getInventory().getContents();

		/* Save all this data */
		EventPlayer eventPlayer = new EventPlayer(uuid, sPlayer, false);
		eventPlayer.save();

		/* Log it all, just in case */
		String sInventoryContents = "";

		for (ItemStack itemStack : inventoryContents) {
			if (itemStack == null)
				continue;
			String durability = "";
			   if (itemStack.getItemMeta() instanceof Damageable){
		            durability = Integer.toString(((Damageable) itemStack.getItemMeta()).getDamage());
		        }

			String tmp = itemStack.getType().toString()
				+ "." + itemStack.getAmount()
				+ "." + durability
				+ "." + itemStack.getEnchantments().toString();
			tmp = tmp.replaceAll(" ", "");
			sInventoryContents += " " + tmp;
		}

		Events.instance.getLogger().info(sPlayer + " is participating in event: " + playerLevel + sInventoryContents);

		/* Now we reset all their stuff and teleport them off */
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
		for (PotionEffect potionEffect : player.getActivePotionEffects())
			player.removePotionEffect(potionEffect.getType());
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(Event.getStartLocation());

		player.sendMessage(ChatColor.AQUA + "Teleporting you to the event arena.");

		return true;

	}

}
