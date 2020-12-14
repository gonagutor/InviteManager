package com.gonagutor.invitemanager.Commands;

import org.bukkit.command.CommandExecutor;

import com.gonagutor.invitemanager.InviteManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
public class InvitarCommand implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length < 1)
        {
            sender.sendMessage(InviteManager.pluginPrefix + "§cNo se usa así! Los usos (-1 para infinitos) especificarse §a/invitar [USOS]");
            return true;
        }
		InviteManager.plf.createCode(Integer.parseInt(args[0]), 1, sender);
        return true;
    }
}
