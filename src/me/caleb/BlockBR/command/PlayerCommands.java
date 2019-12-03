package me.caleb.BlockBR.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.caleb.BlockBR.BlockBR;
import me.caleb.BlockBR.Main;
import me.caleb.BlockBR.utils.Chat;
import me.caleb.BlockBR.utils.Gui;
import me.caleb.BlockBR.utils.RewardsGui;

public class PlayerCommands implements CommandExecutor{

	Player player;
	
	private Main plugin;
	
	public PlayerCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("blockbr").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		player = (Player) sender;
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use these commands!");
		}else {
			
			if(player.hasPermission("blockbr.use")) {		
				if(args[0].equalsIgnoreCase("menu")) {
					Gui g = new Gui(plugin, "Info",9);
					g.initializeItems(getTier(),getAmount(), getLevel(), getThreshold());
					g.openInventory(player);
				}else if(args[0].equalsIgnoreCase("help")) {
					List<String> list = plugin.getConfig().getStringList("Help");
					sender.sendMessage(Chat.blockBrChat(("List of commands: ")));
					for(String e : list) {
						sender.sendMessage(Chat.chat("&5" + e));
					}
				}
			}
		}
		
		
		return false;
	}
	
	private ResultSet getPlayerData() {
		
		ResultSet rs = null;
		String playerName = player.getName();
		
		try {
			
			PreparedStatement stmt = plugin.getConnection().prepareStatement("SELECT * FROM `blockbr` WHERE playerName=?");
			stmt.setString(1, playerName);
			rs = stmt.executeQuery();
			/*
			 * The ResultSet will equal null if the database gets no rows
			 */
			if(!(rs.next())) rs = null;
			//If it equals null, throw an exception so that the plugin does not execute anymore and also respond to the exception with the catch statement.
			if(rs.equals(null)) {
				throw new NullPointerException("NullRS");
			}
			
			return rs;
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			player.sendMessage(Chat.blockBrChat("You are not in the database! Mine a block, and then try to use this command again"));
		}
		
		return rs;
		
	}
	
	private double getThreshold() {
		
		double threshold = 0;
		ResultSet rs = getPlayerData();
		
		try {
			
			if(Integer.parseInt(rs.getString("level")) == 1) {
				threshold = BlockBR.tierValues.get(rs.getString("tier"));
				return threshold;
			}else {
				threshold = BlockBR.tierValues.get(rs.getString("tier")) * (BlockBR.multValues.get(rs.getString("tier"))*(Integer.parseInt(rs.getString("level"))-1));	
				return threshold;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return threshold;
	}
	
	private int getLevel() {
		
		ResultSet rs = getPlayerData();
		
		try {
			return Integer.parseInt(rs.getString("level"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
		
	}

	private int getAmount(){
		// TODO Auto-generated method stub
		ResultSet rs = getPlayerData();
		
		try {
			return Integer.parseInt(rs.getString(getTier()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}
	// This works
	private String getTier() {
		// TODO Auto-generated method stub
		ResultSet rs = getPlayerData();
		
		try {
			return rs.getString("tier");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
