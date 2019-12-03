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

public abstract class AbstractGui implements Listener, InventoryHolder{
	
	public final Inventory inv;
	Material materialList[] = {Material.GRASS_BLOCK,Material.OAK_LOG,Material.STONE,Material.COAL_ORE,Material.REDSTONE_ORE,Material.LAPIS_ORE,Material.IRON_ORE,Material.GOLD_ORE,Material.OBSIDIAN,Material.DIAMOND_ORE,Material.EMERALD_ORE};
	Material matTier;
	public Main plugin;
	
	public AbstractGui(Main plugin, String guiTitle, int numSlots) {
		inv = Bukkit.createInventory(this, numSlots, Chat.blockBrChat(guiTitle));
		//Bukkit.getPluginManager().registerEvents(this,plugin);
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public abstract void initializeItems(String tier, int amount, int level, double threshold);
	
	public abstract void openInventory(Player p);
	
	@EventHandler
    public abstract void onInventoryClick(InventoryClickEvent e);

}
