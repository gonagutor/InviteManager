package com.gonagutor.invitemanager.GUIs;

import org.bukkit.plugin.Plugin;

public class GUILoader {
	public static InviteGUI inviteGUI;
	public static HelperGUI helpergui;
	public static CodesGUI codesGUI;
	public static CreateCodeGUI createCodeGUI;

	public static void loadGuis(Plugin pl) {
		inviteGUI = new InviteGUI(pl);
		codesGUI = new CodesGUI(pl);
		createCodeGUI = new CreateCodeGUI(pl);
		helpergui = new HelperGUI();
		registerEvents(pl);
	}

	public static void registerEvents(Plugin pl) {
		pl.getServer().getPluginManager().registerEvents(inviteGUI, pl);
		pl.getServer().getPluginManager().registerEvents(helpergui, pl);
		pl.getServer().getPluginManager().registerEvents(codesGUI, pl);
		pl.getServer().getPluginManager().registerEvents(createCodeGUI, pl);
	}
}
