package com.gonagutor.invitemanager;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!InviteManager.plf.getConfig().contains("players." + player.getUniqueId() + ".invitedBy")) {
            Location welcomeSpawn = InviteManager.plf.getConfig().getLocation("plugindata.welcomespawn");
            if (welcomeSpawn != null)
                player.teleport(welcomeSpawn);
            player.setGameMode(GameMode.ADVENTURE);
            player.setSleepingIgnored(true);
            player.sendMessage(InviteManager.pluginPrefix + "§eBienvenido al server!");
            player.sendMessage(InviteManager.pluginPrefix + "§ePara poder continuar introduce el codigo de invitación");
            player.sendMessage(InviteManager.pluginPrefix + "§eUsa §a§l/vengode [CODIGO] §epara continuar");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent ePlayerMoveEvent) {
        if (!InviteManager.plf.getConfig()
                .contains("players." + ePlayerMoveEvent.getPlayer().getUniqueId() + ".invitedBy")) {
            Location to = ePlayerMoveEvent.getFrom();
            to.setPitch(ePlayerMoveEvent.getTo().getPitch());
            to.setYaw(ePlayerMoveEvent.getTo().getYaw());
            ePlayerMoveEvent.setTo(to);
        }
    }
}
