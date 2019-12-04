package me.caleb.BlockBR.utils.tierRewards;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;

import me.caleb.BlockBR.Main;
import me.caleb.BlockBR.utils.TierMenu;

public class Coal extends TierMenu implements Listener, InventoryHolder{
	
	public Coal(Main plugin, String guiTitle) {
		super(plugin, guiTitle);
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}

	@Override
	public void initializeItems(String tier, int amount, int level, double threshold) {};

}
