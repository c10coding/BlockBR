package me.caleb.BlockBR;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import me.caleb.BlockBR.utils.Chat;
import me.caleb.BlockBR.utils.Rewards;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BlockBR{
	
	public static Player player;
	private static Block block;
	private static Main plugin;
	public static double threshold;
	public static int amount;
	
	public static int level;
	public static String tier;
	private static String logList[] = {"Oak_log","Birch_log","Acacia_Log","Jungle_log","Dark_Oak_Log","Spruce_Log"};
	public static String tierList[] = {"grass","log","stone","coal","redstone","lapis","iron","gold","obsidian","diamond","emerald"};
 	private static int grassValue, logValue, stoneValue, coalValue, redstoneValue, lapisValue, ironValue, goldValue, obsidianValue, diamondValue, emeraldValue;
	private static double grassMult, logMult, stoneMult, coalMult, redstoneMult, lapisMult, ironMult, goldMult, obsidianMult, diamondMult, emeraldMult;
	public static HashMap<String, Integer> tierValues = new HashMap<String, Integer>();
	public static HashMap<String, Double> multValues = new HashMap<String, Double>();
	
	public BlockBR(Main plugin) {
		
		this.plugin = plugin; 
		
		setVars();
		
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
		
		//Action bar message
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Chat.chat("&b&lLevel: &l" + level + " &r&l| &r&l[" + "&d&l" + tier.toUpperCase() + "&r&l] " + "&6&l" + amount + "&b&l/&6&l" + (int) threshold)));
		
	}
	
	public static void setVars() {
		
		 grassValue = plugin.getConfig().getInt("GrassValue");
		 logValue = plugin.getConfig().getInt("LogValue");
		 stoneValue = plugin.getConfig().getInt("StoneValue");
		 coalValue = plugin.getConfig().getInt("CoalValue");
		 ironValue = plugin.getConfig().getInt("IronValue");
		 redstoneValue = plugin.getConfig().getInt("RedstoneValue");
		 lapisValue = plugin.getConfig().getInt("LapisValue");
		 goldValue = plugin.getConfig().getInt("GoldValue");
		 obsidianValue = plugin.getConfig().getInt("ObsidianValue");
		 diamondValue = plugin.getConfig().getInt("DiamondValue");
		 emeraldValue = plugin.getConfig().getInt("EmeraldValue");
		
		 grassMult = plugin.getConfig().getDouble("GrassMult");
		 logMult = plugin.getConfig().getDouble("LogMult");
		 stoneMult = plugin.getConfig().getDouble("StoneMult");
		 coalMult = plugin.getConfig().getDouble("CoalMult");
		 ironMult = plugin.getConfig().getDouble("IronMult");
		 redstoneMult = plugin.getConfig().getDouble("RedstoneMult");
		 lapisMult = plugin.getConfig().getDouble("LapisMult");
		 goldMult = plugin.getConfig().getDouble("GoldMult");
		 obsidianMult = plugin.getConfig().getDouble("ObsidianMult");
		 diamondMult = plugin.getConfig().getDouble("DiamondMult");
		 emeraldMult = plugin.getConfig().getDouble("EmeraldMult");
		
		//Puts the variables into the tierValues HashMap
		tierValues.put("grass", grassValue);
		tierValues.put("log", logValue);
		tierValues.put("stone", stoneValue);
		tierValues.put("coal", coalValue);
		tierValues.put("iron", ironValue);
		tierValues.put("redstone", redstoneValue);
		tierValues.put("lapis", lapisValue);
		tierValues.put("gold", goldValue);
		tierValues.put("obsidian", obsidianValue);
		tierValues.put("diamond", diamondValue);
		tierValues.put("emerald", emeraldValue);
		
		//Puts the variables into the multValues HashMap
		multValues.put("grass", grassMult);
		multValues.put("log", logMult);
		multValues.put("stone", stoneMult);
		multValues.put("coal", coalMult);
		multValues.put("iron", ironMult);
		multValues.put("redstone", redstoneMult);
		multValues.put("lapis", lapisMult);
		multValues.put("gold", goldMult);
		multValues.put("obsidian", obsidianMult);
		multValues.put("diamond", diamondMult);
		multValues.put("emerald", emeraldMult);
		
	}
	
	public static void checkThreshold(int amount) {
		   
		if(level == 1) {
			threshold = tierValues.get(tier);
		}else {
			//Takes the initial value and multiplies it by the multiplier times the level-1
			threshold = tierValues.get(tier) * (multValues.get(tier)*(level-1));
		}
		
		if(threshold == amount)
			levelUpLevelAndTier();
		else
			return;
		
	}
	
	public static void levelUpLevelAndTier() {
		
		int index;
		Rewards r = new Rewards(plugin);
		
		// Takes you to the next tier
		for(int x = 0;x < tierList.length;x++) {
			//If this is the last tier
			if(tier.equalsIgnoreCase(tierList[tierList.length-1])) {
				
				String lastTier = tier;
				int lastLevel = level;
				
				Boolean broadcast;
				// Try to get the config value broadcast. If it's not set, then set it to false
				
				try {
					broadcast = plugin.getConfig().getBoolean("Broadcast");
				}catch(NullPointerException e) {
					broadcast = false;
				}

				//New Level and tier is set back to grass
				level++;
				tier = "grass";
				
				if(broadcast.equals(true)) {
					Bukkit.broadcastMessage(Chat.blockBrChat("&l" + player.getName() + " &5has reached level &6") + String.valueOf(level));
				}
				
				player.sendMessage(Chat.blockBrChat("Congratulations! You have leveled up to level " + level + ". You are now back at tier &6" + tier));
				
				r.giveKey(player,lastTier,lastLevel);
				r.giveMoney(player, lastTier, lastLevel);
				
				spawnFireWorks();
				upgradeData(lastTier);
				
				return;
			}else {
				//New Tier
				if(tierList[x].equalsIgnoreCase(tier)) {	
					
					String lastTier = tier;
					tier = tierList[x+1];
					player.sendMessage(Chat.blockBrChat("Congratulations! You have gone up a tier. You are now on tier &l&6" + tier));
					
					r.giveKey(player,lastTier,level);
					r.giveMoney(player, lastTier, level);
					
					spawnFireWorks();
					upgradeData(lastTier);
					
					return;
				}	
			}
		}
	}
	
	public static void upgradeData(String lastTier) {
		
		try {
			
			//Updates the level
			PreparedStatement stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET level=? WHERE playerName=?");
			stmt.setString(1, String.valueOf(level));
			stmt.setString(2, player.getName());
			stmt.executeUpdate();
			
			//Makes the count to 0
				if(lastTier.equalsIgnoreCase("grass")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET grass=? WHERE playerName=?");
					
				}else if(lastTier.contentEquals("log")){
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET log=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("stone")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET stone=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("coal")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET coal=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("redstone")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET redstone=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("lapis")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET lapis=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("iron")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET iron=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("gold")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET gold=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("obsidian")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET obsidian=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("diamond")) {
					
					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET diamond=? WHERE playerName=?");
					
				}else if(lastTier.equalsIgnoreCase("emerald")) {

					stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET emerald=? WHERE playerName=?");
					
				}else {
					player.sendMessage(Chat.blockBrChat("An error has ocurred..."));
				}		
				
				stmt.setString(1, "0");
				stmt.setString(2, player.getName());
				stmt.executeUpdate();
				
				//Sets the tier
				stmt = plugin.getConnection().prepareStatement("UPDATE `blockbr` SET tier=? WHERE playerName=?");
				stmt.setString(1, tier);
				stmt.setString(2, player.getName());
				stmt.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void spawnFireWorks() {
		
		Location playerLoc = player.getLocation();
		Firework fw = (Firework) playerLoc.getWorld().spawnEntity(playerLoc, EntityType.FIREWORK);
		
		FireworkMeta fwm = fw.getFireworkMeta();
		FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.AQUA).build();
		fwm.addEffect(effect);
		
		fw.setFireworkMeta(fwm);
		
		detonate(fw);
	}
	
	public static void detonate(final Firework fw) {
		//Waits 1 tick and then it detonates
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                try{
                    fw.detonate();
                }catch(Exception e){}
            }
        }, (3));
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
				
				if(blockName.equalsIgnoreCase("grass_block") || blockName.equalsIgnoreCase("dirt")) {
					increaseAmount(tier);
				}else {
					return;
				}
				
			}else if(tier.contentEquals("log")){
				
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