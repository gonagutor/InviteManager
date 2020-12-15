package com.gonagutor.invitemanager.Commands;

import org.bukkit.command.CommandExecutor;

import com.gonagutor.invitemanager.InviteManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ImCommand implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length < 1)
        {
			sender.sendMessage("§6-----------------§e<§6< " + InviteManager.pluginPrefix + "§6-----------------");
			sender.sendMessage("§6 /im setnverteleport - §eEstablece el punto de aparicion de los no verificados");
            sender.sendMessage("§6 /im setverteleport - §eEstablece el punto de aparicion de los verificados correctamente");
            return true;
        } else if (args[0].equalsIgnoreCase("setnvteleport")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                InviteManager.plf.getConfig().set("plugindata.welcomespawn", player.getLocation());
                InviteManager.plf.saveConfig();
                sender.sendMessage(InviteManager.pluginPrefix + "§eSpawn para los no verificados colocado en tu posición actual (X: " + player.getLocation().getX() + ", Y: " + player.getLocation().getY() + ", Z: " + player.getLocation().getZ() + ")");
            } else {
                sender.sendMessage(InviteManager.pluginPrefix + "§cEste comando no lo puede ejecutar la consola");
            }
        } else if (args[0].equalsIgnoreCase("setverteleport")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                InviteManager.plf.getConfig().set("plugindata.verifiedspawn", player.getLocation());
                InviteManager.plf.saveConfig();
                sender.sendMessage(InviteManager.pluginPrefix + "§eSpawn para los verificados correctamente colocado en tu posición actual (X: " + player.getLocation().getX() + ", Y: " + player.getLocation().getY() + ", Z: " + player.getLocation().getZ() + ")");
            } else {
                sender.sendMessage(InviteManager.pluginPrefix + "§cEste comando no lo puede ejecutar la consola");
            }
        }
        return true;
    }
}
