package me.caleb.BlockBR.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import me.caleb.BlockBR.BlockBR;
import me.caleb.BlockBR.Main;

public class GetInfo {

	private Main plugin;
	
	public String tier;
	public int level, amount;
	public double threshold;
	public ArrayList<String> rewardNames = new ArrayList<String>();
	public ArrayList<Material> rewardMats = new ArrayList<Material>();
	public ArrayList<Integer> rewardAmounts = new ArrayList<Integer>();
	
	public GetInfo(Main plugin) {
		this.plugin = plugin;
	}
	
	public void getInfo(Player p) {
		
		String playerName = p.getName();
		
		try {
		
			PreparedStatement stmt = plugin.getConnection().prepareStatement("SELECT * FROM `blockbr` WHERE playerName=?");
			stmt.setString(1, playerName);
			
			ResultSet rs = stmt.executeQuery();
			rs.next();
			setTier(rs.getString("tier"));
			setLevel(Integer.parseInt(rs.getString("level")));
			setAmount(Integer.parseInt(rs.getString(tier)));
			
			Rewards r = new Rewards(plugin, p, tier, level);
			
			rewardNames = r.getAllRewardsNames(p, tier, level);
			rewardMats = r.getAllRewardsMaterials(p, tier, level);
			rewardAmounts = r.getAllRewardsAmounts(p, tier, level);
			
			if(Integer.parseInt(rs.getString("level")) == 1) {
				setThreshold(BlockBR.tierValues.get(rs.getString("tier")));
				p.sendMessage(String.valueOf(threshold));
			}else {
				setThreshold(BlockBR.tierValues.get(rs.getString("tier")) * (BlockBR.multValues.get(rs.getString("tier"))*(Integer.parseInt(rs.getString("level"))-1)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setTier(String tier) {
		this.tier = tier;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
}
