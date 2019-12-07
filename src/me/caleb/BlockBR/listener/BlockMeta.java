package me.caleb.BlockBR.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ProtectionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.caleb.BlockBR.BlockBR;
import me.caleb.BlockBR.Main;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class BlockMeta implements Listener{

	private Main plugin;
	boolean isNatural;
	
	
	public BlockMeta(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}
	/*
	 * When the player places a block,
	 * the meta data of "PLACED" should be "true"
	 * 
	 */
	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		// Makes a new metadata value called placed 
		block.setMetadata("PLACED", new FixedMetadataValue(plugin,true));
	}
	
	


	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		WorldGuardPlugin w = plugin.getWorldGuard();
		
		//RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		//ApplicableRegionSet set = container.createQuery().getApplicableRegions(BukkitAdapter.adapt(block.getLocation()));
		
		boolean q = w.createProtectionQuery().testBlockBreak(event, block);
		
		if(q) {
			player.sendMessage("You have broken a block! while outside of a wg region");
			try {
				String noBuildReason = GriefPrevention.instance.allowBreak(player, block, block.getLocation(), event);
				//null = they can build
				if(!noBuildReason.equals(null)) return;
					
			}catch(NullPointerException e) {
				
				if(block.hasMetadata("PLACED") || block.hasMetadata("FORMED")) {
					//If it's not natural
					BlockBR.insertPlayer(player,block);
					BlockBR.ifNatural(false);
				}else {
					//If it is natural
					BlockBR.insertPlayer(player,block);
					BlockBR.ifNatural(true);
				}
				
			}
		}
		
		/*
		LocalPlayer p = w.wrapPlayer(player);
		//Checks to see if it's in a worldguard region
		try{
			if(set.getRegions().isEmpty()) {
				player.sendMessage("You are not in a region");
			}else {
				
				player.sendMessage("You are in a region");
			}
		}catch(NullPointerException e) {
			player.sendMessage("You are not in a region");
		}*/
		
		//Checks to see if it's in a GriefPrevention region
		
		
		
		
		
		

	}
	
	/*
	 * Whenever you try to form cobblestone (cobblestone generator),
	 * it fires this event
	 * 
	 * Same thing for obsidian
	 * 
	 * MetaData of "FORMED" is set to true
	 */
	@EventHandler
	private void onBlockForm(BlockFormEvent event) {
		
		//Bukkit.getConsoleSender().sendMessage("A block has been formed!");
		Block block = event.getBlock();
		// Makes a new metadata value called formed
		block.setMetadata("FORMED", new FixedMetadataValue(plugin,true));
	}
}
