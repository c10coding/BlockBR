package me.caleb.BlockBR.utils.crate;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.CratePlugin;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.crate.reward.RewardLine;
import com.hazebyte.crate.api.util.ItemBuilder;

import me.caleb.BlockBR.Main;
import me.caleb.BlockBR.utils.Chat;

public class CrateDealer {
	
	private Main plugin;
	private static CratePlugin api = CrateAPI.getInstance();
	
	public CrateDealer(Main plugin) {
		this.plugin = plugin;
	}
	
	public void addItems(String crateName, org.bukkit.Material material, int amount, Player p) {
		
		ItemBuilder i = new ItemBuilder(material);
		i.amount(amount);
		
		Crate c = api.getCrateRegistrar().getCrate(crateName);
		
		
		Reward r = api.getCrateRegistrar().createReward();
		
		r.addItem(i.asItemStack());
		r.setSlot(0);
		
		Bukkit.broadcastMessage(r.toString());
		c.addReward(r);
		
		p.sendMessage(Chat.blockBrChat("The item has been added to the crate!"));
		
	}
	
	public void makeCrate(String name, Player p) {
			
		CrateType type = CrateType.KEY;
		com.hazebyte.crate.api.crate.Crate c = api.getCrateRegistrar().createCrate(name, type);
		c.setDisplayName(name);
		api.getCrateRegistrar().add(c);
		c.serialize();
	}
}
