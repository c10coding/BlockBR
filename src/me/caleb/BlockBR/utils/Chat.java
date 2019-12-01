package me.caleb.BlockBR.utils;

import org.bukkit.ChatColor;

public class Chat {
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	public static String blockBrChat(String s) {
		return chat("&l[&6BlockBR&r&l]&r " + s);
	}
}
