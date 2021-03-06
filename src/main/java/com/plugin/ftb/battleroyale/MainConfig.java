package com.plugin.ftb.battleroyale;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class MainConfig extends BattleRoyale {

	public static BattleRoyale plugin = BattleRoyale.plugin;
	public static ArrayList<Location> locations = new ArrayList<>();
	public static ArrayList<Integer> _stagelocationsL = new ArrayList<>();
	public static ArrayList<Integer> _stagelocationsR = new ArrayList<>();
	public static ArrayList<Integer> _joinGameL = new ArrayList<>();
	public static ArrayList<Integer> _joinGameR = new ArrayList<>();
	public static ArrayList<Integer> _chestlocations = new ArrayList<>();
	public static ArrayList<Integer> _lobbypoint = new ArrayList<>();
	public static ArrayList<ArrayList<Integer>> _startpoints = new ArrayList<>();
	public static ArrayList<Integer> _deathpoint = new ArrayList<>();
	public static ArrayList<Integer> _Timer = new ArrayList<>();
	public static ArrayList<Integer> check = new ArrayList<>();
	public static Map<String, ArrayList<String>> _teams = new HashMap<>();
	public static ArrayList<Location> _protectedBlocks = new ArrayList<>();

	public static int chestCount;

	public static boolean isRandom = false;

	/*
	 * configからlocationsを取得
	 */
	@SuppressWarnings("unchecked")
	public static void loadConfig() {

		plugin.reloadConfig();
		locations = new ArrayList<>();
		locations = (ArrayList<Location>) plugin.getConfig().get("locations");
		if (locations == null) {
			locations = new ArrayList<>();
		}

		_stagelocationsL = new ArrayList<>();
		_stagelocationsL = (ArrayList<Integer>) plugin.getConfig().get("stagelocationsL");
		if (_stagelocationsL == null) {
			_stagelocationsL = new ArrayList<>();
		}

		_stagelocationsR = new ArrayList<>();
		_stagelocationsR = (ArrayList<Integer>) plugin.getConfig().get("stagelocationsR");
		if (_stagelocationsR == null) {
			_stagelocationsR = new ArrayList<>();
		}

		_joinGameL = new ArrayList<>();
		_joinGameL = (ArrayList<Integer>) plugin.getConfig().get("joinGameL");
		if (_joinGameL == null) {
			_joinGameL = new ArrayList<>();
		}

		_joinGameR = new ArrayList<>();
		_joinGameR = (ArrayList<Integer>) plugin.getConfig().get("joinGameR");
		if (_joinGameR == null) {
			_joinGameR = new ArrayList<>();
		}

		_chestlocations = new ArrayList<>();
		_chestlocations = (ArrayList<Integer>) plugin.getConfig().get("chestlocations");
		if (_chestlocations == null) {
			_chestlocations = new ArrayList<>();
		}

		_startpoints = new ArrayList<>();
		_startpoints = (ArrayList<ArrayList<Integer>>) plugin.getConfig().get("Startpoints");
		if (_startpoints == null) {
			_startpoints = new ArrayList<>();
		}

		isRandom = plugin.getConfig().getBoolean("isRandom");

		_deathpoint = new ArrayList<>();
		_deathpoint = (ArrayList<Integer>) plugin.getConfig().get("Deathpoint");
		if (_deathpoint == null) {
			_deathpoint = new ArrayList<>();
		}

		_lobbypoint = new ArrayList<>();
		_lobbypoint = (ArrayList<Integer>) plugin.getConfig().get("Lobbypoint");
		if(_lobbypoint == null){
			_lobbypoint = new ArrayList<>();
		}

		_Timer = new ArrayList<>();
		_Timer = (ArrayList<Integer>) plugin.getConfig().get("Timer");
		if(_Timer == null){
			_Timer = new ArrayList<>();
		}

		_teams = new HashMap<>();
		if(!plugin.getConfig().getMapList("teams").isEmpty()) {
			_teams = (Map<String, ArrayList<String>>) plugin.getConfig().getMapList("teams").get(0);
		}
		
		 _protectedBlocks = new ArrayList<>();
		_protectedBlocks = (ArrayList<Location>) getProtectedBrocksConfig().get("glasses");
		if(_protectedBlocks == null){
			_protectedBlocks = new ArrayList<>();
		}
	}

	public static void setSign(Location loc){
		loadConfig();

		plugin.getConfig().set("SignValue.x", loc.getBlockX());
		plugin.getConfig().set("SignValue.y", loc.getBlockY());
		plugin.getConfig().set("SignValue.z", loc.getBlockZ());

		plugin.saveConfig();
	}

	/*
	 * ステージの対角線の座標
	 *
	 * @param loc コマンドを打った人の現在地の座標
	 *
	 * @param player コマンド発信者
	 *
	 * @param jud stageLとstageRのどちらがコマンドとして入力されたかの識別子
	 */
	public static void makeStage(Location loc, Player player, int judge){
		loadConfig();

		switch(judge){
		case 0:
			_stagelocationsL.add(loc.getBlockX());
			_stagelocationsL.add(loc.getBlockZ());

			plugin.getConfig().set("stagelocationsL", null);
			plugin.getConfig().set("stagelocationsL", _stagelocationsL);
			plugin.saveConfig();

			player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "StageLを設定しました");

			return;

		case 1:
			_stagelocationsR.add(loc.getBlockX());
			_stagelocationsR.add(loc.getBlockZ());

			plugin.getConfig().set("stagelocationsR", null);
			plugin.getConfig().set("stagelocationsR", _stagelocationsR);
			plugin.saveConfig();

			player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "StageRを設定しました");

			return;
		}
	}

	public static void makeJoinGame(Location loc, Player player, int judge){
		loadConfig();

		switch(judge){
		case 0:
			_joinGameL.add(loc.getBlockX());
			_joinGameL.add(loc.getBlockZ());

			plugin.getConfig().set("joinGameL", null);
			plugin.getConfig().set("joinGameL", _joinGameL);
			plugin.saveConfig();

			player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "joinGameLを設定しました");

			return;

		case 1:
			_joinGameR.add(loc.getBlockX());
			_joinGameR.add(loc.getBlockZ());

			plugin.getConfig().set("joinGameR", null);
			plugin.getConfig().set("joinGameR", _joinGameR);
			plugin.saveConfig();

			player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "joinGameRを設定しました");

			return;
		}
	}

	//lobbypointの設定
	public static void setLobbypoint(Location loc, Player player){
		loadConfig();

		_lobbypoint.add((int)loc.getX());
		_lobbypoint.add((int)loc.getY());
		_lobbypoint.add((int)loc.getZ());

		plugin.getConfig().set("Lobbypoint", null);

		plugin.getConfig().set("Lobbypoint", _lobbypoint);
		plugin.saveConfig();
	}

	//startpointの設定
	public static void setStartpoint(Location loc, Player player){
		loadConfig();

		ArrayList<Integer> point = new ArrayList<>();

		point.add((int)loc.getX());
		point.add((int)loc.getY());
		point.add((int)loc.getZ());

		_startpoints.add(point);

		plugin.getConfig().set("Startpoints", null);

		plugin.getConfig().set("Startpoints", _startpoints);
		plugin.saveConfig();
	}

	public static void setStartpointRandom(Player player){
		loadConfig();

		plugin.getConfig().set("isRandom", null);
		plugin.getConfig().set("isRandom", !isRandom);
		isRandom = !isRandom;

		player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "ランダムを" + isRandom + "にしました");

		plugin.saveConfig();
	}

	//Deathpointの設定
	public static void setDeathpoint(Location loc, Player player){
		loadConfig();

		_deathpoint.add((int)loc.getX());
		_deathpoint.add((int)loc.getY());
		_deathpoint.add((int)loc.getZ());

		plugin.getConfig().set("Deathpoint", null);
		plugin.getConfig().set("Deathpoint", _deathpoint);
		plugin.saveConfig();
	}

	//禁止区域追加までの時間の設定
	public static void setTimer(int a, int b){
		loadConfig();

		//追加する前に配列の要素をリセット
		_Timer = new ArrayList<>();
		_Timer.add(a);
		_Timer.add(b);

		plugin.getConfig().set("Timer", null);
		plugin.getConfig().set("Timer", _Timer);
		plugin.saveConfig();
	}

	//最初の攻撃不可の時間の設定
	public static void setNATimer(int parseInt) {
		loadConfig();

		plugin.getConfig().set("NATimer", null);
		plugin.getConfig().set("NATimer", parseInt);
		plugin.saveConfig();
	}

	//chestの設定
	public static void subChestConfig(Location loc, Player player){
		loadConfig();

		if(_chestlocations.contains(loc)){
			player.sendMessage(BattleRoyale.prefix + ChatColor.RED + "このチェストは既に追加されています");
			return;
		}

		chestCount = plugin.getConfig().getInt("chestCounter");
		chestCount++;

		_chestlocations.add(loc.getBlockX());
		_chestlocations.add(loc.getBlockY());
		_chestlocations.add(loc.getBlockZ());

		plugin.getConfig().set("chestlocations", null);
		plugin.getConfig().set("chestCounter", chestCount);
		plugin.getConfig().set("chestlocations"+chestCount+".x", _chestlocations.get(0));
		plugin.getConfig().set("chestlocations"+chestCount+".y", _chestlocations.get(1));
		plugin.getConfig().set("chestlocations"+chestCount+".z", _chestlocations.get(2));
		plugin.saveConfig();

		player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "このチェストを追加しました");
	}

	/*
	 * マップを保存する
	 */
	@SuppressWarnings("deprecation")
	public static void setMap(ItemStack map){
		loadConfig();
		MapView mapView = Bukkit.getServer().getMap(map.getDurability());
		plugin.getConfig().set("mapWorld", mapView.getWorld().getName());
		plugin.getConfig().set("mapNum", map.getDurability());
		plugin.getConfig().set("mapX", mapView.getCenterX());
		plugin.getConfig().set("mapZ", mapView.getCenterZ());
		plugin.getConfig().set("mapScale", mapView.getScale().name());
		broadcast(mapView.getScale().name() );
		plugin.saveConfig();
	}

	/*
	 * マップを配布する
	 */
	@SuppressWarnings("deprecation")
	public static void giveMap(){
		loadConfig();
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			//新しい地図データを作る
	        MapView view = Bukkit.getServer().createMap(plugin.getServer().getWorld(plugin.getConfig().getString("mapWorld")));

	        //座標と縮尺を設定
	        view.setCenterX(plugin.getConfig().getInt("mapX"));
	        view.setCenterZ(plugin.getConfig().getInt("mapZ"));

			Scale scale = Scale.FARTHEST;
			String scaleString = plugin.getConfig().getString("mapScale");
			if(scaleString.equalsIgnoreCase("CLOSEST")){
				scale = Scale.CLOSEST;
			}if(scaleString.equalsIgnoreCase("CLOSE")){
				scale = Scale.CLOSE;
			}if(scaleString.equalsIgnoreCase("NORMAL")){
				scale = Scale.NORMAL;
			}if(scaleString.equalsIgnoreCase("FAR")){
				scale = Scale.FAR;
			}if(scaleString.equalsIgnoreCase("FARTHEST")){
				scale = Scale.FARTHEST;
			}
	        view.setScale(scale);

	        //設定したマップをレンダリング
	        MapView setMap = Bukkit.getServer().getMap((short)plugin.getConfig().getInt("mapNum"));
	        //座標と縮尺を設定
	        setMap.setCenterX(plugin.getConfig().getInt("mapX"));
	        setMap.setCenterZ(plugin.getConfig().getInt("mapZ"));
	        setMap.setScale(scale);

	        //レンダーを追加
	        for(MapRenderer ren : setMap.getRenderers()){
	        	view.addRenderer(ren);
	        }
	        view.addRenderer(new CursorRenderer());
	        view.addRenderer(new CustomMap());

	        //マップを初期設定
	        initializeMap(view);

	        ItemStack item = new ItemStack(Material.MAP, 1, view.getId());
			player.getInventory().addItem(item);
			player.updateInventory();
		}
	}

	/*
	 * マップの初期設定
	 */
	public static void initializeMap(MapView map){
		plugin.reloadConfig();
		String scaleString = plugin.getConfig().getString("mapScale");
		Scale scale = Scale.FARTHEST;
		if(scaleString.equalsIgnoreCase("CLOSEST")){
			scale = Scale.CLOSEST;
		}if(scaleString.equalsIgnoreCase("CLOSE")){
			scale = Scale.CLOSE;
		}if(scaleString.equalsIgnoreCase("NORMAL")){
			scale = Scale.NORMAL;
		}if(scaleString.equalsIgnoreCase("FAR")){
			scale = Scale.FAR;
		}if(scaleString.equalsIgnoreCase("FARTHEST")){
			scale = Scale.FARTHEST;
		}

		int edgeX = map.getCenterX();
		int edgeZ = map.getCenterZ();

		//マップの左下の座標を計算
		//CLOSESTの時、1ピクセル=座標1
		float locPerPix = 16;
		float pixPerLoc = 1;

		if(scale.equals(Scale.CLOSEST)){
			edgeX -= 64;
			edgeZ -= 64;
		}if(scale.equals(Scale.CLOSE)){
			edgeX -= 128;
			edgeZ -= 128;
			locPerPix /= 2;
			pixPerLoc *= 2;
		}
		if(scale.equals(Scale.NORMAL)){
			edgeX -= 256;
			edgeZ -= 256;
			locPerPix /= 4;
			pixPerLoc *= 4;
		}
		if(scale.equals(Scale.FAR)){
			edgeX -= 512;
			edgeZ -= 512;
			locPerPix /= 8;
			pixPerLoc *= 8;
		}
		if(scale.equals(Scale.FARTHEST)){
			edgeX -= 1024;
			edgeZ -= 1024;
			locPerPix /= 16;
			pixPerLoc *= 16;
		}

		CustomMap.edgeX = edgeX;
		CustomMap.edgeZ = edgeZ;
		CustomMap.locPerPix = locPerPix;
		CustomMap.pixPerLoc = pixPerLoc;

		CursorRenderer.edgeX = edgeX;
		CursorRenderer.edgeZ = edgeZ;
		CursorRenderer.locPerPix = locPerPix;
		CursorRenderer.pixPerLoc = pixPerLoc;
	}
	
	//破壊できないブロックを追加する
	public static void addProtectedBlocks(Location loc, Player player) {
		loadConfig();
		
		if(_protectedBlocks.isEmpty() || !_protectedBlocks.contains(loc)) {
			_protectedBlocks.add(loc);
			player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "このブロックを破壊できないようにしました。");
		}else{
			_protectedBlocks.remove(loc);
			player.sendMessage(BattleRoyale.prefix + ChatColor.GREEN + "このブロックを破壊できるようにしました。");
		}
		
		FileConfiguration config = getProtectedBrocksConfig();
		config.set("glasses", null);
		config.set("glasses", _protectedBlocks);
		saveProtectedBlocksConfig(config);
	}

	/*
	 * コンフィグファイル生成
	 */
	//チェストアイテム
	public static void saveDefaultChestItemsConfig(){
		File file = new File(plugin.getDataFolder(), "chestItemsConfig.yml");
		if(!file.exists()){
			FileConfiguration config = new YamlConfiguration();
			try{
				config.save(file);
			}catch(IOException e){}
		}
	}
	//初期アイテム
	public static void saveDefaultFirstItemsConfig(){
		File file = new File(plugin.getDataFolder(), "firstItemsConfig.yml");
		if(!file.exists()){
			YamlConfiguration config = new YamlConfiguration();
			try{
				config.save(file);
			}catch(IOException e){}
		}
	}
	
	//非破壊ブロック用
	public static void saveDefaultProtectedBlocksConfig(){
		File file = new File(plugin.getDataFolder(), "protectedBlocksConfig.yml");
		if(!file.exists()){
			YamlConfiguration config = new YamlConfiguration();
			try{
				config.save(file);
			}catch(IOException e){
				Bukkit.broadcastMessage(e.toString());
			}
		}
	}

	/*
	 * チェストアイテム用Configをリロードし、最新のファイル情報を渡す
	 */
	public static FileConfiguration getChestItemsConfig(){
		return YamlConfiguration.loadConfiguration( new File(plugin.getDataFolder(), "chestItemsConfig.yml"));
	}
	public static FileConfiguration getFirstItemsConfig(){
		return YamlConfiguration.loadConfiguration( new File(plugin.getDataFolder(), "firstItemsConfig.yml"));
	}
	//破壊できないガラス用Config
	public static FileConfiguration getProtectedBrocksConfig(){
		return YamlConfiguration.loadConfiguration( new File(plugin.getDataFolder(), "protectedBlocksConfig.yml"));
	}

	/*
	 * configを上書き
	 */
	//チェストアイテム
	public static void saveChestItemsConfig(FileConfiguration config){
		File file = new File(plugin.getDataFolder(), "chestItemsConfig.yml");
		try{
			config.save(file);
		}catch(IOException e){}
	}
	//初期アイテム
	public static void saveFirstItemsConfig(FileConfiguration config){
		File file = new File(plugin.getDataFolder(), "firstItemsConfig.yml");
		try{
			config.save(file);
		}catch(IOException e){}
	}
	
	//非破壊ブロック用
	public static void saveProtectedBlocksConfig(FileConfiguration config){
		File file = new File(plugin.getDataFolder(), "protectedBlocksConfig.yml");
		try{
			config.save(file);
		}catch(IOException e){}
	}
	
	// デバッグ用
	public static void broadcast(String message) {
		BattleRoyale.broadcast(message);
	}
}