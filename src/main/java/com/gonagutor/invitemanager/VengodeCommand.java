package com.gonagutor.invitemanager;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class VengodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(InviteManager.pluginPrefix + "§aEl codigo es correcto. Bienvenido!");
            player.setGameMode(GameMode.SURVIVAL);
            player.setSleepingIgnored(false);
            player.setWalkSpeed(0.0f);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.performCommand("spawn");
            return true;
        }
        sender.sendMessage(InviteManager.pluginPrefix + "§cTio, pero que coño haces, que es tu plugin colega");
        return true;
    }
}
