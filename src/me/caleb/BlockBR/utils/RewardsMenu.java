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
import me.caleb.BlockBR.utils.tierRewards.Grass;

public class RewardsMenu extends AbstractGui implements Listener, InventoryHolder{
	
	public static Player p;
	
	public RewardsMenu(Main plugin,String title, int numSlots, boolean blockbrchat) {
		super(plugin, title, numSlots,blockbrchat);
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
        
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
       
        if (e.getRawSlot() == 0) {
        	
        	p.closeInventory();
        	
        	Grass grass = new Grass(plugin,"Grass Rewards",36,false);
        	
        	GetInfo i = new GetInfo(plugin);
        	i.getInfo(p);
        	
        	grass.initializeItems(i.tier, i.amount, i.level, i.threshold, p);
        	grass.openInventory(p);
        	
        }else if(e.getRawSlot() == 17) {
        	p.closeInventory();
        	
        	Menu m = new Menu(plugin, "Info",9,true);
        	gi.getInfo(p);
        	m.initializeItems(gi.tier, gi.amount, gi.level, gi.threshold);
        	m.openInventory(p);
        	
        }
    }
	

}
