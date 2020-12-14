package com.gonagutor.invitemanager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.getFirstPlayed() == 0 || true)
        {
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 250));
            player.setWalkSpeed(-1.0f);
            player.setSleepingIgnored(true);
            player.sendMessage(InviteManager.pluginPrefix + "§eBienvenido al server!");
            player.sendMessage(InviteManager.pluginPrefix + "§ePara poder continuar introduce el codigo de invitación");
            player.sendMessage(InviteManager.pluginPrefix + "§eUsa /vengode [CODIGO] para continuar");
        }
    }
}
