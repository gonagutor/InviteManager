package com.gonagutor.invitemanager;

import com.gonagutor.invitemanager.Commands.*;
import com.gonagutor.invitemanager.Files.PlayerLogFile;
import com.gonagutor.invitemanager.GUIs.GUILoader;
import com.gonagutor.invitemanager.Placeholders.IMPlaceholders;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviteManager extends JavaPlugin {
  public static String pluginPrefix = "§6§lInvitaciones §e>§6> ";
  public static PlayerLogFile plf;
  public static FileConfiguration config;

  @Override
  public void onEnable() {
    // Register Placeholders
    if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null)
      getLogger().warning("No se ha podido encontrar PlaceholderAPI. Los Placeholders no estarán disponibles");
    else
      new IMPlaceholders(this).register();
    // Load Config FIles
    plf = new PlayerLogFile(this);
    this.saveDefaultConfig();
    GUILoader.loadGuis(this);
    // Load Commands
    this.getCommand("vengode").setExecutor(new VengodeCommand());
    this.getCommand("invitar").setExecutor(new InvitarCommand());
    this.getCommand("im").setExecutor(new ImCommand(this));
    // Register Events
    registerJoinEvent();
    getLogger().info(pluginPrefix + "§aEl plugin se ha cargado correctamente");
  }

  @Override
  public void onDisable() {
    getLogger().info(pluginPrefix + "§aEl plugin se ha desactivado correctamente");
  }

  public void registerJoinEvent() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new JoinEvent(), this);
  }
}
