package me.caleb.BlockBR;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import me.caleb.BlockBR.Main;
import me.caleb.BlockBR.utils.Chat;

public class BlockBR {
	
	private static Player player;
	private static Block block;
	private static Main plugin;
	
	public BlockBR(Main plugin) {
		this.plugin = plugin;
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
			getTier();
		}else {
			return;
		}
	}
	
	public static void getTier() {
		/*
		try {
			PreparedStatement stmt = plugin.getConnection().prepareStatement("SELECT * FROM `blockbr` WHERE ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
}
