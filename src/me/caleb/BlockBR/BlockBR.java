package me.caleb.BlockBR;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.caleb.BlockBR.utils.Chat;

public class BlockBR {
	
	private static Player player;
	private static Block block;
	private static Main plugin;
	
	private static int level;
	private static String tier;
	private static String logList[] = {"Log_1","Log_2","Log_3","Log_4","Log_5","Log_6"};
	
	/*
	private static int grassValue = plugin.getConfig().getInt("GrassValue");
	private static int logValue = plugin.getConfig().getInt("LogValue");
	private static int stoneValue = plugin.getConfig().getInt("StoneValue");
	private static int coalValue = plugin.getConfig().getInt("CoalValue");
	private static int ironValue = plugin.getConfig().getInt("IronValue");
	private static int redstoneValue = plugin.getConfig().getInt("RedstoneValue");
	private static int lapisValue = plugin.getConfig().getInt("LapisValue");
	private static int goldValue = plugin.getConfig().getInt("GoldValue");
	private static int obsidianValue = plugin.getConfig().getInt("ObsidianValue");
	private static int diamondValue = plugin.getConfig().getInt("DiamondValue");
	private static int emeraldValue = plugin.getConfig().getInt("EmeraldValue");
	
	private static int grassMult = plugin.getConfig().getInt("GrassMult");
	private static int logMult = plugin.getConfig().getInt("LogMult");
	private static int stoneMult = plugin.getConfig().getInt("StoneMult");
	private static int coalMult = plugin.getConfig().getInt("CoalMult");
	private static int ironMult = plugin.getConfig().getInt("IronMult");
	private static int redstoneMult = plugin.getConfig().getInt("RedstoneMult");
	private static int lapisMult = plugin.getConfig().getInt("LapisMult");
	private static int goldMult = plugin.getConfig().getInt("GoldMult");
	private static int obsidianMult = plugin.getConfig().getInt("ObsidianMult");
	private static int diamondMult = plugin.getConfig().getInt("DiamondMult");
	private static int emeraldMult = plugin.getConfig().getInt("EmeraldMult");
	
	//Level 1 values
	private final static int initValues[] = {grassValue,logValue,stoneValue,coalValue,ironValue,redstoneValue,lapisValue,goldValue,obsidianValue,diamondValue,emeraldValue};
	//Multipliers
	private final static double multValues[] = {grassMult,logMult,stoneMult,coalMult,ironMult,redstoneMult,lapisMult,goldMult,obsidianMult,diamondMult,emeraldMult};
	*/
	public BlockBR(Main plugin) {
		
		this.plugin = plugin; 
/*
		HashMap<String, Integer> tierValues = new HashMap<String, Integer>();
		tierValues.put("Grass", grassValue);
		tierValues.put("Log", logValue);
		tierValues.put("Stone", stoneValue);
		tierValues.put("Coal", coalValue);
		tierValues.put("Iron", ironValue);
		tierValues.put("Redstone", redstoneValue);
		tierValues.put("Lapis", lapisValue);
		tierValues.put("Gold", goldValue);
		tierValues.put("Obsidian", obsidianValue);
		tierValues.put("Diamond", diamondValue);
		tierValues.put("Emerald", emeraldValue);
		
		HashMap<String, Integer> multValues = new HashMap<String, Integer>();
		multValues.put("Grass", grassMult);
		multValues.put("Log", logMult);
		multValues.put("Stone", stoneMult);
		multValues.put("Coal", coalMult);
		multValues.put("Iron", ironMult);
		multValues.put("Redstone", redstoneMult);
		multValues.put("Lapis", lapisMult);
		multValues.put("Gold", goldMult);
		multValues.put("Obsidian", obsidianMult);
		multValues.put("Diamond", diamondMult);
		multValues.put("Emerald", emeraldMult);
		*/
	}
	
	public static void insertPlayer(Player p, Block b) {
		
		player = p;
		block = b;
		String playerName = p.getName();
		
		try {
			
			PreparedStatement stmt = plugin.getConnection().prepareStatement("SELECT * FROM `blockbr` WHERE playerName=?");
			stmt.setString(1, playerName);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				// Player is already in the database
				return;
			}else {
				// Player is not in the database. Put him in
				stmt = plugin.getConnection().prepareStatement("INSERT INTO `blockbr` (playerName,level,tier,grass,log,stone,coal,redstone,lapis,iron,gold,obsidian,diamond,emerald) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				stmt.setString(1, playerName);
				stmt.setString(2, "1");
				stmt.setString(3, "grass");
				stmt.setString(4, "0");
				stmt.setString(5, "0");
				stmt.setString(6, "0");
				stmt.setString(7, "0");
				stmt.setString(8, "0");
				stmt.setString(9, "0");
				stmt.setString(10, "0");
				stmt.setString(11, "0");
				stmt.setString(12, "0");
				stmt.setString(13, "0");
				stmt.setString(14, "0");
				
				stmt.execute();
				
				Bukkit.getConsoleSender().sendMessage(Chat.blockBrChat("Inserted a new player into the database..."));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void ifNatural(boolean natural){
		if(natural == true) {
			findLevelandTier();
			
		}else {
			return;
		}
	}
	
	public static void increaseAmount(String tier) {
		
		// Amount mined at your current tier
		int amount;
		
		//Finds the amount of the current tier they're on
		try {
			PreparedStatement stmt = plugin.getConnection().prepareStatement("SELECT * FROM `blockbr` WHERE playerName=?");
			stmt.setString(1, player.getName());
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			amount = Integer.parseInt(rs.getString(tier));
			//Increments the amount mined
			amount++;
			
			
			// Updates the amount mined in the database
			if(tier.equalsIgnoreCase("grass")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET grass=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("log")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET log=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("stone")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET stone=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("coal")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET coal=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("redstone")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET redstone=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("lapis")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET lapis=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("iron")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET iron=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("gold")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET gold=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("obsidian")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET obsidian=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("diamond")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET diamond=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else if(tier.equalsIgnoreCase("emerald")) {
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET emerald=? WHERE playerName=?");

				stmt.setInt(1, amount);
				stmt.setString(2, player.getName());
			}else {
				player.sendMessage(Chat.blockBrChat("An error has ocurred..."));
			}
			
			
			int results = stmt.executeUpdate();
			
			checkThreshold(amount);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void checkThreshold(int amount) {
		
	}
	
	// Finds the tier and the level
	public static void findLevelandTier() {
		
		try {
			
			PreparedStatement stmt = plugin.getConnection().prepareStatement("SELECT * FROM `blockbr` WHERE playerName=?");
			stmt.setString(1, player.getName());
			double threshold;
			
			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			
			tier = rs.getString("tier");
			level = Integer.parseInt(rs.getString("level"));
			
			String blockName = block.getType().toString();
			
			//If tier is grass, check to see if they have mined dirt or grass.
			//If the tier is anything else, just check accordingly
			if(tier.equalsIgnoreCase("grass")) {
				
				if(blockName.equalsIgnoreCase(tier) || blockName.equalsIgnoreCase("dirt")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.contentEquals("log")){
				
				player.sendMessage(blockName);
				
				for(int x = 0;x < logList.length; x++) {
					if(blockName.equalsIgnoreCase(logList[x])) {
						increaseAmount(tier);
					}
				}
				
			}else if(tier.equalsIgnoreCase("stone")) {
				
				if(blockName.equalsIgnoreCase(tier)) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("coal")) {
				
				if(blockName.equalsIgnoreCase("Coal_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("redstone")) {
				
				if(blockName.equalsIgnoreCase("Redstone_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("lapis")) {
				
				if(blockName.equalsIgnoreCase("Lapis_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("iron")) {
				
				if(blockName.equalsIgnoreCase("Iron_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("gold")) {
				
				if(blockName.equalsIgnoreCase("Gold_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("obsidian")) {
				
				if(blockName.equalsIgnoreCase("Obsidian")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("diamond")) {
				
				if(blockName.equalsIgnoreCase("Diamond_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.equalsIgnoreCase("emerald")) {
				
				if(blockName.equalsIgnoreCase("Emerald_Ore")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else {
				player.sendMessage(Chat.blockBrChat("An error has ocurred..."));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
