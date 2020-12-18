package com.gonagutor.invitemanager.GUIs;

import org.bukkit.plugin.Plugin;

public class GUILoader {
	public static InviteGUI gui;
	public static HelperGUI helpergui;

	public static void loadGuis(Plugin pl) {
		gui = new InviteGUI(pl);
		helpergui = new HelperGUI();
		registerEvents(pl);
	}

	public static void registerEvents(Plugin pl) {
		pl.getServer().getPluginManager().registerEvents(gui, pl);
		pl.getServer().getPluginManager().registerEvents(helpergui, pl);
	}
}
