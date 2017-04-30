package com.plugin.ftb.battleroyale;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class CustomMap extends MapRenderer {

	public static BattleRoyale plugin = BattleRoyale.plugin;

	public static ArrayList<Integer> plusDeathX = PlusDeathArea.plusDeathX;
	public static ArrayList<Integer> plusDeathZ = PlusDeathArea.plusDeathZ;
	public static ArrayList<Integer> deathRan = PlusThreadClass.deathRan;
	public static ArrayList<Integer> locL = PlusDeathArea.locL;
	public static ArrayList<Integer> locR = PlusDeathArea.locR;
	public static String scaleString;
	public static Scale scale = Scale.FARTHEST;
	public static float locPerPix;
	public static float pixPerLoc;
	public static int edgeX;
	public static int edgeZ;
	
	public int pastSize = 0;

	@SuppressWarnings("deprecation")
	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		if(pastSize == PlusThreadClass.deathRanCount.size()){
			return;
		}
		pastSize = PlusThreadClass.deathRanCount.size();
		for(int i:PlusThreadClass.deathRanCount){

			int r = PlusThreadClass.deathRan.get(i);

			int pdaX = (int)PlusDeathArea.plusDeathX.get(r);
			int pdaZ = (int)PlusDeathArea.plusDeathZ.get(r);
			if ((int)locL.get(0)>=(int)locR.get(0)&&(int)locL.get(1)>=(int)locR.get(1)){
				for(int x=0; x<locPerPix; x++){
					for(int z=0; z<locPerPix; z++){
						canvas.setPixel((int)((pdaX - edgeX)/pixPerLoc) - x, (int)((pdaZ - edgeZ)/pixPerLoc) - z, (byte)18);
					}
				}
			}else if ((int)locL.get(0)<(int)locR.get(0)&&(int)locL.get(1)<(int)locR.get(1)){
				for(int x=0; x<locPerPix; x++){
					for(int z=0; z<locPerPix; z++){
						canvas.setPixel((int)((pdaX - edgeX)/pixPerLoc) + x, (int)((pdaZ - edgeZ)/pixPerLoc) + z, (byte)18);
					}
				}
			}else if ((int)locL.get(0)>=(int)locR.get(0)&&(int)locL.get(1)<(int)locR.get(1)){
				for(int x=0; x<locPerPix; x++){
					for(int z=0; z<locPerPix; z++){
						canvas.setPixel((int)((pdaX - edgeX)/pixPerLoc)- x, (int)((pdaZ - edgeZ)/pixPerLoc) + z, (byte)18);
					}
				}

			}else if ((int)locL.get(0)<(int)locR.get(0)&&(int)locL.get(1)>=(int)locR.get(1)){
				for(int x=0; x<locPerPix; x++){
					for(int z=0; z<locPerPix; z++){
						canvas.setPixel((int)((pdaX - edgeX)/pixPerLoc) + x, (int)((pdaZ - edgeZ)/pixPerLoc) - z, (byte)18);
					}
				}
			}
		}
	}

	// ブロードキャスト
	public void broadcast(Object message) {
		BattleRoyale.broadcast(message.toString());
	}
}
