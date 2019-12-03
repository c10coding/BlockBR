package me.caleb.BlockBR.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.caleb.BlockBR.BlockBR;
import me.caleb.BlockBR.Main;

public class RewardsGui extends AbstractGui implements Listener, InventoryHolder{
	
	public RewardsGui(Main plugin,String title, int numSlots) {
		super(plugin, title, numSlots);
	}

	@Override
	public void initializeItems(String tier, int amount, int level, double threshold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void openInventory(Player p) {
		p.openInventory(inv);
	}
	

}
