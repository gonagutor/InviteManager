package com.gonagutor.invitemanager.Commands;

import com.gonagutor.invitemanager.InviteManager;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VengodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        vengode(sender, command, label, args);
        return true;
    }

    public void vengode(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            if (args.length < 1) {
                sender.sendMessage(InviteManager.pluginPrefix + "§cNo se usa así! Debes especificar el codigo con §a/vengode [CODIGO]");
                return;
            }
            Player player = (Player) sender;
            if (InviteManager.plf.addPlayerToFile(player, args[0])){
                player.sendMessage(InviteManager.pluginPrefix + "§aEl codigo es correcto. Bienvenido!");
                InviteManager.getPlugin(InviteManager.class).getServer().broadcastMessage(InviteManager.pluginPrefix + "§a§lDadle la bienvenida a " + player.getName());
                player.setGameMode(GameMode.SURVIVAL);
                player.setWalkSpeed(0.2f);
                player.setSleepingIgnored(false);
                player.performCommand("spawn");
                return;
            }
            return;
        }
        return;
    }
}