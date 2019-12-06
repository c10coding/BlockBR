package me.caleb.BlockBR.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.CratePlugin;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateRegistrar;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.crate.reward.RewardLine;

import me.caleb.BlockBR.Main;

public class Rewards {
	
	private Main plugin;
	private static CratePlugin api = CrateAPI.getInstance();
	
	public Rewards (Main plugin) {
		this.plugin = plugin;
	}
	
	//Gives the player a key based on the tier and level
	public static void giveKey(Player p,String tier,int level) {
		api.getCrateRegistrar().getCrate(tier+level+"Chest").giveTo(p, 1);	
	}
	
	//Gets all the rewards for this tier
	public ArrayList<String> getAllRewardsNames(String tier, int level) {
		
		int size = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewardSize();
		ArrayList<String> rewardNames = new ArrayList<String>();
		
		String name = "";
		
		for(int x = 0; x < size;x++) {
			
			// Fixes the error to where if there was an enchanted item with a custom name it didn't show the correct display name. Now it does
			ItemMeta meta = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().get(x).getDisplayItem().getItemMeta();
			
			if(meta.getEnchants().isEmpty()) {
				//If the item has no enchants, get the type name, make the first letter uppecase and the rest lowercase
				//Then replace the underscores with spaces
				name = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().get(x).getDisplayItem().getType().name();
			}else {
				//If the item has enchants
				name = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().get(x).getDisplayItem().getItemMeta().getDisplayName();
			}
			
			rewardNames.add(name);
		}
		
		
		return rewardNames;
		
	}
	
	// I need to do an array list inside an array list. Since an item can have multiple enchantments, i a 2d arraylist
	/*
	 * [
	 * 	[Enchantment 1,Enchantment 2]
	 * 	[Enchantment 1, Enchantment 2]
	 * ]
	 * 
	 * 
	 * 
	 */
	
	public ArrayList<ArrayList<Enchantment>> getAllRewardsEnchants(String tier, int level){
		
		int size = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewardSize();
		
		ArrayList<ArrayList<Enchantment>> allRewardsEnchants =  new ArrayList<ArrayList<Enchantment>>(); 
		
		ArrayList<Enchantment> itemEnchants = new ArrayList<Enchantment>(); 
		
		Crate crate = api.getCrateRegistrar().getCrate(tier+level+"Chest");
		
		for(int x= 0; x < size ; x++) {
			
			Map<Enchantment,Integer> crateEnchants;
			
			ItemStack item = crate.getRewards().get(x).getDisplayItem();
			
			if(item.getItemMeta() instanceof EnchantmentStorageMeta) {
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
				crateEnchants = meta.getStoredEnchants();
			}else {
				crateEnchants = crate.getRewards().get(x).getDisplayItem().getEnchantments();;
			}
			
			for(Entry<Enchantment, Integer> en : crateEnchants.entrySet()){
				itemEnchants.add(en.getKey());
			}
			
			allRewardsEnchants.add(itemEnchants);
			itemEnchants = new ArrayList<Enchantment>();
			
			//Bukkit.broadcastMessage(allRewardsEnchants.toString());
		}
		
		return allRewardsEnchants;
		
	}
	
	public ArrayList<ArrayList<Integer>> getAllRewardsEnchantmentLevels(String tier, int level){
		
		int size = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewardSize();
		
		ArrayList<ArrayList<Integer>> allRewardsLevels =  new ArrayList<ArrayList<Integer>>(); 
		
		ArrayList<Integer> itemLevels = new ArrayList<Integer>(); 
		
		Crate crate = api.getCrateRegistrar().getCrate(tier+level+"Chest");
		
		for(int x= 0; x < size ; x++) {
			
			Map<Enchantment,Integer> crateEnchants;
			
			ItemStack item = crate.getRewards().get(x).getDisplayItem();
			
			if(item.getItemMeta() instanceof EnchantmentStorageMeta) {
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
				crateEnchants = meta.getStoredEnchants();
			}else {
				crateEnchants = crate.getRewards().get(x).getDisplayItem().getEnchantments();
			}
			
			for(Entry<Enchantment, Integer> en : crateEnchants.entrySet()){
				itemLevels.add(en.getValue());
			}
			
			allRewardsLevels.add(itemLevels);
			itemLevels = new ArrayList<Integer>();
			
			//Bukkit.broadcastMessage(allRewardsLevels.toString());
		}
		
		return allRewardsLevels;
		
	}
	
	public ArrayList<Material> getAllRewardsMaterials(String tier, int level) {
		
		int size = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewardSize();
		ArrayList<Material> rewardMaterials = new ArrayList<Material>();
		
		for(int x = 0; x < size; x++) {
			Material mat = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().get(x).getDisplayItem().getType();
			rewardMaterials.add(mat);
		}
		
		
		return rewardMaterials;
		
	}
	
	public ArrayList<Integer> getAllRewardsAmounts(String tier, int level){
		
		int size = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewardSize();
		ArrayList<Integer> rewardsAmounts = new ArrayList<Integer>();	
		
		for(int x = 0; x < size; x++) {
			int amount = api.getCrateRegistrar().getCrate(tier+level+"Chest").getRewards().get(x).getDisplayItem().getAmount();
			rewardsAmounts.add(amount);
		}
		return rewardsAmounts;
		
		
	}
	
	// Gives the player money based on their tier and level
	public void giveMoney(Player p,String tier,int level) {
		
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
