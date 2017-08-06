package com.plugin.ftb.battleroyale.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.plugin.ftb.battleroyale.MainConfig;

public class TeamConfig extends MainConfig{
	/*
	 * チームが存在するか返す
	 */
	public static Boolean isTeamExist(String teamName) {
		loadConfig();
		if(!_teams.keySet().isEmpty() && _teams.keySet().contains(teamName)) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 *　プレイヤーがチームに参加しているか返す
	 */
	public static boolean teamHasPlayer(Player player) {
		loadConfig();
		//チームが1つもない場合
		if(_teams.keySet().isEmpty()) {
			return false;
		}
		//valuesにplayerが含まれているか検索
		for(ArrayList<String> uuids : _teams.values()) {
			if(uuids.contains(player.getUniqueId().toString())){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * プレイヤーが参加しているチームを返す
	 */
	public static String joiningTeamName(Player player) {
		loadConfig();
		for(String teamName : _teams.keySet()) {
			if(_teams.get(teamName).contains(player.getUniqueId().toString())) {
				return teamName;
			}
		}
		return "";
	}
	
	/*
	 * チームをconfigに書き込み
	 */
	public static void makeTeam(String name) {
		loadConfig();
		if(_teams.keySet() != null && _teams.keySet().size() == 45) {
			//45個以上作れないようにする
			return;
		}
		//configに書き込み
		_teams.put(name, new ArrayList<>());
		//一度List<Map<>()に変換する
		List<Map<String,ArrayList<String>>> mapList = new ArrayList<Map<String,ArrayList<String>>>();
		mapList.add(_teams);
		plugin.getConfig().set("teams", null);
		plugin.getConfig().set("teams", mapList);
		plugin.saveConfig();
	}
	
	/*
	 * チームをすべて削除
	 */
	public static void removeAllTeams() {
		loadConfig();
		_teams = new HashMap<>();
		plugin.getConfig().set("teams", null);
		plugin.saveConfig();
	}
	
	/*
	 * チームに参加
	 */
	public static void joinTeam(String teamName, Player player) {
		loadConfig();
		
		//UUIDを追加、保存
		ArrayList<String> uuids = _teams.get(teamName);
		//既に同じチームに入っていないときのみ
		if(!uuids.contains(player.getUniqueId().toString())) {
			uuids.add(player.getUniqueId().toString());
		}
		
		//一度List<Map<>()に変換する
		List<Map<String,ArrayList<String>>> mapList = new ArrayList<Map<String,ArrayList<String>>>();
		mapList.add(_teams);
		
		plugin.getConfig().set("teams", null);
		plugin.getConfig().set("teams", mapList);
		plugin.saveConfig();
	}
	
	/*
	 * チームから脱退
	 */
	public static void leaveTeam(Player player) {
		loadConfig();
		
		String teamName = joiningTeamName(player);
		
		//uuidsからプレイヤーを削除
		ArrayList<String> uuids = _teams.get(teamName);
		uuids.remove(player.getUniqueId().toString());
		_teams.put(teamName, uuids);
		
		//一度List<Map<>()に変換する
		List<Map<String,ArrayList<String>>> mapList = new ArrayList<Map<String,ArrayList<String>>>();
		mapList.add(_teams);
		
		plugin.getConfig().set("teams", null);
		plugin.getConfig().set("teams", mapList);
		plugin.saveConfig();
	}
}
