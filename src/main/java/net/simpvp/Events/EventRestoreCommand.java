package net.simpvp.Events;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import org.bukkit.ChatColor;

/**
 * Adds the /eventrestore command
 *
 * Restores items based on the player items logged when a player runs the /event
 * command, should be directly copy-pastable from this output. The first argument
 * however must be the name of the player to receive the items. This command can
 * only be run from console.
 *
 * For info about the format of the items, see EventCommand.java
 */
public class EventRestoreCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		if (args.length < 1) {
			Events.instance.getLogger().info("You must provide at least one argument.");
			return true;
		}

		@SuppressWarnings("deprecation") /* Only used on currently online players */
		Player player = Events.instance.getServer().getPlayerExact(args[0]);
		if (player == null) {
			Events.instance.getLogger().info("No such player found: " + args[0]);
			return true;
		}

		for (String arg : args) {
			if (!arg.matches(".*\\..*"))
				continue;

			ItemStack itemstack = createItemStack(arg);
			if (itemstack == null)
				return true;

			player.getWorld().dropItem(player.getLocation(), itemstack);
		}

		return true;
	}

	/**
	 * Create an itemstack given a 4-point string
	 */
	private ItemStack createItemStack(String item) {
		Events.instance.getLogger().info("create item: " + item);
		String[] input = item.split("\\.");

		if (input.length != 4)
			return null;


		/* damage = durability inverse */
		int amount;
		short damage;
		Material material;
		try {
			amount = Integer.parseInt(input[1]);
			damage = Short.parseShort(input[2]);
			material = Material.valueOf(input[0]);
		} catch (Exception e) {
			return null;
		}

		ItemStack ret = new ItemStack(material, amount, damage);
		ret = addEnchantments(ret, input[3]);

		return ret;
	}

	/**
	 * Add the given enchantments to the given ItemStack
	 */
	private ItemStack addEnchantments(ItemStack input, String enchants) {
		/* AFAIK there's no easy way to do regex captures in java,
		 * so instead we're just going to do it this hacky way.
		 *
		 * This code is ugly as fuck, but it works. */
		enchants = enchants.replaceAll("\\{", "");
		enchants = enchants.replaceAll("\\}", "");

		enchants = "," + enchants;
		enchants = enchants.replaceAll(",Ench", "");

		String[] enchantments = enchants.split("antment\\S+?,");

		for (String enchantment : enchantments) {
			if (!enchantment.matches(".*=.*"))
				continue;

			String[] tmp = enchantment.split("]=");
			Enchantment ench;
			int level;
			try {
				ench = Enchantment.getByName(tmp[0]);
				level = Integer.parseInt(tmp[1]);
			} catch (Exception e) {
				return null;
			}

			input.addEnchantment(ench, level);
		}

		return input;
	}

}
