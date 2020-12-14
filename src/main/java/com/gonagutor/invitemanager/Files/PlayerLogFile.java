package com.gonagutor.invitemanager.Files;

import com.gonagutor.invitemanager.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerLogFile {
	private InviteManager plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;

	public PlayerLogFile(InviteManager plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}
	
	public void deleteCode(String code, String newUser)
	{
		String ownerPlayer = this.getConfig().getString("codes." + code + ".byPlayer");
		List<String> codes = this.getConfig().getStringList("player." + ownerPlayer + ".codes");
		List<String> invitedPlayers = this.getConfig().getStringList("player." + ownerPlayer + ".invitedPlayers");
		if (newUser != "")
			invitedPlayers.add(newUser);
		for (int i = 0; i < codes.size(); i++)
			if (code == codes.get(i))
				codes.remove(i);
		this.getConfig().set("players." + ownerPlayer + ".codes", codes);
		this.getConfig().set("players." + ownerPlayer + ".activeInvites", codes.size());
		this.getConfig().set("players." + ownerPlayer + ".invitedPlayers", invitedPlayers);
		this.getConfig().set("codes." + code, null);
		this.saveConfig();
	}

	public void createCode(int uses, int expires, CommandSender sender)
	{
		CodeGenerator session = new CodeGenerator(6);
		String code = session.nextString();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DATE, 1);
		String expiresIn = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(c.getTime());

		while (!this.getConfig().contains("codes." + code)) {
			if (sender instanceof ConsoleCommandSender) {
				List<String> codes = this.getConfig().getStringList("players.Console.codes");
				codes.add(code);
				List<String> invitedPlayers = this.getConfig().getStringList("players.Console.invitedPlayers");
				this.getConfig().set("codes." + code + ".byPlayer", "Console");
				this.getConfig().set("codes." + code + ".expires", (expires == 0) ? false : true);
				this.getConfig().set("codes." + code + ".unlimited", (uses == -1) ? true : false);
				this.getConfig().set("codes." + code + ".expiryDate", expiresIn);
				this.getConfig().set("codes." + code + ".invitesLeft", (uses == -1) ? 9999 : uses);
				this.getConfig().set("players.Console.invitedBy", "Server");
				this.getConfig().set("players.Console.activeInvites", (this.getConfig().contains("players.Console.activeInvites")) ? 0 : this.getConfig().getInt("players.Console.activeInvites") + 1);
				this.getConfig().set("players.Console.invitedPlayers", invitedPlayers);
				this.getConfig().set("players.Console.codes", codes);
				this.saveConfig();
				sender.sendMessage(InviteManager.pluginPrefix + "§e Se ha generado un nuevo codigo de invitación! Código: " + code);
				return;
			} else if (sender instanceof Player){
				Player player = (Player) sender;
				List<String> codes = this.getConfig().getStringList("players." + player.getUniqueId().toString() + ".codes");
				codes.add(code);
				List<String> invitedPlayers = this.getConfig().getStringList("players." + player.getUniqueId().toString() + ".invitedPlayers");
				this.getConfig().set("codes." + code + ".byPlayer", player.getUniqueId().toString());
				this.getConfig().set("codes." + code + ".expires", (expires == 0) ? false : true);
				this.getConfig().set("codes." + code + ".unlimited", (uses == -1) ? true : false);
				this.getConfig().set("codes." + code + ".expiryDate", expiresIn);
				this.getConfig().set("codes." + code + ".invitesLeft", (uses == -1) ? 9999 : uses);
				this.getConfig().set("players." + player.getUniqueId().toString() + ".invitedBy", player.getName());
				this.getConfig().set("players." + player.getUniqueId().toString() + ".activeInvites", (this.getConfig().contains("players." + player.getUniqueId().toString() + ".activeInvites")) ? 0 : this.getConfig().getInt("players." + player.getUniqueId().toString() + ".activeInvites") + 1);
				this.getConfig().set("players." + player.getUniqueId().toString() + ".invitedPlayers", invitedPlayers);
				this.getConfig().set("players." + player.getUniqueId().toString() + ".codes", codes);
				this.saveConfig();
				sender.sendMessage(InviteManager.pluginPrefix + "§e Se ha generado un nuevo codigo de invitación! Código: " + code);
				return;
			}
		}
	}

	public boolean addPlayerToFile(Player p, String code)
    {
        if (this.getConfig().contains("codes." + code)) {
            boolean expires = this.getConfig().getBoolean("codes." + code + ".expires");
            String invitedBy = this.getConfig().getString("codes." + code + ".byPlayer");
            int invitesLeft = this.getConfig().getInt("codes." + code + ".invitesLeft");
            boolean unlimitedInvites = this.getConfig().getBoolean("codes." + code + ".unlimited");
            Date expiryDate;
            try {
                expiryDate = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").parse(this.getConfig().getString("codes." + code + ".expiryDate"));
            } catch (Exception e) {
                return false;
            }

            if ((expires && expiryDate.after(new Date(System.currentTimeMillis())))) {
                this.deleteCode(code, "");
                return false;
            }
            String[] codes = {};
            String[] invitedPlayers = {};
            if (!unlimitedInvites && invitesLeft - 1 < 1)
                this.deleteCode(code, p.getUniqueId().toString());
            else if (!unlimitedInvites)
                this.getConfig().set("codes." + code + ".invitesLeft", invitesLeft - 1);
            this.getConfig().set("players." + p.getUniqueId().toString(), true);
            this.getConfig().set("players." + p.getUniqueId().toString() + ".invitedBy", invitedBy);
            this.getConfig().set("players." + p.getUniqueId().toString() + ".activeInvites", 0);
            this.getConfig().set("players." + p.getUniqueId().toString() + ".codes", Arrays.asList(codes));
            this.getConfig().set("players." + p.getUniqueId().toString() + ".invitedPlayers", Arrays.asList(invitedPlayers));
			this.saveConfig();
			return true;
        } else {
            p.sendMessage(InviteManager.pluginPrefix + "§cEste codigo no es válido!");
            return false;
		}
    }

	public void reloadConfig() {
		if (this.configFile == null)
			this.configFile = new File(this.plugin.getDataFolder(), "data.yml");
		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
		InputStream defaultStream = this.plugin.getResource("data.yml");
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		if (this.dataConfig == null)
			reloadConfig();
		return this.dataConfig;
	}
	
	public void saveConfig() {
		if (this.dataConfig == null || this.configFile == null)
			return;
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, InviteManager.pluginPrefix + "No se ha podido guardar el archivo en" + this.configFile, e);
		}
	}

	public void saveDefaultConfig() {
		if (this.configFile == null)
			this.configFile = new File(this.plugin.getDataFolder(), "data.yml");
		if (!this.configFile.exists()) {
			this.plugin.saveResource("data.yml", false);
		}
	}
}