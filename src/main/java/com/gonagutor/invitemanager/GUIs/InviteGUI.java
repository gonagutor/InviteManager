package com.gonagutor.invitemanager.GUIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gonagutor.invitemanager.InviteManager;
import com.gonagutor.invitemanager.Helpers.Gradienter;

import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InviteGUI implements Listener {
	private final Inventory inv;
	private final Plugin pl;

	public InviteGUI(Plugin pl) {
		// Create a new inventory, with no owner (as this isn't a real inventory), a
		// size of nine, called example
		this.pl = pl;
		String gradientTop = pl.getConfig().getString("menuGradients.nameTop");
		String gradientBottom = pl.getConfig().getString("menuGradients.nameBottom");
		inv = Bukkit.createInventory(null, 27,
				Gradienter.gradientedString("Invitaciones", gradientTop, gradientBottom, "§l"));
		// Put the items into the inventory
		initializeItems();
	}

	// You can call this whenever you want to put the items in
	public void initializeItems() {
		String firstItemTop = pl.getConfig().getString("menuGradients.firstItemTop");
		String firstItemBottom = pl.getConfig().getString("menuGradients.firstItemBottom");
		String secondItemTop = pl.getConfig().getString("menuGradients.secondItemTop");
		String secondtemBottom = pl.getConfig().getString("menuGradients.secondItemBottom");
		String thirdItemTop = pl.getConfig().getString("menuGradients.thirdItemTop");
		String thirdItemBottom = pl.getConfig().getString("menuGradients.thirdItemBottom");
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " "));
		}
		inv.setItem(10,
				createGuiItem(Material.POPPY,
						Gradienter.gradientedString("Crear una nueva Invitación", firstItemTop, firstItemBottom, "§l"),
						"§eHaz click para crear un", "§enuevo código de invitación"));
		inv.setItem(13,
				createGuiItem(Material.NAME_TAG,
						Gradienter.gradientedString("Códigos disponibles", secondItemTop, secondtemBottom, "§l"),
						"§eHaz click para ver los ", "§ecodigos que tienes", "§edisponibles"));
		inv.setItem(16,
				createGuiItem(Material.PLAYER_HEAD,
						Gradienter.gradientedString("Tu Info:", thirdItemTop, thirdItemBottom, "§l"),
						"§eA ti te invitó: §6%player%"));
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

	private ItemStack createSkullItem(Player player, String name, List<String> lore) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer((OfflinePlayer) player.getPlayer());
		meta.setDisplayName(name);
		meta.setLore(lore);
		skull.setItemMeta(meta);
		return skull;
	}

	// You can open the inventory with this
	public void openInventory(final HumanEntity ent) {
		String thirdItemTop = pl.getConfig().getString("menuGradients.thirdItemTop");
		String thirdItemBottom = pl.getConfig().getString("menuGradients.thirdItemBottom");
		List<String> invitedPlayer = InviteManager.plf.getConfig()
				.getStringList("player." + ent.getUniqueId() + ".invitedPlayers");
		List<String> lore = new ArrayList<String>();
		lore.add("§eA ti te invitó: §6" + ent.getName());
		lore.add("§eTu has invitado a: ");
		int i = 0;
		if (invitedPlayer.size() > 0)
			for (String string : invitedPlayer) {
				if (i < 10)
					lore.add(" §7- §6" + Bukkit.getPlayer(string));
				else {
					lore.add("§6...");
					break;
				}
				i++;
			}
		else
			lore.add(" §7- §6Todavía no has invitado a nadie");
		inv.setItem(16, createSkullItem((Player) ent,
				Gradienter.gradientedString("Tu Info:", thirdItemTop, thirdItemBottom, "§l"), lore));
		ent.openInventory(inv);
	}

	// Check for clicks on items
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.getInventory() != inv)
			return;
		e.setCancelled(true);
		final ItemStack clickedItem = e.getCurrentItem();
		if (clickedItem == null || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE)
			return;

		final Player p = (Player) e.getWhoClicked();
		if (e.getRawSlot() == 10)
			p.sendMessage("Intento de crear una invitación");
		if (e.getRawSlot() == 13)
			GUILoader.codesGUI.openInventory(p);
		// if (e.getRawSlot() == 16)
		// p.sendMessage("Intento de ver los jugadores invitados");
	}

	// Cancel dragging in our inventory
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent e) {
		if (e.getInventory() == inv) {
			e.setCancelled(true);
		}
	}
}