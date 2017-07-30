package com.plugin.ftb.battleroyale;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class PlusDeathArea {

	public static BattleRoyale plugin = BattleRoyale.plugin;
	public static ArrayList<Integer> locL = new ArrayList<Integer>();
	public static ArrayList<Integer> locR = new ArrayList<Integer>();
	public static ArrayList<Integer> plusDeathX = new ArrayList<Integer>();
	public static ArrayList<Integer> plusDeathZ = new ArrayList<Integer>();

	public static int betaLx, betaLz, betaRx, betaRz;
	public static int deathAreaCount=0;
	public static int beta=0;

	public void setPlusDeath(){
		locL = (ArrayList<Integer>) plugin.getConfig().getIntegerList("stagelocationsL");
		locR = (ArrayList<Integer>) plugin.getConfig().getIntegerList("stagelocationsR");

		betaLx = (int)locL.get(0);
		betaLz = (int)locL.get(1);
		betaRx = (int)locR.get(0);
		betaRz = (int)locR.get(1);

		/*         z軸
		 *          |
		 *          |
		 *          |
		 * ---------+--------→ x軸
		 *          |
		 *          |
		 *          |
		 *          ↓
		 */
		if ((int)locL.get(0)>=(int)locR.get(0)&&(int)locL.get(1)>=(int)locR.get(1)){

			//Lがx座標、z座標共にRより大きい場合
			do{
				if(betaLz>=(int)locR.get(1)){
					if(betaLx>=(int)locR.get(0)){
						plusDeathX.add(betaLx);
						plusDeathZ.add(betaLz);
						betaLx-=64;
					}else{
						betaLx = (int)locL.get(0);
						betaLz-=64;
					}
				}else{
					break;
				}
			}while(true);

		}else if ((int)locL.get(0)<(int)locR.get(0)&&(int)locL.get(1)<(int)locR.get(1)){

			//Lがx座標、z座標共にRより小さい場合
			do{
				if(betaLz<=(int)locR.get(1)){
					if(betaLx<=(int)locR.get(0)){
						plusDeathX.add(betaLx);
						plusDeathZ.add(betaLz);
						betaLx+=64;
					}else{
						betaLx = (int)locL.get(0);
						betaLz+=64;
					}
				}else{
					break;
				}
			}while(true);


		}else if ((int)locL.get(0)>=(int)locR.get(0)&&(int)locL.get(1)<(int)locR.get(1)){

			//x座標はLが大きく、z座標はRが大きい場合
			do{
				if(betaLz<=(int)locR.get(1)){
					if(betaLx>=(int)locR.get(0)){
						plusDeathX.add(betaLx);
						plusDeathZ.add(betaLz);
						betaLx-=64;
					}else{
						betaLx = (int)locL.get(0);
						betaLz+=64;
					}
				}else{
					break;
				}
			}while(true);


		}else if ((int)locL.get(0)<(int)locR.get(0)&&(int)locL.get(1)>=(int)locR.get(1)){

			//x座標はRが大きく、z座標はLが大きい場合
			do{
				if(betaLz>=(int)locR.get(1)){
					if(betaLx<=(int)locR.get(0)){
						plusDeathX.add(betaLx);
						plusDeathZ.add(betaLz);
						betaLx+=64;
					}else{
						betaLx = (int)locL.get(0);
						betaLz-=64;
					}
				}else{
					break;
				}
			}while(true);
		}
	}

	public void plus(){

		PlusThreadClass pth = new PlusThreadClass();
		PlusDeathThreadClass pdth = new PlusDeathThreadClass();

		pth.runTaskTimer(plugin, 0, 20);
		pdth.runTaskTimer(plugin, 0, 20);
	}
}

class PlusThreadClass extends BukkitRunnable{

	public static BattleRoyale plugin = BattleRoyale.plugin;
	public static final String TEAM_ALIVE_NAME = BattleRoyale.TEAM_ALIVE_NAME;
	public static final String TEAM_DEAD_NAME = BattleRoyale.TEAM_DEAD_NAME;

	public static ArrayList<Integer> deathRandom = new ArrayList<Integer>();

	//猶予段階のエリア
	public static ArrayList<Integer> deathRandomCount = new ArrayList<Integer>();
	//猶予後のエリア
	public static ArrayList<Integer> deathRandomCountPast = new ArrayList<Integer>();

	public static ArrayList<Integer> Timer = new ArrayList<Integer>();

	public static int count=0;
	public static int countPast=0;
	//禁止区域が追加されるまでのカウンター
	public static int loopC=plugin.getConfig().getIntegerList("Timer").get(0);
	//攻撃が可能になるまでの時間
	public static int attackCountDown=plugin.getConfig().getInt("NATimer");

	/*
	 *スコアボードに禁止区域追加までの時間を表示させるため、追加方法の処理を変更しました
	 */
	@SuppressWarnings("deprecation")
	public void run(){
		Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();

		//禁止区域追加30秒前
		if(loopC==30){
			if(!(board.getTeam(TEAM_ALIVE_NAME).getPlayers().size()>1)){
				//Bukkit.broadcastMessage("test");
				this.cancel();
				return;
			}
			if(PlusDeathArea.beta==0){
				for(int i=0; i<PlusDeathArea.plusDeathX.size(); i++){
					deathRandom.add(PlusDeathArea.beta);

					PlusDeathArea.beta++;
					Collections.shuffle(deathRandom);
				}
			}
			deathRandomCount.add(count);
			Bukkit.broadcastMessage(BattleRoyale.prefix + ChatColor.RED + "30秒後" + ChatColor.GRAY + "に禁止区域が追加されます。");
			count++;
		}

		//禁止区域追加
		if(loopC==0){
			if(!(board.getTeam(TEAM_ALIVE_NAME).getPlayers().size()>1)){
				this.cancel();
				return;
			}
			deathRandomCountPast.add(countPast);
			Bukkit.broadcastMessage(BattleRoyale.prefix + ChatColor.RED + "禁止区域が追加されました。");
			countPast++;
			//二週目からはずっと同じ一定時間
			loopC=plugin.getConfig().getIntegerList("Timer").get(1);
		}

		//攻撃可能になるまでの時間が0になったら攻撃を可能にする
		if(attackCountDown == 0){
			MainListener.Attack = false;
		}else{
			attackCountDown--;
		}

		//１秒ごとにカウントを減らしていく。
		loopC--;

		//スコアボードの設定
		ScoreBoard.scoreSide(true);
	}
}

class PlusDeathThreadClass extends BukkitRunnable{

	public static BattleRoyale plugin = BattleRoyale.plugin;
	public static final String TEAM_ALIVE_NAME = BattleRoyale.TEAM_ALIVE_NAME;

	public static ArrayList<Integer> locL = new ArrayList<Integer>();
	public static ArrayList<Integer> locR = new ArrayList<Integer>();

	public static int betaLx, betaLz, betaRx, betaRz;

	//deathAreaCount++;

	@SuppressWarnings("deprecation")
	public void run(){
		Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
		locL = (ArrayList<Integer>) plugin.getConfig().getIntegerList("stagelocationsL");
		locR = (ArrayList<Integer>) plugin.getConfig().getIntegerList("stagelocationsR");

		if(!(board.getTeam(TEAM_ALIVE_NAME).getPlayers().size()>1)){
        	this.cancel();
        }

		for(int i:PlusThreadClass.deathRandomCountPast){
			//Bukkit.broadcastMessage(PlusThreadClass.deathRanCount.size() + ", " + PlusThreadClass.deathRan.size());
			if(PlusThreadClass.deathRandomCount.isEmpty() || PlusThreadClass.deathRandom.isEmpty()){
				return;
			}

			int r = PlusThreadClass.deathRandom.get(i);
			int pdaX = (int)PlusDeathArea.plusDeathX.get(r);
			int pdaZ = (int)PlusDeathArea.plusDeathZ.get(r);

			if ((int)locL.get(0)>=(int)locR.get(0)&&(int)locL.get(1)>=(int)locR.get(1)){
				for(OfflinePlayer p : board.getTeam(TEAM_ALIVE_NAME).getPlayers()){
					//p.getPlayer().sendMessage(String.valueOf(PlusThreadClass.deathRan));
					if(p.isOnline()){
						if(pdaX-64<=(int)p.getPlayer().getLocation().getX()&&pdaX>=(int)p.getPlayer().getLocation().getX()
								&&pdaZ-64<=(int)p.getPlayer().getLocation().getZ()&&pdaZ>=(int)p.getPlayer().getLocation().getZ()){

							p.getPlayer().setHealth(0.0D);

						}
					}
				}
			}else if ((int)locL.get(0)<(int)locR.get(0)&&(int)locL.get(1)<(int)locR.get(1)){
				for(OfflinePlayer p : board.getTeam(TEAM_ALIVE_NAME).getPlayers()){
					//p.getPlayer().sendMessage(String.valueOf(PlusThreadClass.deathRan));
					if(p.isOnline()){
						if(pdaX+64>=(int)p.getPlayer().getLocation().getX()&&pdaX<=(int)p.getPlayer().getLocation().getX()
								&&pdaZ+64>=(int)p.getPlayer().getLocation().getZ()&&pdaZ<=(int)p.getPlayer().getLocation().getZ()){

							p.getPlayer().setHealth(0.0D);

						}
					}
				}

			}else if ((int)locL.get(0)>=(int)locR.get(0)&&(int)locL.get(1)<(int)locR.get(1)){
				for(OfflinePlayer p : board.getTeam(TEAM_ALIVE_NAME).getPlayers()){
					//p.getPlayer().sendMessage(String.valueOf(PlusThreadClass.deathRan));
					if(p.isOnline()){
						if(pdaX-64<=(int)p.getPlayer().getLocation().getX()&&pdaX>=(int)p.getPlayer().getLocation().getX()
								&&pdaZ+64>=(int)p.getPlayer().getLocation().getZ()&&pdaZ<=(int)p.getPlayer().getLocation().getZ()){

							p.getPlayer().setHealth(0.0D);

						}
					}
				}

			}else if ((int)locL.get(0)<(int)locR.get(0)&&(int)locL.get(1)>=(int)locR.get(1)){
				for(OfflinePlayer p : board.getTeam(TEAM_ALIVE_NAME).getPlayers()){
					//p.getPlayer().sendMessage(String.valueOf(PlusThreadClass.deathRan));
					if(p.isOnline()){
						if(pdaX+64>=(int)p.getPlayer().getLocation().getX()&&pdaX<=(int)p.getPlayer().getLocation().getX()
								&&pdaZ-64<=(int)p.getPlayer().getLocation().getZ()&&pdaZ>=(int)p.getPlayer().getLocation().getZ()){

							p.getPlayer().setHealth(0.0D);

						}
					}
				}
			}
		}
	}
}
