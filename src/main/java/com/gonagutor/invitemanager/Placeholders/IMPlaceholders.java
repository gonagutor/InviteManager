package com.gonagutor.invitemanager.Placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.List;
import java.util.UUID;

import com.gonagutor.invitemanager.InviteManager;

/**
 * This class will be registered through the register-method in the plugins
 * onEnable-method.
 */
public class IMPlaceholders extends PlaceholderExpansion {

  private InviteManager plugin;

  public IMPlaceholders(InviteManager plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean persist() {
    return true;
  }

  @Override
  public boolean canRegister() {
    return true;
  }

  @Override
  public String getAuthor() {
    return plugin.getDescription().getAuthors().toString();
  }

  @Override
  public String getVersion() {
    return plugin.getDescription().getVersion();
  }

  /**
   * The placeholder identifier should go here. <br>
   * This is what tells PlaceholderAPI to call our onRequest method to obtain a
   * value if a placeholder starts with our identifier. <br>
   * The identifier has to be lowercase and can't contain _ or %
   *
   * @return The identifier in {@code %<identifier>_<value>%} as String.
   */
  @Override
  public String getIdentifier() {
    return "InviteManager";
  }

  /**
   * This is the method called when a placeholder with our identifier is found and
   * needs a value. <br>
   * We specify the value identifier in this method. <br>
   * Since version 2.9.1 can you use OfflinePlayers in your requests.
   *
   * @param player     A {@link org.bukkit.Player Player}.
   * @param identifier A String containing the identifier/value.
   *
   * @return possibly-null String of the requested identifier.
   */
  @Override
  public String onPlaceholderRequest(Player player, String identifier) {
    if (player == null) {
      return "";
    }

    if (identifier.equals("invited_by")) {
      String playerUUID = InviteManager.plf.getConfig().getString("players." + player.getUniqueId() + ".invitedBy",
          "N/A");
      return (playerUUID.equals("Console")) ? "Console" : Bukkit.getPlayer(UUID.fromString(playerUUID)).getName();
    }

    if (identifier.equals("active_invites")) {
      return String
          .valueOf(InviteManager.plf.getConfig().getInt("players." + player.getUniqueId() + ".activeInvites", 0));
    }

    if (identifier.equals("codes")) {
      List<String> codes = InviteManager.plf.getConfig().getStringList("players." + player.getUniqueId() + ".codes");
      String ret = "";
      for (String string : codes) {
        ret += " - " + string + "\n";
      }
      if (ret.length() > 1)
        ret = ret.substring(0, ret.length() - 1);
      return (ret != "") ? ret : "Ninguno";
    }

    if (identifier.equals("invited_players")) {
      List<String> invitedPlayers = InviteManager.plf.getConfig()
          .getStringList("players." + player.getUniqueId() + ".invitedPlayers");
      String ret = "";
      for (String string : invitedPlayers) {
        ret += " - " + string + "\n";
      }
      if (ret.length() > 1)
        ret = ret.substring(0, ret.length() - 1);
      return (ret != "") ? ret : "Ninguno";
    }
    return null;
  }
}