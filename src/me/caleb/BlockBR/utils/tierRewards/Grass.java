package me.caleb.BlockBR.utils.tierRewards;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.caleb.BlockBR.Main;
import me.caleb.BlockBR.utils.AbstractGui;
import me.caleb.BlockBR.utils.Chat;
import me.caleb.BlockBR.utils.GetInfo;
import me.caleb.BlockBR.utils.RewardsMenu;

public class Grass extends AbstractGui implements Listener, InventoryHolder{
	
	public Grass(Main plugin, String guiTitle, int numSlots,boolean blockbrchat) {
		super(plugin, guiTitle, numSlots, blockbrchat);
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}

	public void initializeItems(String tier, int amount, int level, double threshold, Player p) {
		
		gi.getInfo(p);
		
		try {
			for(int x = 0;x <= 35;x++) {
				
				if(x < gi.rewardAmounts.size()) {
					inv.addItem(createGuiItem(gi.rewardMats.get(x), Chat.chat("&l" + gi.rewardNames.get(x)),gi.rewardAmounts.get(x),Chat.chat("&6&lAmount: ") + Chat.chat("&r&l") + gi.rewardAmounts.get(x)));
				}else {
					//If the spot is null, then check if the spot is 28. If it's 28 then set it to red wool. If not, then set it to air
					if(inv.getItem(x) == null) {
						if(x == 27) {
							inv.addItem(createGuiItem(Material.RED_WOOL,Chat.chat("&lGO BACK"),Chat.chat("Click this to go back to the previous page")));
						}else {
							inv.setItem(x, createGuiItem());
						}
					}
					
				}
				
			}
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		
		
		
	}
	
	public void initializeItems(String tier, int amount, int level, double threshold) {};

	@Override
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

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
       
        if (e.getRawSlot() == 27) {
        	
        	p.closeInventory();
        	RewardsMenu rm = new RewardsMenu(plugin, "Rewards", 18, true);
        	gi.getInfo(p);
        	rm.initializeItems(gi.tier, gi.amount, gi.level, gi.threshold);
        	rm.openInventory(p);
        }
		
	}

}
