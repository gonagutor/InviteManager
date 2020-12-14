package com.gonagutor.invitemanager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviteManager extends JavaPlugin {
    public static String pluginPrefix ="§6§lInvitaciones §e>§6> ";
    @Override
    public void onEnable() {
        getLogger().info(pluginPrefix + "§aEl plugin se ha cargado correctamente");
        this.getCommand("vengode").setExecutor(new VengodeCommand());
        registerJoinEvent();
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginPrefix + "§aEl plugin se ha desactivado correctamente");
    }

    public void registerJoinEvent()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
    }
}
