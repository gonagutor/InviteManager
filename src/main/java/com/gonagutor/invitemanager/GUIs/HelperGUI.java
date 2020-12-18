package com.gonagutor.invitemanager.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class HelperGUI implements Listener {
	private final Inventory inv;

	public HelperGUI() {
		// Create a new inventory, with no owner (as this isn't a real inventory), a
		// size of nine, called example
		inv = Bukkit.createInventory(null, 54, "Helper gui");
		// Put the items into the inventory
		initializeItems();
	}

	// You can call this whenever you want to put the items in
	public void initializeItems() {
		for (int i = 1; i < 54; i++)
			inv.setItem(i, createGuiItem(Material.CHEST, i, "Slot número " + i));
	}

	// Nice little method to create a gui item with a custom name, and description
	protected ItemStack createGuiItem(final Material material, int amount, final String name) {
		final ItemStack item = new ItemStack(material, amount);
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		return item;
	}

	public void openInventory(final HumanEntity ent) {
		ent.openInventory(inv);
	}

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.getInventory() == inv)
			e.setCancelled(true);
	}

	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory() == inv)
			e.setCancelled(true);
	}
}