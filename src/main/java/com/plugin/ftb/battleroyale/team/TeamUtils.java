package com.plugin.ftb.battleroyale.team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamUtils {
	
	/*
	 * チーム用本を配る
	 */
	public static void giveBook() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			ItemStack book = new ItemStack(Material.BOOK);
			ItemMeta bookMeta = book.getItemMeta();
			bookMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "右クリックでチームを見る");
			book.setItemMeta(bookMeta);
			player.getInventory().addItem(book);
		}
	}
}
