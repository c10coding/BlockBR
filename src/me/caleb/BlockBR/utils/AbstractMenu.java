package me.caleb.BlockBR.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.caleb.BlockBR.BlockBR;
import me.caleb.BlockBR.Main;

public abstract class AbstractMenu implements Listener, InventoryHolder{
	
	public final Inventory inv;
	Material materialList[] = {Material.GRASS_BLOCK,Material.OAK_LOG,Material.STONE,Material.COAL_ORE,Material.REDSTONE_ORE,Material.LAPIS_ORE,Material.IRON_ORE,Material.GOLD_ORE,Material.OBSIDIAN,Material.DIAMOND_ORE,Material.EMERALD_ORE};
	public Main plugin;
	//Initialized so that every gui that extends this can access the gi variables
	public GetInfo gi;
	
	public AbstractMenu(Main plugin, String guiTitle, int numSlots, boolean blockbrchat) {
		
		if(blockbrchat == true) {
			inv = Bukkit.createInventory(this, numSlots, Chat.blockBrChat(guiTitle));
		}else {
			inv = Bukkit.createInventory(this, numSlots, guiTitle);
		}
		
		this.plugin = plugin;
		
		gi = new GetInfo(plugin);

	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public static ItemStack createGuiItem(Material material, String name, int amount, String...lore) {
		
		ItemStack item = new ItemStack(material,1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setAmount(amount);
		ArrayList<String> metaLore = new ArrayList<String>();
		
		for(String lorecomments : lore) {
			
			metaLore.add(lorecomments);
			
		}
		
		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
		
	}
	// If you want more than one item
	public static ItemStack createGuiItem(Material material, String name, String...lore) {
		
		ItemStack item = new ItemStack(material,1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		
		ArrayList<String> metaLore = new ArrayList<String>();
		
		for(String lorecomments : lore) {
			
			metaLore.add(lorecomments);
			
		}
		
		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createGuiItem() {
		
		ItemStack item = new ItemStack(Material.CYAN_STAINED_GLASS_PANE,1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("");
		meta.setLore(null);
		
		return item;
	}
	
	public abstract void initializeItems(String tier, int amount, int level, double threshold);
	
	public void openInventory(Player p) {
		p.openInventory(inv);
	}
	
	@EventHandler
    public abstract void onInventoryClick(InventoryClickEvent e);


}
