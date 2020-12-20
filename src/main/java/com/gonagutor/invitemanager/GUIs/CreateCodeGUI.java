package com.gonagutor.invitemanager.GUIs;

import java.util.Arrays;

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

public class CreateCodeGUI implements Listener {
	private final Inventory inv;
	private final Plugin pl;
	private final String increaseUsesIName = "§aIncrementar Usos";
	private final String decreaseUsesIName = "§cDisminuir Usos";
	private final String increaseTimeIName = "§aIncrementar Tiempo";
	private final String decreaseTimeIName = "§cDisminuir Tiempo";

	public CreateCodeGUI(Plugin pl) {
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
			inv.setItem(i, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, 1, " "));
		}
		inv.setItem(22, createGuiItem(Material.RED_CONCRETE, 1, "§c§lVolver"));
	}

	// Nice little method to create a gui item with a custom name, and description
	protected ItemStack createGuiItem(final Material material, int count, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, count);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	// You can open the inventory with this
	public void openInventory(final HumanEntity ent) {
		initializeItems();
		inv.setItem(4,
				createGuiItem(Material.NAME_TAG, 1,
						Gradienter.gradientedString("Crear Código",
								pl.getConfig().getString("menuGradients.secondItemTop"),
								pl.getConfig().getString("menuGradients.secondItemBottom"), "§l")));
		inv.setItem(12, createGuiItem(Material.GREEN_DYE, 1, increaseUsesIName, "§7Click para aumentar"));
		inv.setItem(11, createGuiItem(Material.POPPY, 1, "§e§lUsos de este Código", "§7Haz click en la izquierda",
				"§7para §aaumentar§7 los usos", "§7que tiene el codigo, y en", "§7el derecho para §cdisminuirlos"));
		inv.setItem(10, createGuiItem(Material.RED_DYE, 1, decreaseUsesIName, "§7Click para disminuir"));
		inv.setItem(16, createGuiItem(Material.GREEN_DYE, 1, increaseTimeIName, "§7Click para aumentar"));
		inv.setItem(15, createGuiItem(Material.CLOCK, 1, "§e§lDuración de este Código", "§7Haz click en la izquierda",
				"§7para §aaumentar§7 lo que", "§7dura el codigo, y en", "§7el derecho para §cdisminuirlo"));
		inv.setItem(14, createGuiItem(Material.RED_DYE, 1, decreaseTimeIName, "§7Click para disminuir"));
		ent.openInventory(inv);
	}

	// Check for clicks on items
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.getInventory() != inv)
			return;
		if (e.getClick() == ClickType.DOUBLE_CLICK) // Esto arregla el bug de aumentar dos veces
			return;
		e.setCancelled(true);
		final ItemStack clickedItem = e.getCurrentItem();
		ItemStack maxPlayersI = e.getInventory().getItem(11);
		ItemStack expiryDateI = e.getInventory().getItem(15);
		if (clickedItem == null || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE
				|| clickedItem.getType() == Material.AIR)
			return;

		final Player p = (Player) e.getWhoClicked();
		if (clickedItem.getType() == Material.NAME_TAG) {
			if (InviteManager.plf.getConfig().getStringList("players." + p.getUniqueId() + ".codes").size() < 7) {
				InviteManager.plf.createCode(maxPlayersI.getAmount(), expiryDateI.getAmount(), p);
				CodesGUI cgui = new CodesGUI(pl);
				cgui.openInventory(p);
			} else
				p.sendMessage(InviteManager.pluginPrefix
						+ "§cTodavía tienes codigos activos. No puedes tener mas de 7 codigos activos.");
		}
		if (clickedItem.getType() == Material.GREEN_DYE) {
			if (clickedItem.getItemMeta().getDisplayName().equals(increaseUsesIName)) {
				if (maxPlayersI.getAmount() + 1 <= 5) {
					maxPlayersI.setAmount(maxPlayersI.getAmount() + 1);
				} else {
					p.sendMessage(InviteManager.pluginPrefix + "§cEl limite de usos es 5");
				}
			}
			if (clickedItem.getItemMeta().getDisplayName().equals(increaseTimeIName)) {
				if (expiryDateI.getAmount() + 1 <= 48) {
					expiryDateI.setAmount(expiryDateI.getAmount() + 1);
				} else {
					p.sendMessage(InviteManager.pluginPrefix + "§cLa duración máxima son 48 horas");
				}
			}
		}
		if (clickedItem.getType() == Material.RED_DYE) {
			if (clickedItem.getItemMeta().getDisplayName().equals(decreaseUsesIName)) {
				if (maxPlayersI.getAmount() - 1 >= 1) {
					maxPlayersI.setAmount(maxPlayersI.getAmount() - 1);
				} else {
					p.sendMessage(InviteManager.pluginPrefix + "§cNo puedes crear un codigo con 0 usos");
				}
			}
			if (clickedItem.getItemMeta().getDisplayName().equals(decreaseTimeIName)) {
				if (expiryDateI.getAmount() - 1 >= 1) {
					expiryDateI.setAmount(expiryDateI.getAmount() - 1);
				} else {
					p.sendMessage(InviteManager.pluginPrefix + "§cLa duración minima es 1 hora");
				}
			}
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
