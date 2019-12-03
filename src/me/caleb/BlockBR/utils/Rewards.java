package me.caleb.BlockBR.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.CratePlugin;

import me.caleb.BlockBR.Main;

public class Rewards {
	
	private static Main plugin;
	private static CratePlugin api = CrateAPI.getInstance();
	
	public Rewards (Main plugin, Player p, String tier, int level) {
		this.plugin = plugin;
		final ArrayList<String> REWARD_NAMES = getAllRewardsNames(p,tier,level);
	}
	
	//Gives the player a key based on the tier and level
	public static void giveKey(Player p,String tier,int level) {
		api.getCrateRegistrar().getCrate(tier+level+"Chest").giveTo(p, 1);	
	}
	
	//Gets all the rewards for this tier
	public ArrayList<String> getAllRewardsNames(Player p, String tier, int level) {
		
		int size = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().size();
		ArrayList<String> rewardNames = new ArrayList<String>();
		
		for(int x = 0; x < size;x++) {
			String name = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().get(x).getDisplayItem().getType().name();
			rewardNames.add(name);
		}
		
		return rewardNames;
		
	}
	
	public static void getAllRewardsMaterials() {
		
	}
	
	public static void getAllRewardsAmounts(){
		
	}
	
	// Gives the player money based on their tier and level
	public static void giveMoney(Player p,String tier,int level) {
		
		int tierMoney = 0;
		//Makes the first letter uppercase and the rest of the word lowercase
		tier = tier.substring(0,1).toUpperCase() + tier.substring(1).toLowerCase();
		// If they are level 2, then tier that they're on has to increase for the money incentive
		if(level == 1) {
			tierMoney = plugin.getConfig().getInt(tier + "Money");
		}else {
			tierMoney = (int) ((int) plugin.getConfig().getInt(tier + "Money") * ((level-1) * plugin.getConfig().getDouble("MoneyMult")));
		}
		
		p.sendMessage(Chat.blockBrChat("You have been given a key that you can use at &c&lTarget&r (&6/warp shop&r) and &6&l$" + tierMoney + "!"));
		Main.getEconomy().depositPlayer(p,tierMoney);
		
	}
	
}
