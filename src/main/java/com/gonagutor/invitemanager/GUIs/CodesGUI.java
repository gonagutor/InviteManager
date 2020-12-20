package com.gonagutor.invitemanager.GUIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gonagutor.invitemanager.InviteManager;
import com.gonagutor.invitemanager.Helpers.Gradienter;

import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import net.md_5.bungee.api.ChatColor;

public class CodesGUI implements Listener {
	private final Inventory inv;
	private final Plugin pl;

	public CodesGUI(Plugin pl) {
		this.pl = pl;
		pl.getServer().getPluginManager().registerEvents(this, pl);
		String gradientTop = pl.getConfig().getString("menuGradients.nameTop");
		String gradientBottom = pl.getConfig().getString("menuGradients.nameBottom");
		inv = Bukkit.createInventory(null, 27,
				Gradienter.gradientedString("Invitaciones", gradientTop, gradientBottom, "§l"));
		initializeItems();
	}

	public void initializeItems() {
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " "));
		}
		inv.setItem(22, createGuiItem(Material.RED_CONCRETE, "§c§lVolver"));
	}

	// Nice little method to create a gui item with a custom name, and description
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	private ItemStack createCodeItem(String code) {
		final int usos = InviteManager.plf.getConfig().getInt("codes." + code + ".invitesLeft");
		final String colorTop = pl.getConfig().getString("menuGradients.secondItemTop");
		final String colorBottom = pl.getConfig().getString("menuGradients.secondItemBottom");
		final ItemStack item = new ItemStack(Material.NAME_TAG, usos);
		final ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();

		if (!InviteManager.plf.getConfig().getBoolean("codes." + code + ".expires"))
			lore.add("§7§oEste codigo no expira");
		else
			lore.add("§eExpira en: §6" + InviteManager.plf.getConfig().getString("codes." + code + ".expiryDate"));
		if (InviteManager.plf.getConfig().getBoolean("codes." + code + ".unlimited"))
			lore.add("§eUsos restantes: §6Ilimitados");
		else
			lore.add("§eUsos restantes: §6" + InviteManager.plf.getConfig().getInt("codes." + code + ".invitesLeft"));
		lore.add("§7Haz click derecho para");
		lore.add("§c§leliminarla");
		meta.setDisplayName(Gradienter.gradientedString(code, colorTop, colorBottom, "§l"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	// You can open the inventory with this
	public void openInventory(final HumanEntity ent) {
		List<String> playerCodes = InviteManager.plf.getConfig()
				.getStringList("players." + ent.getUniqueId() + ".codes");
		initializeItems();
		int i = 10;
		for (String code : playerCodes) {
			if (i < 17) {
				if (!InviteManager.plf.codeIsValid(code)) {
					InviteManager.plf.deleteCode(code);
					continue;
				}
				inv.setItem(i, createCodeItem(code));
				i++;
			} else
				break;
		}
		ent.openInventory(inv);
	}

	// Check for clicks on items
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.getInventory() != inv)
			return;
		if (e.getClick() == ClickType.DOUBLE_CLICK)
			return;
		e.setCancelled(true);
		final ItemStack clickedItem = e.getCurrentItem();
		if (clickedItem == null || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE)
			return;

		final Player p = (Player) e.getWhoClicked();
		if (clickedItem.getType() == Material.NAME_TAG && e.getClick() == ClickType.RIGHT) {
			String codigoClickeado = ChatColor
					.stripColor(inv.getContents()[e.getRawSlot()].getItemMeta().getDisplayName());
			InviteManager.plf.deleteCode(codigoClickeado);
			p.sendMessage(InviteManager.pluginPrefix + "§7El codigo "
					+ inv.getContents()[e.getRawSlot()].getItemMeta().getDisplayName() + "§7 se ha borrado");
			openInventory(p);
		}
		if (clickedItem.getType() == Material.RED_CONCRETE) {
			InviteGUI igui = new InviteGUI(pl);
			igui.openInventory(p);
		}
	}

	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory() == inv) {
			e.setCancelled(true);
		}
	}
}