package com.plugin.ftb.battleroyale.team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.plugin.ftb.battleroyale.MainConfig;

public class TeamGUI {
	
	/*
	 * MainGUIを表示
	 */
	@SuppressWarnings("deprecation")
	public static void displayMainGUI(Player player){
		//インベントリを作成
	    Inventory inv = Bukkit.createInventory(null, 54, "Main");	//第2引数には9の倍数を指定

	    //アイテムを設定    
	    ItemStack make = new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getWoolData());
	    ItemMeta makeMeta = make.getItemMeta();
	    makeMeta.setDisplayName("チームを作成");
	    make.setItemMeta(makeMeta);
	    
	    ItemStack leave = new ItemStack(Material.WOOL, 1, DyeColor.LIGHT_BLUE.getWoolData());
	    ItemMeta leaveMeta = leave.getItemMeta();
	    leaveMeta.setDisplayName("チームから脱退");
	    leave.setItemMeta(leaveMeta);
	    
	    ItemStack reload = new ItemStack(Material.WOOL, 1, DyeColor.BLACK.getWoolData());
	    ItemMeta reloadMeta = leave.getItemMeta();
	    reloadMeta.setDisplayName("リロード");
	    reload.setItemMeta(reloadMeta);
	    
	    //アイテムを配置
	    inv.setItem(0, make);
	    inv.setItem(1, leave);
	    inv.setItem(2, reload);
	    
	    Map<String, ArrayList<String>> teams = MainConfig._teams;
	    for(int i=0; i<teams.keySet().size(); i++) {
	    	String teamName = teams.keySet().toArray()[i].toString();
		    //アイテムを設定
		    ItemStack team = new ItemStack(Material.EMERALD);
		    ItemMeta teamMeta = team.getItemMeta();
		    //アイテム名を設定
		    teamMeta.setDisplayName(ChatColor.RED + teamName);
		    //説明文を設定
		    List<String> lore = new ArrayList<>();
		    lore.add(0, ChatColor.GRAY + "クリックで参加");
		    //プレイヤーの名前を追加
		    /*
		    for(String uuid : teams.get(teamName)) {
		    	lore.add(ChatColor.WHITE + Bukkit.getPlayer(UUID.fromString(uuid)).getName());
		    }
		    teamMeta.setLore(lore);
		    */
		    team.setItemMeta(teamMeta);
		    
		    //2段目からアイテムを配置
		    inv.setItem(9 + i, team);	
	    }
	    player.openInventory(inv);
	}
}
