package me.caleb.BlockBR.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import me.caleb.BlockBR.Main;

public class BlockMeta implements Listener{

	private Main plugin;
	
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
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		// Makes a new metadata value called placed 
		block.setMetadata("PLACED", new FixedMetadataValue(plugin,true));
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(block.hasMetadata("PLACED") || block.hasMetadata("FORMED")) {
			player.sendMessage("This block has been placed or formed!");
		}else {
			player.sendMessage("This block has not been placed or formed!");
		}
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
	public void onBlockForm(BlockFormEvent event) {
		Bukkit.getConsoleSender().sendMessage("A block has been formed!");
		Block block = event.getBlock();
		// Makes a new metadata value called formed
		block.setMetadata("FORMED", new FixedMetadataValue(plugin,true));
	}
}
