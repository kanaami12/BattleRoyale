package com.plugin.ftb.battleroyale;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainCommandExecutor implements CommandExecutor {

	public static BattleRoyale _plugin = BattleRoyale.plugin;
	public static int judEdit;

	public static ArrayList<Player> setChestPlayer = new ArrayList<>();

	public MainCommandExecutor(BattleRoyale plugin) {
		MainCommandExecutor._plugin = plugin;
	}

	/*
	 * onEnable()メソッドで定義したコマンドが実行されるとこのメソッドを通る。
	 *
	 * @param sender コマンド実行者
	 *
	 * @param cmd 実行されたコマンド
	 *
	 * @param label コマンドエイリアス
	 *
	 * @param args コマンドの引数
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			if (args.length == 0) {
				return false;
			}

			Player _player = (Player) sender;

			/*
			 * 対角線でステージの登録を行うためのL、Rの登録
			 * 引数の0と1は、LとRの識別子でMainConfigクラスで関数が増えるのを防ぐため
			 * とか言ってるけど結局文の数は対して変わってないのでわざわざswitch文使わなくてよかったかもです><
			 * 解読する際はみにくいかと思いますが頑張ってください・・・笑
			 */
			switch(args[0]){
			case "setStageL":
				Location _locL = _player.getLocation();

				MainConfig.makeStage(_locL,_player,0);

				return true;

			case "setStageR":
				if(_plugin.getConfig().get("stagelocationsL")!=null){
					Location _locR = _player.getLocation();

					MainConfig.makeStage(_locR,_player,1);

					return true;

				}else{
					_player.sendMessage(BattleRoyale.prefix + ChatColor.GRAY + "stageLから設定してください");

					return true;
				}

			case "setChest":
				if(_plugin.getConfig().get("stagelocationsR")!=null){
					judEdit = 2;
					_player.sendMessage(BattleRoyale.prefix + ChatColor.AQUA + "骨を持って左クリックでチェストの位置を編集してください");
					setChestPlayer.add(_player);

					return true;

				}else{
					_player.sendMessage(BattleRoyale.prefix + ChatColor.GRAY + "stageRを設定してください");

					return true;
				}

			case "comChest":
				judEdit = 0;
				_player.sendMessage(BattleRoyale.prefix + ChatColor.AQUA + "チェストの位置を確定しました");
				if(setChestPlayer.contains(_player)){
					setChestPlayer.remove(_player);
				}
				return true;

			case "setMap":
				ItemStack item = _player.getItemInHand();
				if(item.getType().equals(Material.MAP)){
					MainConfig.setMap(item);
					_player.sendMessage(BattleRoyale.prefix + ChatColor.GRAY + "マップを設定しました。");
				}else{
					_player.sendMessage(BattleRoyale.prefix + ChatColor.GRAY + "マップを持ってコマンドを実行してください。");
				}

				return true;

			case "setLobbypoint":
				MainConfig.setLobbypoint(_player.getLocation(), _player);

				_player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "Lobbypointを設定しました");

				return true;

			case "setStartpoint":
				MainConfig.setStartpoint(_player.getLocation(), _player);

				_player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "Startpointを設定しました");

				return true;

			case "setDeathpoint":
				MainConfig.setDeathpoint(_player.getLocation(), _player);

				_player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "Deathpointを設定しました");

				return true;

			default:
				_player.sendMessage(BattleRoyale.prefix + ChatColor.GRAY + "\n/battleroyale " + ChatColor.RED + "setStageL\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "setStageR\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "setLobbypoint\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "setStartpoint\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "setDeathpoint\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "setChest\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "comChest\n"
						+ ChatColor.GRAY + "/battleroyale " + ChatColor.RED + "setMap");
				return true;
			}
		}

		return false;
	}
}