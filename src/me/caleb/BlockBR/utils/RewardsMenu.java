package me.caleb.BlockBR.utils;

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

import me.caleb.BlockBR.BlockBR;
import me.caleb.BlockBR.Main;
import me.caleb.BlockBR.utils.tierRewards.Coal;
import me.caleb.BlockBR.utils.tierRewards.Diamond;
import me.caleb.BlockBR.utils.tierRewards.Emerald;
import me.caleb.BlockBR.utils.tierRewards.Gold;
import me.caleb.BlockBR.utils.tierRewards.Grass;
import me.caleb.BlockBR.utils.tierRewards.Iron;
import me.caleb.BlockBR.utils.tierRewards.Lapis;
import me.caleb.BlockBR.utils.tierRewards.Log;
import me.caleb.BlockBR.utils.tierRewards.Obsidian;
import me.caleb.BlockBR.utils.tierRewards.Redstone;
import me.caleb.BlockBR.utils.tierRewards.Stone;

public class RewardsMenu extends AbstractMenu implements Listener, InventoryHolder{
	
	public static Player p;
	
	public RewardsMenu(Main plugin,String title, int numSlots, boolean blockbrchat) {
		super(plugin, title, numSlots, blockbrchat);
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}

	@Override
	public void initializeItems(String tier, int amount, int level, double threshold) {
		
		for(int x = 0;x < materialList.length;x++) {
			String currentTier = BlockBR.tierList[x];
			
			currentTier = currentTier.substring(0,1).toUpperCase() + currentTier.substring(1);
			inv.addItem(createGuiItem(materialList[x], Chat.chat("&l" + currentTier), Chat.chat("Click me to see the rewards for the &6&l" + currentTier + "&r &5tier")));
		}
		
		for(int i = 11; i < inv.getSize()-1;i++) {
			inv.setItem(i, createGuiItem());
		}
		
		inv.addItem(createGuiItem(Material.RED_WOOL,Chat.chat("&lGO BACK"),Chat.chat("Click this to go back to the previous page")));
		
		
	}

	
	public void openInventory(Player p) {
		p.openInventory(inv);
	}
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		
        if (e.getInventory().getHolder() != this) {
            return;
        }
        
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            e.setCancelled(true);
        }
        
        e.setCancelled(true);

        p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        
        //Gets the information of everything about the player in the database
        GetInfo i = new GetInfo(plugin);
    	i.getInfo(p);
        
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
       
        if (e.getRawSlot() == 0) {
        	
        	p.closeInventory();
        	
        	Grass grass = new Grass(plugin,"Grass Rewards");
        	
        	grass.initializeRewards("grass",i.level);
        	grass.openInventory(p);
        	
        }else if(e.getRawSlot() == 1){
        	
        	p.closeInventory();
        	
        	Log log = new Log(plugin, "Log Rewards");
        	
        	log.initializeRewards("log", i.level);
        	log.openInventory(p);
        	
        }else if(e.getRawSlot() == 2){
        	
        	p.closeInventory();
        	
        	Stone stone = new Stone(plugin, "Stone Rewards");
        	
        	stone.initializeRewards("stone", i.level);
        	stone.openInventory(p);
    	}else if(e.getRawSlot() == 3){
        	
        	p.closeInventory();
        	
        	Coal coal = new Coal(plugin, "Coal Rewards");
        	
        	coal.initializeRewards("coal", i.level);
        	coal.openInventory(p);	
		}else if(e.getRawSlot() == 4){
        	
        	p.closeInventory();
        	
        	Redstone redstone = new Redstone(plugin, "Redstone Rewards");
        	
        	redstone.initializeRewards("redstone", i.level);
        	redstone.openInventory(p);	
		}else if(e.getRawSlot() == 5){
        	
        	p.closeInventory();
        	
        	Lapis obj = new Lapis(plugin, "Lapis Rewards");
        	
        	obj.initializeRewards("lapis", i.level);
        	obj.openInventory(p);	
		}else if(e.getRawSlot() == 6){
        	
        	p.closeInventory();
        	
        	Iron obj = new Iron(plugin, "Iron Rewards");
        	
        	obj.initializeRewards("iron", i.level);
        	obj.openInventory(p);	
		}else if(e.getRawSlot() == 7){
        	
        	p.closeInventory();
        	
        	Gold obj = new Gold(plugin, "Gold Rewards");
        	
        	obj.initializeRewards("gold", i.level);
        	obj.openInventory(p);	
		}else if(e.getRawSlot() == 8){
        	
        	p.closeInventory();
        	
        	Obsidian obj = new Obsidian(plugin, "Obsidian Rewards");
        	
        	obj.initializeRewards("obsidian", i.level);
        	obj.openInventory(p);	
		}else if(e.getRawSlot() == 9){
        	
        	p.closeInventory();
        	
        	Diamond obj = new Diamond(plugin, "Diamond Rewards");
        	
        	obj.initializeRewards("diamond", i.level);
        	obj.openInventory(p);	
		}else if(e.getRawSlot() == 10){
        	
        	p.closeInventory();
        	
        	Emerald obj = new Emerald(plugin, "Emerald Rewards");
        	
        	obj.initializeRewards("emerald", i.level);
        	obj.openInventory(p);	
        	
        }else if(e.getRawSlot() == 17) {
        	
        	p.closeInventory();
        	
        	Menu m = new Menu(plugin, "Info",9,true);
        	gi.getInfo(p);
        	m.initializeItems(gi.tier, gi.amount, gi.level, gi.threshold);
        	m.openInventory(p);
        	
        }
    }
	

}
