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

public class Gui implements Listener, InventoryHolder{
	
	private final Inventory inv;
	Material materialList[] = {Material.GRASS_BLOCK,Material.OAK_LOG,Material.STONE,Material.COAL_ORE,Material.REDSTONE_ORE,Material.LAPIS_ORE,Material.IRON_ORE,Material.GOLD_ORE,Material.OBSIDIAN,Material.DIAMOND_ORE,Material.EMERALD_ORE};
	Material matTier;
	
	public Gui() {
		inv = Bukkit.createInventory(this, 9, Chat.blockBrChat("Info"));
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	private ItemStack createGuiItem(Material material, String name, String...lore) {
		
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
	
	public void setMatTier(String tier) {
		
		if(tier.equalsIgnoreCase("grass")) {
			matTier = materialList[0];
		}else if(tier.equalsIgnoreCase("log")) {
			matTier = materialList[1];
		}else if(tier.equalsIgnoreCase("stone")) {
			matTier = materialList[2];
		}else if(tier.equalsIgnoreCase("coal")) {
			matTier = materialList[3];
		}else if(tier.equalsIgnoreCase("redstone")) {
			matTier = materialList[4];
		}else if(tier.equalsIgnoreCase("lapis")) {
			matTier = materialList[5];
		}else if(tier.equalsIgnoreCase("iron")) {
			matTier = materialList[6];
		}else if(tier.equalsIgnoreCase("gold")) {
			matTier = materialList[7];
		}else if(tier.equalsIgnoreCase("obsidian")) {
			matTier = materialList[8];
		}else if(tier.equalsIgnoreCase("diamond")) {
			matTier = materialList[9];
		}else if(tier.equalsIgnoreCase("emerald")) {
			matTier = materialList[10];
		}
		
	}
	
	public void initializeItems(String tier, int amount, int level, double threshold) {
		
		setMatTier(tier);
		
        inv.addItem(createGuiItem(matTier, Chat.chat("&lCurrent Tier"), "Your current tier is " + Chat.chat("&6" + tier.toUpperCase()), "The amount you must reach to get to the next tier is " + (int) threshold));
        inv.addItem(createGuiItem(Material.IRON_PICKAXE, "Amount mined on current tier", Chat.chat("&lAmount: " + amount)));
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

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Using slots click is a best option for your inventory click's
        if (e.getRawSlot() == 10) p.sendMessage("You clicked at slot " + 10);
    }

}
