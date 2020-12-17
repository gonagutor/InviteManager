package com.gonagutor.invitemanager;

import com.gonagutor.invitemanager.Commands.*;
import com.gonagutor.invitemanager.Files.PlayerLogFile;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class InviteManager extends JavaPlugin {
    public static String pluginPrefix = "§6§lInvitaciones §e>§6> ";
    public static PlayerLogFile plf;

    @Override
    public void onEnable() {
        plf = new PlayerLogFile(this);
        this.getCommand("vengode").setExecutor(new VengodeCommand());
        this.getCommand("invitar").setExecutor(new InvitarCommand());
        this.getCommand("im").setExecutor(new ImCommand());
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
