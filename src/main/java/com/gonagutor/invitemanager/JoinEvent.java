package com.gonagutor.invitemanager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (!InviteManager.plf.getConfig().contains("players."+ player.getUniqueId() + ".invitedBy"))
        {
            event.setJoinMessage(" ");
            player.setGameMode(GameMode.ADVENTURE);;
            player.setWalkSpeed(0.0f);
            player.setSleepingIgnored(true);
            player.sendMessage(InviteManager.pluginPrefix + "§eBienvenido al server!");
            player.sendMessage(InviteManager.pluginPrefix + "§ePara poder continuar introduce el codigo de invitación");
            player.sendMessage(InviteManager.pluginPrefix + "§eUsa /vengode [CODIGO] para continuar");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent ePlayerMoveEvent)
    {
        if (!InviteManager.plf.getConfig().contains("players."+ ePlayerMoveEvent.getPlayer().getUniqueId() + ".invitedBy"))
            ePlayerMoveEvent.getPlayer().teleport(ePlayerMoveEvent.getFrom());
    }
}